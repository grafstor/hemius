package com.example.hemius.surfaces

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hemius.R
import com.example.hemius.TestThing
import com.example.hemius.Thing
import com.example.hemius.ui.theme.HemiusColors

@Composable
fun ThingsSurface (
    onSelectUpdate: (Boolean) -> Unit,
){
    Surface (
        modifier = Modifier
            .background(HemiusColors.current.background)
    ) {
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
                val things = listOf(
                    TestThing(
                        name = "Спинер",
                        description = "Это мой любимый спинер",
                        imageId = R.drawable.im_spinner,
                    ),
                    TestThing(
                        name = "Спинер",
                        description = "Это мой любимый спинер",
                        imageId = R.drawable.im_spinner,
                    ),
                    TestThing(
                        name = "Спинер",
                        description = "Это мой любимый спинер",
                        imageId = R.drawable.im_spinner,
                    )
                )
                var selectedThings by remember { mutableStateOf(listOf<Int>()) }

                fun toggleItemInList(id : Int) {
                    selectedThings = if (selectedThings.contains(id)) {
                        selectedThings.filterNot { it == id }
                    } else {
                        selectedThings.toMutableList().apply { add(id) }
                    }
                    onSelectUpdate(selectedThings.isNotEmpty())
                }

                fun onThingClick(id : Int) {
                    if (selectedThings.isNotEmpty()) {
                        toggleItemInList(id)
                    }
                }

                things.forEachIndexed { index, thing ->
                    Thing(
                        id = index,
                        text = "Спинер",
                        imageId = R.drawable.im_spinner,
                        selectedIds = selectedThings,
                        onItemClick = { id -> onThingClick(id) },
                        onItemPress = { id -> toggleItemInList(id) }
                    )
                }
            }
        }
    }
}
