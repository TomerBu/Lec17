package edu.tomerbu.lec17.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import edu.tomerbu.lec17.database.AppDatabase
import edu.tomerbu.lec17.models.Movie
import edu.tomerbu.lec17.models.RemoteKeys
import edu.tomerbu.lec17.services.TMDBService
import retrofit2.HttpException
import retrofit2.http.HTTP
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator(
    private val movieApiService: TMDBService,
    private val movieDatabase: AppDatabase
) : RemoteMediator<Int, Movie>() {
    override suspend fun initialize(): InitializeAction {
        //check the time if data in db is too old -> refresh it:

        //one hour in millis:
        val cacheTimeOut = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)

        return if (System.currentTimeMillis() - (movieDatabase.remoteKeysDao().getCreatedAt()
                ?: 0) < cacheTimeOut
        ) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {
        //calculate the page to load: check the load-type (refresh/append/prepend)
        val page: Int = when (loadType) {
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeysForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                //if it's the last page (get out of the method and tell the library)
                nextKey ?: return MediatorResult.Success(remoteKeys != null)
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeysForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                //if it's the last page (get out of the method and tell the library)
                prevKey ?: return MediatorResult.Success(remoteKeys != null)
            }
            LoadType.REFRESH -> {
                //refresh is also called at the first load:
                val remoteKeys = getRemoteKeysClosestToCurrentItem(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
                //remoteKeys.page ?: 1
            }
        }
        //found the page to load:
        try {
            //go to the api and fetch the movies:
            val apiResponse = movieApiService.popularMovies(page)
            val movies = apiResponse.movies
            val endOfPagination = movies.isEmpty()

            //save to db: all or nothing:
            //3 operations against the db:
            //if one operation throws an exception :
            //all 3 operations are canceled (Role back)
            movieDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    //clear the previous data:
                    movieDatabase.remoteKeysDao().clearRemoteKeys()
                    movieDatabase.movieDao().clearAllMovies()
                }

                //generate the data we want to save:
                val prevPage = if (page > 1) page - 1 else null
                val nextPage = if (!endOfPagination) page + 1 else null
                val remoteKeys = movies.map {
                    RemoteKeys(
                        movieId = it.movieId,
                        currentPage = page,
                        nextKey = nextPage,
                        prevKey = prevPage
                    )
                }

                //add the page number to all the movies
                movies.forEach { it.page = page }

                movieDatabase.remoteKeysDao().insertAll(remoteKeys)
                movieDatabase.movieDao().addMovies(movies)
            }
            return MediatorResult.Success(endOfPagination)

        } catch (error: IOException) {
            return MediatorResult.Error(error)
        } catch (error: HttpException) {
            return MediatorResult.Error(error)
        }

    }

    //helper methods for prepend, append, refresh:
    //Append:
    private suspend fun getRemoteKeysForLastItem(state: PagingState<Int, Movie>): RemoteKeys? {
        //state.pages = List<Page>
        //each page contains data: List<Movie>
        //get the last page which contains data (NOT EMPTY)
        val lastPage = state.pages.lastOrNull() {
            it.data.isNotEmpty()
        }

        val lastKey = lastPage?.data?.lastOrNull()?.let { movie ->
            movieDatabase.remoteKeysDao().getRemoteKeysByMovieId(movie.movieId)
        }

        return lastKey
    }

    //Prepend:
    private suspend fun getRemoteKeysForFirstItem(state: PagingState<Int, Movie>): RemoteKeys? {

        val firstPage = state.pages.firstOrNull() {
            it.data.isNotEmpty()
        }

        val firstKey = firstPage?.data?.firstOrNull()?.let { movie ->
            movieDatabase.remoteKeysDao().getRemoteKeysByMovieId(movie.movieId)
        }

        return firstKey
    }

    //Refresh:
    private suspend fun getRemoteKeysClosestToCurrentItem(state: PagingState<Int, Movie>): RemoteKeys? {
        //get the page from the library:
        val position = state.anchorPosition ?: return null//Int of the page

        val closestMovieToPosition = state.closestItemToPosition(position) ?: return null

        return movieDatabase.remoteKeysDao().getRemoteKeysByMovieId(closestMovieToPosition.movieId)
    }
}

