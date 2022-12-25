package edu.tomerbu.lec17.repository

import androidx.lifecycle.LiveData
import edu.tomerbu.lec17.database.dao.FilmDao
import edu.tomerbu.lec17.models.FilmsWithGenres
import kotlinx.coroutines.Dispatchers

class MovieRepository(private val filmDao: FilmDao) {
    suspend fun getFilmsWithGenres(): LiveData<List<FilmsWithGenres>> {
        return with(Dispatchers.IO) {
            //async code for fetching from server
            filmDao.getFilmsWithGenres()
        }
    }
}