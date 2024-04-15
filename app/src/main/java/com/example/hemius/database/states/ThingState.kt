package com.example.hemius.database.states

import android.graphics.Bitmap
import com.example.hemius.database.entities.Thing

data class ThingState (
    val things: List<Thing> = emptyList(),
    val archivedThings: List<Thing> = emptyList(),

    val selectedFolder: String = "Все",
    val selectedThings: List<Thing> = emptyList(),
    val isThingSelected: Boolean = false,

    val name: String = "",
    val description: String = "",

    val image: Bitmap? = null,
    val thing : Thing? = null,
)