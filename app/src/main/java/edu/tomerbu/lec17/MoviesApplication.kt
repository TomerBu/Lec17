package edu.tomerbu.lec17

import android.app.Application
import android.net.ConnectivityManager
import edu.tomerbu.lec17.database.AppDatabase
import edu.tomerbu.lec17.network.NetworkStatusChecker
import edu.tomerbu.lec17.repository.MovieRepository
import edu.tomerbu.lec17.services.TMDBService

class MoviesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        println("App Started")
        instance = this
        //db = AppDatabase.create(this)
    }

    companion object {
        private lateinit var instance: MoviesApplication

        //משתנה ייוצר רק בעת השימוש הראשון בו
        val db: AppDatabase by lazy {
            AppDatabase.create(instance)
        }

        val repository: MovieRepository by lazy {
            MovieRepository(db.movieDao())
        }

        val tmdbService: TMDBService by lazy {
            TMDBService.create()
        }

        val networkStatusChecker: NetworkStatusChecker by lazy {
            val connectivityManager = instance.getSystemService(ConnectivityManager::class.java)
            NetworkStatusChecker(connectivityManager)
        }
    }
}