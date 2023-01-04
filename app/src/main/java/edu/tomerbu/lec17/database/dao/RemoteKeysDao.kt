package edu.tomerbu.lec17.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.tomerbu.lec17.models.RemoteKeys

@Dao
interface RemoteKeysDao {
    //CRUD: insert delete get

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<RemoteKeys>)

    @Query("SELECT * FROM RemoteKeys WHERE movieId = :id")
    suspend fun getRemoteKeysByMovieId(id: Long): RemoteKeys?

    //clear all the rows:
    @Query("DELETE FROM RemoteKeys")
    suspend fun clearRemoteKeys()

    //get the creation time of the last element added:
    @Query("SELECT createdAt FROM RemoteKeys ORDER BY createdAt DESC LIMIT 1")
    suspend fun getCreatedAt():Long?
}
