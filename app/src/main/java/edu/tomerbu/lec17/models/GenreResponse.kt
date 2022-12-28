package edu.tomerbu.lec17.models

import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @SerializedName("genres")
    val genres: List<Genre>,

    @SerializedName("status_code")
    val statusCode: String?,

    @SerializedName("status_message")
    val statusMessage: String?,

    val success: Boolean?
)