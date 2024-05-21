package com.example.hemius.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.hemius.database.entities.Folder
import com.example.hemius.database.entities.FolderWithImages
import kotlinx.coroutines.flow.Flow


@Dao
interface FolderDao {
    @Query("SELECT * FROM FolderWithImages")
    fun getAllFoldersWithImages(): Flow<List<FolderWithImages>>

    @Query("SELECT * FROM folder WHERE id = :folderId")
    suspend fun getFolderById(folderId: Int): Folder

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(folder: Folder)

    @Delete
    suspend fun delete(folder: Folder)
}
