package edu.tomerbu.lec17.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class FGenre(
    @ColumnInfo(name = "film_name")
    val name: String,

    @PrimaryKey
    val genreId: String = UUID.randomUUID().toString(),
)

//one to one = 2 tables
//one to many = 2 tables
//many to many = 3 tables
