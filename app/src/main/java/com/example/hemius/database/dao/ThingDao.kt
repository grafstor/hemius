package com.example.hemius.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.hemius.database.entities.Thing

import kotlinx.coroutines.flow.Flow

@Dao
interface ThingDao {
    @Query("SELECT * FROM thing WHERE is_archived = false")
    fun getAllUnarchived(): Flow<List<Thing>>

    @Query("SELECT * FROM thing WHERE is_archived = true")
    fun getAllArchived(): Flow<List<Thing>>

    @Query("UPDATE thing SET is_archived = true WHERE uid = :uid")
    suspend fun archive(uid: Int)

    @Query("UPDATE thing SET is_archived = false WHERE uid = :uid")
    suspend fun unarchive(uid: Int)

    @Upsert
    suspend fun upsert(thing : Thing)

    @Delete
    suspend fun delete(thing : Thing)
}

