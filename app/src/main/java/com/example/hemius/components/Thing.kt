package com.example.hemius.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.hemius.ui.theme.HemiusColors

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Thing (
    id : Int,
    onItemClick: (Int) -> Unit,
    onItemPress: (Int) -> Unit,
    text : String = "Название вещи",
    selectedIds : List<Int>,
    imageId: Int,
) {
    var isSelected = selectedIds.contains(id)
    Column (
        modifier = Modifier
            .wrapContentSize(Alignment.TopStart)
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = {})
            .background(if (!isSelected) HemiusColors.current.background else HemiusColors.current.select)
            .combinedClickable(
                onClick = {
                    onItemClick(id)
                },
                onLongClick = {
                    onItemPress(id)
                }
            )
    ) {
        Image(
            modifier = Modifier
                .padding(start = 9.dp, top = 9.dp, end = 9.dp)
                .size(120.dp),
            painter = painterResource(id = imageId),
            contentDescription = "Photo Icon"
        )
        Text(
            modifier = Modifier
                .padding(start = 9.dp, top = 3.dp, end = 9.dp, bottom = 3.dp)
                .height(20.dp),
            text = text,
            color = HemiusColors.current.fontSecondary,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Immutable
data class TestThing(
    val name: String,
    val description: String,
    val imageId: Int,
)
