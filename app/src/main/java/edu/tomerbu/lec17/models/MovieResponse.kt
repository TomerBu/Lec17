package edu.tomerbu.lec17.models

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    val page: Int,
    @SerializedName("results")
    val movies: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int,

    @SerializedName("status_code")
    val statusCode: String?,

    @SerializedName("status_message")
    val statusMessage: String?,

    val success: Boolean?
)