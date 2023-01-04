package edu.tomerbu.lec17.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Movie constructor(
    @SerializedName("id")
    @PrimaryKey
    val movieId: Long,

    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String?,

    @SerializedName("original_language")
    val originalLanguage: String,

    @SerializedName("original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int,

    var page: Int = 1
){
    //Room (Don't save the genreIds in the Movie Table)
    @Ignore
    @SerializedName("genre_ids")
    val genreIds: List<Int> = mutableListOf()

    //computed properties
    val posterUrl get() = "https://image.tmdb.org/t/p/w500${posterPath}"
    val backdropUrl get() = "https://image.tmdb.org/t/p/w500${backdropPath}"
}

//MANY TO MANY?