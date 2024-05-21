package com.example.hemius.surfaces

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.dp
import com.example.hemius.components.BottomMenu
import com.example.hemius.components.TextButton
import com.example.hemius.components.TopMenuBack
import com.example.hemius.database.events.ThingEvent
import com.example.hemius.database.states.ThingState
import com.example.hemius.ui.theme.HemiusColors

@Composable
fun SettingsSurface(
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
                Row (
                    horizontalArrangement = Arrangement.spacedBy(18.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(26.dp))
                        .border(1.dp, HemiusColors.current.lines, RoundedCornerShape(26.dp))
                        .padding(start = 19.5.dp, end = 19.5.dp),
                ){
                    Box(
                        modifier = Modifier
                            .padding(top=19.5.dp, bottom=19.5.dp)
                        ,

                    ) {
                        Text(
                            modifier = Modifier
                                .height(28.dp),
                            text = "Тема",
                            color = HemiusColors.current.font,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .clickable(onClick = { onEvent(ThingEvent.ToggleDarkTheme(false)) }),
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(top = 19.5.dp, bottom = 19.5.dp)
                                .height(35.dp),
                            text = "Светлая",
                            color = if (!state.isDarkTheme) HemiusColors.current.blueFirst else HemiusColors.current.font,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .clickable(onClick = { onEvent(ThingEvent.ToggleDarkTheme(true))  }),
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(top = 19.5.dp, bottom = 19.5.dp)
                                .height(35.dp),
                            text = "Тёмная",
                            color = if (state.isDarkTheme) HemiusColors.current.blueFirst else HemiusColors.current.font,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                }
                TextButton(
                    text="Экспортировать склад",
                    fontColor = HemiusColors.current.font
                )

                    Box(
                        modifier = Modifier
                            .padding(19.5.dp),
                    ) {
                        Text(
                            text = "Автор: Георгий Силкин\n2024",
                            color = HemiusColors.current.fontSecondary,
                            style = MaterialTheme.typography.labelSmall
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