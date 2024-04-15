package com.example.hemius.surfaces

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.example.hemius.R
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
                verticalArrangement = Arrangement.spacedBy(25.dp)
            ) {
                state.image?.let {
                    Image(
                        //                    modifier = Modifier
                        //                        .padding(start = 9.dp, top = 9.dp, end = 9.dp)
                        //                    ,
                        //                .background(Color.Red)
                        //            painter = image,
                        //            image = image,
                        bitmap = it.asImageBitmap(),
                        contentDescription = null,
                    )
                }
                PhotoButton(
                    onClick = onRephotoClick,
                    iconId = R.drawable.ic_rephoto,
                    color = HemiusColors.current.fontSecondary,
                )
                OutlinedTextField(
                    modifier = Modifier
                        .background(HemiusColors.current.background)
                        .wrapContentWidth()
                    ,
                    value = state.name,
                    onValueChange = {
                        onEvent(ThingEvent.SetName(it))
                    },
                    placeholder = {
                        Text(text = "Название предмета")
                    }
                )
                OutlinedTextField(
                    modifier = Modifier
                        .background(HemiusColors.current.background)
                        .wrapContentWidth()
                    ,
                    value = state.description,
                    onValueChange = {
                        onEvent(ThingEvent.SetDescription(it))
                    },
                    placeholder = {
                        Text(text = "Лор предмета")
                    }
                )
                TextButton(
                    onClick = {
                        onEvent(ThingEvent.Save)
                        onCreateClick()
                    },
                    text = "Добавить",
                    fontColor = HemiusColors.current.font
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