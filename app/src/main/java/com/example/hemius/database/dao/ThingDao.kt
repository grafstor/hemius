package com.example.hemius.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.hemius.database.entities.Thing
import com.example.hemius.database.entities.ThingFolderCrossRef

import kotlinx.coroutines.flow.Flow

@Dao
interface ThingDao {
    @Query("SELECT * FROM thing")
    suspend fun getAllThingsForLogging(): List<Thing>

    @Query("SELECT * FROM thing WHERE is_archived = :archived")
    fun getAllThings(archived: Boolean): Flow<List<Thing>>

    @Query("""
        SELECT thing.* FROM thing
        INNER JOIN ThingFolderCrossRef ON thing.uid = ThingFolderCrossRef.thingId
        WHERE ThingFolderCrossRef.folderId = :folderId
    """)
    fun getThingsByFolder(folderId: Int): Flow<List<Thing>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(thing: Thing)

    @Delete
    suspend fun delete(thing: Thing)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertThingFolderCrossRef(crossRef: ThingFolderCrossRef)

    @Query("DELETE FROM ThingFolderCrossRef WHERE thingId = :thingId AND folderId = :folderId")
    suspend fun deleteThingFromFolder(thingId: Int, folderId: Int)

    @Query("UPDATE thing SET is_archived = :archived WHERE uid = :uid")
    suspend fun setArchived(uid: Int, archived: Boolean)
}
