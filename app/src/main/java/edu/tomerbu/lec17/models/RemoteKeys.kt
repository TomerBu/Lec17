package edu.tomerbu.lec17.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val movieId: Long,

    //good for refreshing:
    val currentPage: Int,

    //append next page:
    val nextKey: Int?,

    //prepend previous page:
    val prevKey: Int?,

    //to remove old data, we save the time:
    val createdAt: Long = System.currentTimeMillis(),
)