package com.example.hemius.database.entities

import androidx.room.Entity

@Entity(primaryKeys = ["thingId", "folderId"])
data class ThingFolderCrossRef(
    val thingId: Int,
    val folderId: Int
)