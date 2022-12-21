package edu.tomerbu.lec17.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import edu.tomerbu.lec17.database.dao.PeopleDao
import edu.tomerbu.lec17.models.Person


const val DB_NAME = "AppDatabase"
const val DB_VERSION = 1

@Database(entities = [Person::class], version = DB_VERSION)
abstract class AppDatabase : RoomDatabase() {
    //expose the dao's:

    abstract fun peopleDao(): PeopleDao

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


