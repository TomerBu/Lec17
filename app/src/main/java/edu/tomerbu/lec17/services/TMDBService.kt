package edu.tomerbu.lec17.services

import edu.tomerbu.lec17.BuildConfig
import edu.tomerbu.lec17.models.GenreResponse
import edu.tomerbu.lec17.models.MovieDetailsResponse
import edu.tomerbu.lec17.models.MovieResponse
import edu.tomerbu.lec17.network.utils.TokenInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBService {

    @GET("3/discover/movie?sort_by=popularity.desc")
    suspend fun popularMovies(@Query("page") page: Int = 1): MovieResponse

    @GET("3/genre/movie/list")
    suspend fun genres(): GenreResponse

    @GET("3/movie/{id}?language=en-US&append_to_response=videos,credits,similar,recommendations")
    suspend fun getMovieDetails(
        @Path("id") id: Long,
    ): MovieDetailsResponse

    @GET("3/search/movie?include_adult=false")
    suspend fun searchMovies(
        @Query("page") page: Int,
        @Query("query") query: String,
    ): MovieResponse

    @GET("3/discover/movie")
    suspend fun getDiscoveredMovies(
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String,
    ): MovieResponse

    @GET("3/trending/movie/week")
    suspend fun getTrendingMovies(
        @Query("page") page: Int,
    ): MovieResponse

    @GET("3/movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
    ): MovieResponse

    @GET("3/movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int,
    ): MovieResponse

    @GET("3/movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int,
    ): MovieResponse

    @GET("3/discover/movie?sort_by=vote_average.desc&vote_count.gte=4000")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int,
    ): MovieResponse

    @GET("3/discover/movie?sort_by=original_title.asc")
    suspend fun getMoviesByTitleASC(
        @Query("page") page: Int,
    ): MovieResponse

    @GET("3/discover/movie?sort_by=original_title.desc")
    suspend fun getMoviesByTitleDESC(
        @Query("page") page: Int,
    ): MovieResponse

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
                .baseUrl(BuildConfig.TMDB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(TMDBService::class.java)
        }
    }
}