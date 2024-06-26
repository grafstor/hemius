package com.example.hemius.surfaces

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.hemius.components.BottomMenu
import com.example.hemius.components.FolderButton
import com.example.hemius.components.TopMenuBack
import com.example.hemius.database.entities.Folder
import com.example.hemius.database.events.ThingEvent
import com.example.hemius.database.states.ThingState
import com.example.hemius.ui.theme.HemiusColors

@Composable
fun FoldersSurface(
    onBackClick : () -> Unit,

    onHomeClick : () -> Unit = {},
    onFoldersClick : () -> Unit = {},
    onSettingsClick : () -> Unit = {},
    onCameraClick : () -> Unit = {},

    state: ThingState,
    onEvent: (ThingEvent) -> Unit,
) {
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
                            onEvent(ThingEvent.SelectFolder(folder.first.id))
                            onHomeClick()
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