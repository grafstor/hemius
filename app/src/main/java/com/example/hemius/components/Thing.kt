@file:OptIn(ExperimentalFoundationApi::class)

package com.example.hemius.components

import android.graphics.Bitmap
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.hemius.ui.theme.HemiusColors

@Composable
fun ThingBox (
    id : Int,
    onItemClick: (Int) -> Unit,
    onItemPress: (Int) -> Unit,
    text : String = "Название вещи",
    selectedIds : List<Int>,
    image: ImageBitmap,
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
                .padding(start = 9.dp, top = 9.dp, end = 9.dp),
//                .background(Color.Red)
//            painter = image,
//            image = image,
            bitmap = image,
            contentDescription = null,
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
