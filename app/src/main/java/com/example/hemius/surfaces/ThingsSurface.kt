package com.example.hemius.surfaces

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed

import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

import com.example.hemius.components.ThingBox
import com.example.hemius.database.entities.Thing
import com.example.hemius.database.events.ThingEvent
import com.example.hemius.database.states.ThingState
import com.example.hemius.ui.theme.HemiusColors

@Composable
fun ThingsSurface (
    things : List<Thing>,

    onThingOpenClick : () -> Unit = {},

    state: ThingState,
    onEvent: (ThingEvent) -> Unit,
){
    DisposableEffect(Unit) {
        onDispose {
            onEvent(ThingEvent.SaveSelected)
            onEvent(ThingEvent.DeselectSelected)
        }
    }
    Surface (
        modifier = Modifier
            .background(HemiusColors.current.background)
    ) {
        fun onThingClick(thing : Thing) {
            if (state.selectedThings.isNotEmpty()) {
                onEvent(ThingEvent.ToggleItemInList(thing))
            }
            else{
                onThingOpenClick()
            }
        }

        if(things.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier
                        .height(20.dp),
                    text = "Тут пока нет вещей",
                    color = HemiusColors.current.fontSecondary,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        } else {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(0.dp),
                verticalItemSpacing = 0.dp,
                contentPadding = PaddingValues(start=9.dp, top=9.dp, end=9.dp, bottom=200.dp) ,
                modifier = Modifier
                    .background(HemiusColors.current.background)
                    .fillMaxSize()
            ) {
                itemsIndexed(items = things, key = { _, thing -> thing.uid }) { _, thing ->
                    val selectedIds = state.selectedThings.map { it.uid }
                    val isSelected = remember(selectedIds) { selectedIds.contains(thing.uid) }

                    ThingBox(
                        thing = thing,
                        isSelected = isSelected,
                        onItemClick = { onThingClick(it) },
                        onItemPress = { onEvent(ThingEvent.ToggleItemInList(it)) },
                        onEvent = onEvent
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
