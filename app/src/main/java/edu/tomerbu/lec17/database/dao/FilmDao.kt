package edu.tomerbu.lec17.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import edu.tomerbu.lec17.models.*

@Dao
interface FilmDao {
    @Insert
    suspend fun add(film: Film)

    @Insert
    suspend fun add(review: Review)

    @Query("SELECT * FROM Film")
    fun getFilms(): LiveData<List<Film>>

    @Query("SELECT * FROM Review")
    fun getReviews(): LiveData<List<Review>>

    @Transaction
    @Query("SELECT * FROM Film")
    fun getFilmsWithReviews(): LiveData<List<FilmWithReviews>>

    @Insert
    suspend fun add(genre: FGenre)

    @Insert
    suspend fun add(genreCrossRef: FilmGenreCrossRef)

    @Transaction
    @Query("SELECT * FROM Film")
    fun getFilmsWithGenres(): LiveData<List<FilmsWithGenres>>
}