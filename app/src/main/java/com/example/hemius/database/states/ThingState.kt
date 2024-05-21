package com.example.hemius.database.states

import android.graphics.Bitmap
import com.example.hemius.database.entities.Folder
import com.example.hemius.database.entities.Thing
import com.example.hemius.database.vm.ThingViewModel

data class ThingState (
    val things: List<Thing> = emptyList(),
    val folders: List<Pair<Folder, List<String>>> = emptyList(),

    val selectedFolderId: Int = ThingViewModel.ALL_FOLDERS_ID,

    val archivedFilter: Boolean = false,

    val selectedThings: List<Thing> = emptyList(),
    val moveableThings: List<Thing> = emptyList(),

    val isThingSelected: Boolean = false,

    val name: String = "",
    val description: String = "",

    val image: Bitmap? = null,
    val thing : Thing? = null,

    val isFolderCreation: Boolean = false,
    val folderName: String = "",

    val isDarkTheme: Boolean = false,

    val isToFolderMoving: Boolean = false
)