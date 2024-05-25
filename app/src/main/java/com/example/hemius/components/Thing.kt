@file:OptIn(ExperimentalFoundationApi::class)

package com.example.hemius.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import coil.size.Scale
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.example.hemius.R
import com.example.hemius.database.entities.Thing
import com.example.hemius.database.events.ThingEvent
import com.example.hemius.database.states.ThingState
import com.example.hemius.ui.theme.HemiusColors
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

@Composable
fun ThingBox (
    thing: Thing,

    isSelected: Boolean,

    onItemClick: (Thing) -> Unit,
    onItemPress: (Thing) -> Unit,

    onEvent: (ThingEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .wrapContentSize(Alignment.TopStart)
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = {})
            .background(if (!isSelected) HemiusColors.current.background else HemiusColors.current.select)
            .combinedClickable(
                onClick = {
                    onEvent(ThingEvent.OpenThing(thing))
                    onItemClick(thing)
                },
                onLongClick = {
                    onItemPress(thing)
                }
            )
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(thing.imagePath)
                .crossfade(true)
                .build(),

            contentDescription = null,
            contentScale = ContentScale.Inside,
            modifier = Modifier
                .padding(start = 9.dp, top = 9.dp, end = 9.dp),
        )

        Text(
            modifier = Modifier
                .padding(start = 9.dp, top = 3.dp, end = 9.dp, bottom = 3.dp)
                .height(20.dp),
            text = thing.name!!,
            color = HemiusColors.current.fontSecondary,
            style = MaterialTheme.typography.labelSmall
        )
    }

}
