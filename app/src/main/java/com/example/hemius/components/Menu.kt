package com.example.hemius.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.hemius.R
import com.example.hemius.database.entities.Folder
import com.example.hemius.ui.theme.HemiusColors


@Composable
fun FoldersMenu(
    folders: List<Folder>,
    selectedFolderId: Int,
    onFolderSelected: (Int) -> Unit
) {
    Box {
        val scrollState = rememberScrollState()
        Row(
            modifier = Modifier
                .horizontalScroll(scrollState)
                .height(87.dp)
                .fillMaxWidth()
                .background(HemiusColors.current.background)
                .align(Alignment.TopStart)
                .padding(horizontal = 18.dp, vertical = 0.dp) ,
            horizontalArrangement = Arrangement.spacedBy(9.dp) ,
        ) {
            folders.forEach { folder ->
                val isSelected = selectedFolderId == folder.id
                Box(
                    modifier = Modifier
                        .height(87.dp)
                        .wrapContentHeight()
                        .align(
                            Alignment.CenterVertically
                        ) ,
                ) {
                    TextButton(
                        text = folder.name,
                        fontColor = if (isSelected) HemiusColors.current.blueFirst else HemiusColors.current.font,
                        onClick = { onFolderSelected(folder.id) }
                    )
                }
            }
        }

    }
}

