package edu.tomerbu.lec17.services

import edu.tomerbu.lec17.models.GenreResponse
import edu.tomerbu.lec17.models.MovieResponse
import edu.tomerbu.lec17.network.utils.TokenInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface TMDBService {

    @GET("3/discover/movie?sort_by=popularity.desc")
    suspend fun popularMovies(): MovieResponse

    @GET("3/genre/movie/list")
    suspend fun genres(): GenreResponse

    //Setup:
    companion object {
        fun create(): TMDBService {
            //תופס את כל הבקשות ומדפיס ללוג
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY

            // תופס את כל הבקשות ומוסיף Api Key
            val client = OkHttpClient.Builder()
                .addInterceptor(TokenInterceptor())
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .client(client)
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(TMDBService::class.java)
        }
    }
}