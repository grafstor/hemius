package com.example.hemius.surfaces

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.hemius.R
import com.example.hemius.components.HemiusTextField
import com.example.hemius.components.PhotoButton
import com.example.hemius.components.TextButton
import com.example.hemius.database.events.ThingEvent
import com.example.hemius.database.states.ThingState
import com.example.hemius.ui.theme.HemiusColors

@Composable
fun CreateThingSurface(
    onBackClick : () -> Unit,
    onRephotoClick: () -> Unit,
    onCreateClick : () -> Unit,
    state: ThingState,
    onEvent: (ThingEvent) -> Unit,
) {
    Surface (
        modifier = Modifier.background(HemiusColors.current.background)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(25.dp)
            ) {
                state.image?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = null,
                    )
                }
                PhotoButton(
                    onClick = onRephotoClick,
                    iconId = R.drawable.ic_rephoto,
                    color = HemiusColors.current.fontSecondary,
                )

                Column (modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(18.dp),
                ) {
                    HemiusTextField(
                        value = state.name,
                        onValueChange = {
                            onEvent(ThingEvent.SetName(it))
                        },
                        placeholder = {
                            Text(
                                modifier = Modifier
                                    .height(35.dp),
                                text = "Название предмета",
                                color = HemiusColors.current.fontSecondary,
                                style = MaterialTheme.typography.titleLarge
                            )
                        },
                        textStyle = MaterialTheme.typography.titleLarge,
                    )
                    HemiusTextField(
                        value = state.description,
                        onValueChange = {
                            onEvent(ThingEvent.SetDescription(it))
                        },
                        placeholder = {
                            Text(
                                modifier = Modifier
                                    .height(28.dp),
                                text = "Лор предмета",
                                color = HemiusColors.current.fontSecondary,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        textStyle = MaterialTheme.typography.bodyMedium,
                    )
                }
                Column (modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(18.dp),
                ) {
                    val is_filled = state.description != "" && state.name != ""
                    TextButton(
                        onClick = {
                            onEvent(ThingEvent.Save)
                            onCreateClick()
                        },
                        text = "Добавить",
                        fontColor = if (is_filled) HemiusColors.current.background else HemiusColors.current.font,
                        backgroundColor = if (is_filled) HemiusColors.current.blueFirst else HemiusColors.current.background,
                    )
                    TextButton(
                        onClick = {
                            onEvent(ThingEvent.Save)
                            onBackClick()
                        },
                        text = "Отменить",
                        fontColor = HemiusColors.current.redFirst
                    )
                }
            }
        }
    }

}