@Composable
fun TopMenu(
    onArchiveClick : () -> Unit = {},
    onSearchClick : () -> Unit = {},
) {
    Box (modifier = Modifier
        .height(87.dp)
        .wrapContentSize(Alignment.BottomStart)
    ) {
        Row(
            modifier = Modifier
                .height(87.dp)
                .fillMaxWidth()
                .background(HemiusColors.current.background)
                .padding(horizontal = 18.dp, vertical = 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            MenuButton(svgId = R.drawable.ic_archive, onItemClick = onArchiveClick)
            MenuButton(svgId = R.drawable.ic_search, onItemClick = onSearchClick)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .align(Alignment.BottomStart)
                .background(color = HemiusColors.current.lines)
        )
    }
}

@Composable
fun TopMenuSearch(
    onSearchClick : () -> Unit = {},
) {
    Box (modifier = Modifier
        .height(87.dp)
        .wrapContentSize(Alignment.BottomStart)
    ) {
        Row(
            modifier = Modifier
                .height(87.dp)
                .fillMaxWidth()
                .background(HemiusColors.current.background)
                .padding(horizontal = 18.dp, vertical = 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {

            MenuButton(svgId = R.drawable.ic_search, onItemClick = onSearchClick)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .align(Alignment.BottomStart)
                .background(color = HemiusColors.current.lines)
        )
    }
}

@Composable
fun TopMenuBack(
    onBackClick : () -> Unit = {},
) {
    Box (modifier = Modifier
        .height(87.dp)
        .wrapContentSize(Alignment.BottomStart)
    ) {
        Row(
            modifier = Modifier
                .height(87.dp)
                .fillMaxWidth()
                .background(HemiusColors.current.background)
                .padding(horizontal = 18.dp, vertical = 0.dp),
            horizontalArrangement = Arrangement.Start,
        ) {
            MenuButton(svgId = R.drawable.ic_back, onItemClick = onBackClick)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .align(Alignment.BottomStart)
                .background(color = HemiusColors.current.lines)
        )
    }
}

@Composable
fun TopMenuOptions(
    onToFolderClick : () -> Unit = {},
    onDeleteClick : () -> Unit = {},
    onToArchiveClick : () -> Unit = {},
) {
    Box (modifier = Modifier
        .height(87.dp)
        .wrapContentSize(Alignment.BottomStart)
    ) {
        val scrollState = rememberScrollState()
        Row(
            modifier = Modifier
                .horizontalScroll(scrollState)
                .height(87.dp)
                .fillMaxWidth()
                .background(HemiusColors.current.background)
                .align(Alignment.TopStart)
                .padding(horizontal = 18.dp, vertical = 0.dp) ,
            horizontalArrangement = Arrangement.spacedBy(9.dp) ,
        ) {

            Box(
                modifier = Modifier
                    .height(87.dp)
                    .wrapContentHeight()
                    .align(
                        Alignment.CenterVertically
                    ),
            ) {
                TextButton(
                    text = "В папку",
                    fontColor = HemiusColors.current.font,
                    onClick = onToFolderClick
                )
            }
            Box(
                modifier = Modifier
                    .height(87.dp)
                    .wrapContentHeight()
                    .align(
                        Alignment.CenterVertically
                    ) ,
            ) {
                TextButton(
                    text = "Удалить" ,
                    fontColor = HemiusColors.current.redFirst,
                    onClick = onDeleteClick
                )
            }
            Box(
                modifier = Modifier
                    .height(87.dp)
                    .wrapContentHeight()
                    .align(
                        Alignment.CenterVertically
                    ) ,
            ) {
                TextButton(
                    text = "Архивировать" ,
                    fontColor = HemiusColors.current.font,
                    onClick = onToArchiveClick
                )
            }

        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .align(Alignment.BottomStart)
                .background(color = HemiusColors.current.lines)
        )
    }
}

@Composable
fun TopMenuFolderOptions(
    onFromFolderDeleteClick : () -> Unit = {},
    onModeToFolderClick : () -> Unit = {},
) {
    Box (modifier = Modifier
        .height(87.dp)
        .wrapContentSize(Alignment.BottomStart)
    ) {
        val scrollState = rememberScrollState()
        Row(
            modifier = Modifier
                .horizontalScroll(scrollState)
                .height(87.dp)
                .fillMaxWidth()
                .background(HemiusColors.current.background)
                .align(Alignment.TopStart)
                .padding(horizontal = 18.dp, vertical = 0.dp) ,
            horizontalArrangement = Arrangement.spacedBy(9.dp) ,
        ) {

            Box(
                modifier = Modifier
                    .height(87.dp)
                    .wrapContentHeight()
                    .align(
                        Alignment.CenterVertically
                    ),
            ) {
                TextButton(
                    text = "Удалить из папки",
                    fontColor = HemiusColors.current.redFirst,
                    onClick = onFromFolderDeleteClick
                )
            }
            Box(
                modifier = Modifier
                    .height(87.dp)
                    .wrapContentHeight()
                    .align(
                        Alignment.CenterVertically
                    ) ,
            ) {
                TextButton(
                    text = "Переместить" ,
                    fontColor = HemiusColors.current.font,
                    onClick = onModeToFolderClick
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .align(Alignment.BottomStart)
                .background(color = HemiusColors.current.lines)
        )
    }
}

@Composable
fun BottomMenu(
    onHomeClick : () -> Unit = {},
    onSettingsClick : () -> Unit = {},
    onFoldersClick : () -> Unit = {},
    onCameraClick : () -> Unit = {},
) {
    val animationSpec = tween<IntSize>(
        durationMillis = 200,
        easing = FastOutSlowInEasing
    )
    var isMenuOpen by remember { mutableStateOf<Boolean>(false) }
    Box (modifier = Modifier
        .wrapContentSize(Alignment.BottomStart)
        .background(HemiusColors.current.background)
    ) {
        Column {
            AnimatedVisibility(
                visible = isMenuOpen,
                enter = expandVertically(expandFrom = Alignment.Bottom, animationSpec = animationSpec),
                exit = shrinkVertically(shrinkTowards = Alignment.Bottom,animationSpec = animationSpec)
            ) {
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                    ,
                ){
                    TextMenuButton(text = "Настройки", onClick = onSettingsClick)
                    TextMenuButton(text = "Папки",  onClick = onFoldersClick)
                }
            }
            Row(
                modifier = Modifier
                    .height(87.dp)
                    .fillMaxWidth()
                    .background(HemiusColors.current.background)
                    .padding(horizontal = 18.dp, vertical = 0.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                MenuButton(
                    svgId = R.drawable.ic_home,
                    onItemClick = onHomeClick)
                Box(
                    modifier = Modifier
                        .height(87.dp)
                        .wrapContentHeight()
                        .align(
                            Alignment.CenterVertically
                        ),
                ) {
                    PhotoButton(
                        onClick = onCameraClick,
                        iconId = R.drawable.ic_photo,
                        color = HemiusColors.current.blueFirst,
                    )
                }
                MenuButton(
                    svgId = R.drawable.ic_menu,
                    onItemClick = {
                        isMenuOpen = !isMenuOpen
                    }
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .align(Alignment.TopEnd)
                .background(color = HemiusColors.current.lines)
        )
    }
}


@Composable
fun TopThingOptions(
    onOptionsClick : () -> Unit = {},
) {
    Box (modifier = Modifier
        .height(87.dp)
        .wrapContentSize(Alignment.BottomStart)
    ) {
        Row(
            modifier = Modifier
                .height(87.dp)
                .fillMaxWidth()
                .background(HemiusColors.current.background)
                .padding(horizontal = 18.dp, vertical = 0.dp),
            horizontalArrangement = Arrangement.End,
        ) {

            MenuButton(svgId = R.drawable.ic_options, onItemClick = onOptionsClick)
        }
    }
}