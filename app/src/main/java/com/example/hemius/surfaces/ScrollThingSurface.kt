package com.example.hemius.surfaces

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import com.example.hemius.R
import com.example.hemius.components.ThingBox
import com.example.hemius.components.TopThingOptions
import com.example.hemius.database.entities.Thing
import com.example.hemius.database.events.ThingEvent
import com.example.hemius.database.states.ThingState
import com.example.hemius.ui.theme.HemiusColors


@Composable
fun ScrollThingSurface (
    onHomeClick : () -> Unit = {},

    onToFolderClick : () -> Unit = {},
    onDeleteClick : () -> Unit = {},
    onToArchiveClick : () -> Unit = {},
    onToUnarchiveClick : () -> Unit = {},

    state: ThingState,
    onEvent: (ThingEvent) -> Unit,
){
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(HemiusColors.current.background)
            .padding(0.dp)
            .pointerInput(state) {
                detectHorizontalDragGestures { change, dragAmount ->
                    println(dragAmount)
                    val selectedThingIndex =
                        state.things.indexOfFirst { it.uid == state.thing?.uid }

                    if (dragAmount > 0) {
                        val previousThing =
                            if (selectedThingIndex > 0) state.things[selectedThingIndex - 1] else null
                        if (previousThing != null) {
                            onEvent(ThingEvent.OpenThing(previousThing))
                        }
                    } else if (dragAmount < 0) {
                        val nextThing =
                            if (selectedThingIndex < state.things.size - 1) state.things[selectedThingIndex + 1] else null
                        if (nextThing != null) {
                            onEvent(ThingEvent.OpenThing(nextThing))
                        }
                    }
                }
            }
        ,
    ) {
        Column (modifier = Modifier
            .wrapContentSize(Alignment.Center)
            .background(HemiusColors.current.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(25.dp),
        ) {

            TopThingOptions(
                onHomeClick = onHomeClick,

                onToFolderClick = onToFolderClick,
                onDeleteClick = onDeleteClick,
                onToArchiveClick = onToArchiveClick,
                onToUnarchiveClick = onToUnarchiveClick,

                state = state,
                onEvent = onEvent,
            )

            if (state.thing != null){
                val imageKey = state.thing.imagePath

                AnimatedContent(
                    targetState = imageKey,
                    modifier = Modifier.defaultMinSize(200.dp, 200.dp),
                    contentAlignment = Alignment.Center,
                ) { imagePath ->
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(imagePath)
                            .build(),
                        contentDescription = "icon",
                        contentScale = ContentScale.Inside,
                    )
                }
                Column (modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(18.dp),
                ) {
                    Text(
                        modifier = Modifier
                            .height(35.dp),
                        text = state.thing!!.name!!,
                        color = HemiusColors.current.font,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        modifier = Modifier
                            .height(28.dp),
                        text = state.thing!!.description!!,
                        color = HemiusColors.current.font,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                    val selectedThingIndex = state.things.indexOfFirst { it.uid == state.thing?.uid }
                    val previousThing = if (selectedThingIndex > 0) state.things[selectedThingIndex - 1] else null
                    val nextThing = if (selectedThingIndex < state.things.size - 1) state.things[selectedThingIndex + 1] else null

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 18.dp, end = 18.dp)
                            ,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (previousThing != null) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(previousThing.imagePath)
                                    .build(),
                                contentDescription = "icon",
                                contentScale = ContentScale.Inside,
                                modifier = Modifier
                                    .height(40.dp)
                            )

                        }else{
                            Box(
                                modifier = Modifier
                                    .height(40.dp)
                                    .background(HemiusColors.current.background)
                                    .width(40.dp)
                            )
                        }
                        if (nextThing != null) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(nextThing.imagePath)
                                    .build(),
                                contentDescription = "icon",
                                contentScale = ContentScale.Inside,
                                modifier = Modifier
                                    .height(40.dp)
                            )
                        }else{
                            Box(
                                modifier = Modifier
                                    .height(40.dp)
                                    .background(HemiusColors.current.background)
                                    .width(40.dp)
                            )
                        }
                }
            }
        }
    }
}