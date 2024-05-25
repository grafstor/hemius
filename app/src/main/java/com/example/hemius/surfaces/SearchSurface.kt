package com.example.hemius.surfaces

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hemius.components.BottomMenu
import com.example.hemius.components.TopMenuBack
import com.example.hemius.components.TopMenuSearch
import com.example.hemius.database.events.ThingEvent
import com.example.hemius.database.states.ThingState
import com.example.hemius.ui.theme.HemiusColors

@Composable
fun SearchSurface(
    onThingOpenClick : () -> Unit = {},

    state: ThingState,
    onEvent: (ThingEvent) -> Unit,
) {
    DisposableEffect(Unit) {
        onDispose {
            onEvent(ThingEvent.SetSearch(""))
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
            TopMenuSearch(
                state = state,
                onEvent = onEvent,
            )
            ThingsSurface(
                onThingOpenClick = onThingOpenClick,
                things = state.things,
                state = state,
                onEvent = onEvent
            )
        }

    }
}
