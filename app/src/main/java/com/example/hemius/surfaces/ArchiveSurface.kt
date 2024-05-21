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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.core.graphics.get
import com.example.hemius.components.BottomMenu
import com.example.hemius.components.TextButton
import com.example.hemius.components.TopMenuBack
import com.example.hemius.database.events.ThingEvent
import com.example.hemius.database.states.ThingState
import com.example.hemius.ui.theme.HemiusColors

@Composable
fun ArchiveSurface(
    onThingOpenClick : () -> Unit = {},

    onBackClick : () -> Unit,

    onHomeClick : () -> Unit = {},
    onFoldersClick : () -> Unit = {},
    onSettingsClick : () -> Unit = {},
    onCameraClick : () -> Unit = {},

    state: ThingState,
    onEvent: (ThingEvent) -> Unit,
) {
    LaunchedEffect(Unit) {
        onEvent(ThingEvent.ToggleArchive(true))
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
            if (state.archivedFilter){
                ThingsSurface(
                    onThingOpenClick = onThingOpenClick,
                    things = state.things,
                    state = state,
                    onEvent = onEvent
                )
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