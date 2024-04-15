package com.example.hemius.surfaces

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.hemius.R
import com.example.hemius.components.ThingBox
import com.example.hemius.database.entities.Thing
import com.example.hemius.database.events.ThingEvent
import com.example.hemius.database.states.ThingState
import com.example.hemius.ui.theme.HemiusColors

@Composable
fun ThingsSurface (
    things : List<Thing>,
    state: ThingState,
    onEvent: (ThingEvent) -> Unit,
){
    DisposableEffect(Unit) {
        onDispose {
            onEvent(ThingEvent.DeselectSelected)
        }
    }
    Surface (
        modifier = Modifier
            .background(HemiusColors.current.background)
    ) {
        fun toggleItemInList(id : Int) {
            val selectedIds = state.selectedThings.map { it.uid }
            if (selectedIds.contains(id)) {
                onEvent(ThingEvent.DeselectThing(state.selectedThings.find { it.uid == id }!!))
            } else {
                onEvent(ThingEvent.SelectThing(things.find { it.uid == id }!!))
            }
        }

        fun onThingClick(id : Int) {
            if (state.selectedThings.isNotEmpty()) {
                toggleItemInList(id)
            }
        }

        val placeholderBitmap = R.drawable.im_spinner.toBitmap()

        if(things.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("There are no things yet")
            }
        } else {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(150.dp),
                horizontalArrangement = Arrangement.spacedBy(0.dp),
                verticalItemSpacing = 0.dp,
                contentPadding = PaddingValues(start=9.dp, top=9.dp, end=9.dp, bottom=200.dp) ,
                modifier = Modifier
                    .background(HemiusColors.current.background)
                    .fillMaxSize()
            ) {
                items(things) {  thing ->
                    ThingBox(
                        id = thing.uid,
                        text = thing.name!!,
                        image = thing.image?.asImageBitmap()?: placeholderBitmap.asImageBitmap(),
                        selectedIds = state.selectedThings.map { it.uid },
                        onItemClick = { id -> onThingClick(id) },
                        onItemPress = { id -> toggleItemInList(id) }
                    )
                }
            }
        }
    }
}

@Composable
fun Int.toBitmap(): Bitmap {
    val context = LocalContext.current
    return BitmapFactory.decodeResource(context.resources, this)
}
