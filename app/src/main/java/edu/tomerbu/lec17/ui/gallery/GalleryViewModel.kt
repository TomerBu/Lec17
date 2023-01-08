package edu.tomerbu.lec17.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.*
import edu.tomerbu.lec17.MoviesApplication
import edu.tomerbu.lec17.models.Movie
import edu.tomerbu.lec17.repository.MoviesRemoteMediator
import edu.tomerbu.lec17.services.TMDBService

private const val TMDB_PAGE_SIZE = 20

class GalleryViewModel : ViewModel() {

    private var db = MoviesApplication.db
    private var service = MoviesApplication.tmdbService

    @OptIn(ExperimentalPagingApi::class)
    fun getMovies(): LiveData<PagingData<Movie>> = Pager(
        //
        config = PagingConfig(
            pageSize = TMDB_PAGE_SIZE,
            prefetchDistance = 10, //המרחק שאליו עושים scroll לפני append
            initialLoadSize = TMDB_PAGE_SIZE * 2,
        ), pagingSourceFactory = {
            //db reference:
            db.movieDao().getMoviesPaged()
        },
        //ref to the remote mediator class
        remoteMediator = MoviesRemoteMediator(service, db)
    ).liveData
}