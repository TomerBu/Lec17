package edu.tomerbu.lec17.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID


@Entity
data class Review(
    val text: String,

    //each review must belong to a movie:
    val reviewedFilmId: String,

    @PrimaryKey
    val reviewId: String = UUID.randomUUID().toString()
)


//data-models: room
//domain-models: Film{Review}
//domain models android