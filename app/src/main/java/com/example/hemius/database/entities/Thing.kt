package com.example.hemius.database.entities

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Thing(
    @ColumnInfo(name = "name")
    val name: String? = null,

    @ColumnInfo(name = "description")
    val description: String? = null,

    @ColumnInfo(name = "is_archived")
    val isArchived: Boolean = false,

    @ColumnInfo(name = "image", typeAffinity = ColumnInfo.BLOB)
    var image: Bitmap? = null,

    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0
)
