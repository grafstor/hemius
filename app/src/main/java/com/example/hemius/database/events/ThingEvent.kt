package com.example.hemius.database.events

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import com.example.hemius.database.entities.Thing

sealed interface ThingEvent {
    data class SetImage(val image: Bitmap): ThingEvent
    data class SetName(val name: String): ThingEvent
    data class SetDescription(val description : String): ThingEvent

    data class SelectFlder(val folder : String): ThingEvent

    data class SelectThing(val thing : Thing): ThingEvent
    data class DeselectThing(val thing : Thing): ThingEvent

    object Save: ThingEvent
    object DeleteSelected: ThingEvent
    object ArchiveSelected: ThingEvent

    object DeselectSelected: ThingEvent
}