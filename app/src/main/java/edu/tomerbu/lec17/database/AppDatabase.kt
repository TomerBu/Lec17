package edu.tomerbu.lec17.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import edu.tomerbu.lec17.database.dao.MovieDao
import edu.tomerbu.lec17.database.dao.RemoteKeysDao
import edu.tomerbu.lec17.models.Genre
import edu.tomerbu.lec17.models.Movie
import edu.tomerbu.lec17.models.MovieGenreCrossRef
import edu.tomerbu.lec17.models.RemoteKeys


const val DB_NAME = "AppDatabase"
const val DB_VERSION = 2 //when we change the db version - the db is destroyed and re-created

@Database(entities = [Movie::class, Genre::class, MovieGenreCrossRef::class, RemoteKeys::class], version = DB_VERSION)
abstract class AppDatabase : RoomDatabase() {
    //expose the dao's:
    abstract fun movieDao(): MovieDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {
        fun create(context: Context): AppDatabase =
            Room
                .databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                //if a new version of the app is installed AND
                // db structure was upgraded => delete the db and re-create it
                .fallbackToDestructiveMigration()
                .build()
    }
}


