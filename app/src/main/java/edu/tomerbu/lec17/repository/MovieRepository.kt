package edu.tomerbu.lec17.repository

import edu.tomerbu.lec17.database.dao.MovieDao
import edu.tomerbu.lec17.models.MovieGenreCrossRef
import edu.tomerbu.lec17.services.TMDBService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(private val movieDao: MovieDao) {

    //Retrofit is a data Source
    //movieDao is a data Sink
    fun getMovies() = movieDao.getMoviesWithGenres()

    suspend fun refreshMovies() {
        //Change the dispatcher to IO:
        withContext(Dispatchers.IO) {

            //scope function with
            with(TMDBService.create()) {
                //fetch from the api:
                val movies = popularMovies().movies
                val genres = genres().genres

                //save to local db:
                movieDao.addMovies(movies)
                movieDao.addGenres(genres)

                for(movie in movies){
                    for(genreId in movie.genreIds){
                        movieDao.add(MovieGenreCrossRef(movie.movieId, genreId))
                    }
                }
            }
        }
    }
}