package com.example.hemius.database.states

import android.graphics.Bitmap
import com.example.hemius.database.entities.Folder
import com.example.hemius.database.entities.Thing
import com.example.hemius.database.vm.ThingViewModel

data class ThingState (
    val thing : Thing? = null,
    val things: List<Thing> = emptyList(),

    val moveableThings: List<Thing> = emptyList(),
    val selectedThings: List<Thing> = emptyList(),

    val isThingSelected: Boolean = false,

    val name: String = "",
    val description: String = "",
    val image: Bitmap? = null,

    val search: String = "",

    val folders: List<Pair<Folder, List<String>>> = emptyList(),
    val selectedFolderId: Int = ThingViewModel.ALL_FOLDERS_ID,

    val folderName: String = "",

    val isFolderCreation: Boolean = false,
    val isToFolderMoving: Boolean = false,

    val archivedFilter: Boolean = false,

    val isDarkTheme: Boolean = false
)