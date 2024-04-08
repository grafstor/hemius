package com.example.hemius.surfaces

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hemius.components.BottomMenu
import com.example.hemius.components.TextButton
import com.example.hemius.components.TopMenuBack
import com.example.hemius.ui.theme.HemiusColors

@Composable
fun SettingsSurface(
    onBackClick : () -> Unit,

    onHomeClick : () -> Unit = {},
    onFoldersClick : () -> Unit = {},
    onSettingsClick : () -> Unit = {}
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
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.TopStart)
                        .padding(9.dp),
                ) {
                    TextButton(
                        fontColor = HemiusColors.current.font,
                        text = "Настройки"
                    )
                }
            }
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