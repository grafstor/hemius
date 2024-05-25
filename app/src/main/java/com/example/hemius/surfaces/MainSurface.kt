package com.example.hemius.surfaces

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.RoomDatabase
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room


import com.example.hemius.components.BottomMenu
import com.example.hemius.components.FoldersMenu
import com.example.hemius.components.TopMenu
import com.example.hemius.components.TopMenuFolderOptions
import com.example.hemius.components.TopMenuOptions
import com.example.hemius.database.entities.Folder
import com.example.hemius.database.events.ThingEvent
import com.example.hemius.database.states.ThingState
import com.example.hemius.database.vm.ThingViewModel
import com.example.hemius.ui.theme.HemiusColors


@Composable
fun MainSurface (
    onThingOpenClick : () -> Unit = {},

    onArchiveClick : () -> Unit = {},
    onSearchClick : () -> Unit = {},

    onToFolderClick : () -> Unit = {},
    onDeleteClick : () -> Unit = {},
    onToArchiveClick : () -> Unit = {},

    onHomeClick : () -> Unit = {},
    onFoldersClick : () -> Unit = {},
    onSettingsClick : () -> Unit = {},
    onCameraClick : () -> Unit = {},

    onFromFolderDeleteClick : () -> Unit = {},
    onModeToFolderClick : () -> Unit = {},

    state: ThingState,
    onEvent: (ThingEvent) -> Unit,
){

    LaunchedEffect(Unit) {
        onEvent(ThingEvent.ToggleArchive(false))
    }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(HemiusColors.current.background)
            .padding(0.dp),
    ) {
        Column (modifier = Modifier
            .wrapContentSize(Alignment.TopStart)
            .background(HemiusColors.current.background)
            .pointerInput(state) {
                detectHorizontalDragGestures { change, dragAmount ->
                    println(dragAmount)
                    val folders = (listOf(Folder(ThingViewModel.ALL_FOLDERS_ID, "Все")) + state.folders.map { it.first })

                    val selectedFolderIndex = folders.indexOfFirst { it.id == state.selectedFolderId }

                    if (dragAmount > 40) {
                        val previousFolder =
                            if (selectedFolderIndex > 0) folders[selectedFolderIndex - 1] else null
                        if (previousFolder != null) {
                            onEvent(ThingEvent.SelectFolder(previousFolder.id))
                        }
                    } else if (dragAmount < -40) {
                        val nextFolder =
                            if (selectedFolderIndex < folders.size - 1) folders[selectedFolderIndex + 1] else null
                        if (nextFolder != null) {
                            onEvent(ThingEvent.SelectFolder(nextFolder.id))
                        }
                    }
                }
            }
        ) {
            val animationSpec = tween<IntOffset>(
                durationMillis = 300,
                easing = FastOutSlowInEasing
            )
            val animationSpecIntSize = tween<IntSize>(
                durationMillis = 300,
                easing = FastOutSlowInEasing
            )
            Surface(
                modifier = Modifier
                    .background(HemiusColors.current.background)
            ){
                AnimatedVisibility(
                    visible = !state.isThingSelected,
                    enter = slideInVertically(initialOffsetY = { -it }, animationSpec = animationSpec),
                    exit = slideOutVertically(targetOffsetY = { -it }, animationSpec = animationSpec)
                ) {
                    TopMenu(
                        onArchiveClick = onArchiveClick,
                        onSearchClick = onSearchClick
                    )
                }
                AnimatedVisibility(
                    visible = state.isThingSelected,
                    enter = slideInVertically(initialOffsetY = { it }, animationSpec = animationSpec),
                    exit = slideOutVertically(targetOffsetY = { it }, animationSpec = animationSpec)
                ) {
                    if (state.selectedFolderId == -1) {
                        TopMenuOptions(
                            onToFolderClick = onToFolderClick,
                            onDeleteClick = onDeleteClick,
                            onToArchiveClick = onToArchiveClick,
                        )
                    } else {
                        TopMenuFolderOptions(
                            onFromFolderDeleteClick = onFromFolderDeleteClick,
                            onModeToFolderClick = onModeToFolderClick,
                        )
                    }
                }
            }
            AnimatedVisibility(
                visible = !state.isThingSelected,
                enter = expandVertically(expandFrom = Alignment.Top, animationSpec = animationSpecIntSize),
                exit = shrinkVertically(shrinkTowards = Alignment.Top,animationSpec = animationSpecIntSize)
            ) {
                FoldersMenu(
                    folders = (listOf(Folder(ThingViewModel.ALL_FOLDERS_ID, "Все")) + state.folders.map { it.first }),
                    selectedFolderId  = state.selectedFolderId,
                    onFolderSelected = { folderId -> onEvent(ThingEvent.SelectFolder(folderId)) }
                )
            }
            ThingsSurface(
                onThingOpenClick = onThingOpenClick,
                state = state,
                onEvent = onEvent,
                things = state.things
            )
        }

        Column (modifier = Modifier.wrapContentSize(Alignment.BottomStart)) {
            BottomMenu(
                onHomeClick = onHomeClick,
                onSettingsClick = onSettingsClick,
                onFoldersClick = onFoldersClick,
                onCameraClick = onCameraClick,
            )
        }
    }
}