package com.example.hemius.database.entities

import androidx.room.DatabaseView
import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

@DatabaseView(
    """
    SELECT 
        Folder.id as folderId,
        Folder.name as folderName,
        Thing.image_path as imagePath
    FROM Folder
    LEFT JOIN ThingFolderCrossRef ON Folder.id = ThingFolderCrossRef.folderId
    LEFT JOIN Thing ON ThingFolderCrossRef.thingId = Thing.uid
    """
)
data class FolderWithImages(
    val folderId: Int,
    val folderName: String,
    val imagePath: String?
)