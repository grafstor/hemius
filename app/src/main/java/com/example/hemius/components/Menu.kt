package com.example.hemius.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.zIndex
import com.example.hemius.R
import com.example.hemius.database.entities.Folder
import com.example.hemius.database.events.ThingEvent
import com.example.hemius.database.states.ThingState
import com.example.hemius.ui.theme.HemiusColors
import kotlinx.coroutines.launch

@Composable
fun FoldersMenu(
    folders: List<Folder>,
    selectedFolderId: Int,
    onFolderSelected: (Int) -> Unit
) {
    Box {
        val scrollState = rememberLazyListState()
//        LaunchedEffect(selectedFolderId) {
//            scrollState.animateScrollToItem(index)
//        }
        val coroutineScope = rememberCoroutineScope()

        LaunchedEffect(selectedFolderId) {
            // Найдите индекс выбранной папки
            val index = folders.indexOfFirst { it.id == selectedFolderId }
            if (index != -1) {
                coroutineScope.launch {
                    scrollState.animateScrollToItem(index)
                }
            }
        }
        LazyRow(
            state = scrollState,
            modifier = Modifier
//                .horizontalScroll(scrollState)
                .height(87.dp)
                .fillMaxWidth()
                .background(HemiusColors.current.background)
                .align(Alignment.TopStart)
                .padding(horizontal = 18.dp, vertical = 0.dp) ,
            horizontalArrangement = Arrangement.spacedBy(9.dp) ,
        ) {
//            items(folders) { folder ->
            itemsIndexed(items = folders, key = { index, thing -> thing.id }) { index, folder ->
                val isSelected = selectedFolderId == folder.id

                Box(
                    modifier = Modifier
                        .height(87.dp)
                        .wrapContentHeight()
                        .align(
                            Alignment.Center
                        ) ,
                ) {
                    TextButton(
                        text = folder.name,
                        fontColor = if (isSelected) HemiusColors.current.blueFirst else HemiusColors.current.font,
                        onClick = {
                            onFolderSelected(folder.id)
                        }
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

    state: ThingState,
    onEvent: (ThingEvent) -> Unit,
) {
    Box (modifier = Modifier
        .height(87.dp)
//        .background(Color.Green)
        .wrapContentSize(Alignment.BottomStart)
    ) {
        Row(
            modifier = Modifier
                .height(87.dp)
                .fillMaxWidth()
//                .background(Color.Red)
                .background(HemiusColors.current.background)
                .padding(horizontal = 18.dp, vertical = 0.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            HemiusTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.search,
                onValueChange = {
                    onEvent(ThingEvent.SetSearch(it))
                },
                placeholder = {
                    Text(
                        modifier = Modifier
                            .height(28.dp),
                        text = "Поиск",
                        color = HemiusColors.current.fontSecondary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                textStyle = MaterialTheme.typography.bodyMedium,
            )
//            MenuButton(svgId = R.drawable.ic_search, onItemClick = onSearchClick)
        }
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(1.dp)
//                .align(Alignment.BottomStart)
//                .background(color = HemiusColors.current.lines)
//        )
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
fun PopupBox(showPopup: Boolean, onClickOutside: () -> Unit, content: @Composable() () -> Unit) {
    if (showPopup) {
        Box(
            modifier = Modifier
                .offset(-70.dp, 180.dp)
                .background(Color.Green)
                .zIndex(10F),
            contentAlignment = Alignment.Center
        ) {
            Popup(
                alignment = Alignment.Center,
                properties = PopupProperties(
                    excludeFromSystemGesture = true,
                ),
                onDismissRequest = { onClickOutside() }
            ) {
                Column(
                    Modifier
                        .border(1.dp, HemiusColors.current.lines, RoundedCornerShape(26.dp))
                        .clip(RoundedCornerShape(26.dp))
                        .wrapContentSize()
                        .background(Color.White)
                        .clip(RoundedCornerShape(4.dp)),
//                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    content()
                }
            }
        }
    }
}
@Composable
fun TopThingOptions(
    onHomeClick : () -> Unit = {},

    onToFolderClick : () -> Unit = {},
    onDeleteClick : () -> Unit = {},
    onToArchiveClick : () -> Unit = {},
    onToUnarchiveClick : () -> Unit = {},

    state: ThingState,
    onEvent: (ThingEvent) -> Unit,
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
            var expanded by remember { mutableStateOf(false) }
            var is_closing by remember { mutableStateOf(false) }

            Box {
                MenuButton(
                    svgId = R.drawable.ic_options,
                    onItemClick = {
                        if (!is_closing){
                            expanded = !expanded
                        }
                        else {
                            expanded = false
                            is_closing = false
                        }
                    })

                PopupBox(
                    showPopup = expanded,
                    onClickOutside = {
                        expanded = false
                        is_closing = true
                                     },
                    content = {
                        TextOptionsButton(
                            text = "В папку",
                            fontColor = HemiusColors.current.font,
                            onClick = {
                                onEvent(ThingEvent.SelectThing(state.thing!!))
                                onEvent(ThingEvent.SaveSelected)
                                onEvent(ThingEvent.DeselectSelected)
                                onToFolderClick()
                            }
                        )
                        TextOptionsButton(
                            text = "Удалить",
                            fontColor = HemiusColors.current.redFirst,
                            onClick = {
                                onEvent(ThingEvent.SelectThing(state.thing!!))
                                onDeleteClick()
                                onHomeClick()
                            }
                        )
                        if (!state.thing!!.isArchived) {
                            TextOptionsButton(
                                text = "Архивировать",
                                fontColor = HemiusColors.current.font,
                                onClick = {
                                    onEvent(ThingEvent.SelectThing(state.thing))
                                    onToArchiveClick()
                                    onHomeClick()
                                }
                            )
                        } else{
                            TextOptionsButton(
                                text = "Разархивировать",
                                fontColor = HemiusColors.current.font,
                                onClick = {
                                    onEvent(ThingEvent.SelectThing(state.thing))
                                    onToUnarchiveClick()
                                    onHomeClick()
                                }
                            )
                        }
                    }
                )
            }
        }
    }
}


