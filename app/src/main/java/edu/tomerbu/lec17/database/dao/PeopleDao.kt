package edu.tomerbu.lec17.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.tomerbu.lec17.models.Person


@Dao
interface PeopleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) // INSERT INTO People(id, name) VALUES('', '')
    suspend fun add(person: Person)

    @Query("SELECT * FROM people")
    fun getAll(): LiveData<List<Person>>
}