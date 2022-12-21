package edu.tomerbu.lec17.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.UUID

@Entity
data class Film(
    val title: String,
    @PrimaryKey
    val filmId: String = UUID.randomUUID().toString()
)


//many to many:
@Entity(primaryKeys = ["filmId", "genreId"])
data class FilmGenreCrossRef(
    val filmId: String,
    val genreId: String,
//    @PrimaryKey
//    val fgID: String = UUID.randomUUID().toString()
)

data class FilmsWithGenres(
    @Embedded
    val film: Film,

    @Relation(
        parentColumn = "filmId",
        entityColumn = "genreId",
        associateBy = Junction(FilmGenreCrossRef::class)
    )
    val genres: List<FGenre>
)


//one to many:
//View
data class FilmWithReviews(
    @Embedded
    val film: Film,

    @Relation(
        parentColumn = "filmId",
        entityColumn = "reviewedFilmId"
    )
    val reviews: List<Review>?
)