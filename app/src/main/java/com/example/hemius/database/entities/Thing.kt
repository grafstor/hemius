package com.example.hemius.database.entities

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(
    entity = Folder::class,
    parentColumns = ["id"],
    childColumns = ["folderId"],
    onDelete = ForeignKey.CASCADE
)])
data class Thing(
    @ColumnInfo(name = "name")
    val name: String? = null,

    @ColumnInfo(name = "description")
    val description: String? = null,

    @ColumnInfo(name = "is_archived")
    val isArchived: Boolean = false,

    @ColumnInfo(name = "folderId", index = true)
    val folderId: Int? = null,

    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0,

    @ColumnInfo(name = "image_path")
    val imagePath: String? = null,
)
