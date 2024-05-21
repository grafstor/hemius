package com.example.hemius.surfaces

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hemius.components.BottomMenu
import com.example.hemius.components.FolderButton
import com.example.hemius.components.HemiusTextField
import com.example.hemius.components.TextButton
import com.example.hemius.components.TopMenuBack
import com.example.hemius.database.events.ThingEvent
import com.example.hemius.database.states.ThingState
import com.example.hemius.ui.theme.HemiusColors

@Composable
fun AddToFolderSurface(
    onBackClick : () -> Unit,

    onHomeClick : () -> Unit = {},
    onFoldersClick : () -> Unit = {},
    onSettingsClick : () -> Unit = {},
    onCameraClick : () -> Unit = {},

    state: ThingState,
    onEvent: (ThingEvent) -> Unit,
) {
    DisposableEffect(Unit) {
        onDispose {
            onEvent(ThingEvent.ToggleFolderCreation(false))
        }
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
        ) {
            TopMenuBack(
                onBackClick = onBackClick
            )

            Column(
                modifier = Modifier
                    .background(HemiusColors.current.background)
                    .verticalScroll(rememberScrollState())
                    .wrapContentSize(Alignment.TopStart)
                    .padding(9.dp),
                verticalArrangement = Arrangement.spacedBy(9.dp),
            ) {
                state.folders.forEach { folder ->
                    FolderButton(
                        folder = folder,
                        onClick = {
                            onEvent(ThingEvent.ToFolderSelected(folder.first.id))
                            onHomeClick()
                        }
                    )
                }
                if (state.isFolderCreation){
                    HemiusTextField(
                        value = state.folderName,
                        onValueChange = {
                            onEvent(ThingEvent.SetFolderName(it))
                        },
                        placeholder = {
                            Text(
                                modifier = Modifier
                                    .height(28.dp),
                                text = "Название папки",
                                color = HemiusColors.current.fontSecondary,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        textStyle = MaterialTheme.typography.bodyMedium,
                    )
                    TextButton(
                        onClick = {
                            onEvent(ThingEvent.SaveFolder)
                            onEvent(ThingEvent.ToggleFolderCreation(false))
                        },
                        text = "Добавить",
                        fontColor = HemiusColors.current.font
                    )
                    TextButton(
                        onClick = {
                            onEvent(ThingEvent.ToggleFolderCreation(false))
                        },
                        text = "Отменить",
                        fontColor = HemiusColors.current.redFirst
                    )
                }
                else{
                    TextButton(
                        text = "+",
                        fontColor = HemiusColors.current.font,
                        onClick = {
                            onEvent(ThingEvent.ToggleFolderCreation(true))
                        }
                    )
                }
            }
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