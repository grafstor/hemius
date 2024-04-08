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

import com.example.hemius.components.BottomMenu
import com.example.hemius.components.FoldersMenu
import com.example.hemius.components.TopMenu
import com.example.hemius.components.TopMenuFolderOptions
import com.example.hemius.components.TopMenuOptions
import com.example.hemius.ui.theme.HemiusColors

@Composable
fun MainSurface (
    onArchiveClick : () -> Unit = {},
    onSearchClick : () -> Unit = {},

    onToFolderClick : () -> Unit = {},
    onDeleteClick : () -> Unit = {},
    onToArchiveClick : () -> Unit = {},

    onHomeClick : () -> Unit = {},
    onFoldersClick : () -> Unit = {},
    onSettingsClick : () -> Unit = {},

    onFromFolderDeleteClick : () -> Unit = {},
    onModeToFolderClick : () -> Unit = {},
){
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(HemiusColors.current.background)
            .padding(0.dp),
    ) {
        Column (modifier = Modifier
            .wrapContentSize(Alignment.TopStart)
            .background(HemiusColors.current.background)
        ) {
            val animationSpec = tween<IntOffset>(
                durationMillis = 300,
                easing = FastOutSlowInEasing
            )
            val animationSpecIntSize = tween<IntSize>(
                durationMillis = 300,
                easing = FastOutSlowInEasing
            )

            val folders = listOf("Все", "Игрушки", "Важное")

            var isThingSelected by remember { mutableStateOf<Boolean>(false) }
            var selectedItem by remember { mutableStateOf<String?>("Все") }

            val onItemClick: (String) -> Unit = { selectedFolder ->
                selectedItem = selectedFolder
            }
            Surface(
                modifier = Modifier
                    .background(HemiusColors.current.background)
            ){
                AnimatedVisibility(
                    visible = !isThingSelected,
                    enter = slideInVertically(initialOffsetY = { -it }, animationSpec = animationSpec),
                    exit = slideOutVertically(targetOffsetY = { -it }, animationSpec = animationSpec)
                ) {
                    TopMenu(
                        onArchiveClick = onArchiveClick,
                        onSearchClick = onSearchClick
                    )
                }
                AnimatedVisibility(
                    visible = isThingSelected,
                    enter = slideInVertically(initialOffsetY = { it }, animationSpec = animationSpec),
                    exit = slideOutVertically(targetOffsetY = { it }, animationSpec = animationSpec)
                ) {
                    if (selectedItem == "Все") {
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
                visible = !isThingSelected,
                enter = expandVertically(expandFrom = Alignment.Top, animationSpec = animationSpecIntSize),
                exit = shrinkVertically(shrinkTowards = Alignment.Top,animationSpec = animationSpecIntSize)
            ) {
                FoldersMenu(
                    folders = folders,
                    selectedItem = selectedItem,
                    onItemClick = onItemClick
                )
            }
            ThingsSurface(
                onSelectUpdate = {update -> isThingSelected = update}
            )
        }

        Column (modifier = Modifier.wrapContentSize(Alignment.BottomStart)) {
            BottomMenu(
                onHomeClick = onHomeClick,
                onSettingsClick = onSettingsClick,
                onFoldersClick = onFoldersClick,
            )
        }
    }
}