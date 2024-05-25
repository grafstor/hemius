package com.example.hemius.database.events

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import com.example.hemius.database.entities.Thing

sealed interface ThingEvent {
    data class SetSearch(val search: String): ThingEvent
    data class SetImage(val image: Bitmap): ThingEvent
    data class SetName(val name: String): ThingEvent
    data class SetDescription(val description : String): ThingEvent
    data class SelectFolder(val folderId: Int): ThingEvent
    data class ToggleItemInList(val thing: Thing) : ThingEvent
    data class SelectThing(val thing : Thing): ThingEvent
    data class DeselectThing(val thing : Thing): ThingEvent

    object Save: ThingEvent
    object DeleteSelected: ThingEvent
    object ArchiveSelected: ThingEvent
    object DeleteFromFolderSelected: ThingEvent
    object DeselectSelected: ThingEvent
    object UnarchiveSelected: ThingEvent

    data class SetFolderName(val name: String): ThingEvent
    data class ToggleFolderCreation(val creation: Boolean): ThingEvent
    data class ToggleToFolderMoving(val isMoving: Boolean): ThingEvent
    data class ToggleArchive(val isArchive : Boolean): ThingEvent

    data class ToggleDarkTheme(val isDarkTheme: Boolean): ThingEvent

    object SaveFolder: ThingEvent
    object SaveSelected: ThingEvent

    data class OpenThing(val thing : Thing): ThingEvent
    data class ToFolderSelected(val folderId: Int): ThingEvent
}