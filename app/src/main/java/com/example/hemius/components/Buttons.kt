package com.example.hemius.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.hemius.R
import com.example.hemius.ui.theme.HemiusColors

@Composable
fun TextButton(
    text : String = "Текстовая Кнопка",
    fontColor : Color,
    onClick : () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.TopStart)
            .clip(RoundedCornerShape(26.dp))
            .border(1.dp, HemiusColors.current.lines, RoundedCornerShape(26.dp))
    ) {
        Box(
            modifier = Modifier
                .background(HemiusColors.current.background)
                .clickable(onClick = onClick)
        ) {
            Box(
                modifier = Modifier
                    .padding(19.5.dp),
            ) {
                Text(
                    modifier = Modifier
                        .height(28.dp),
                    text = text,
                    color = fontColor,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

        }
    }
}

@Composable
fun PhotoButton(
    onClick : () -> Unit = {},
    iconId : Int,
    color : Color,
) {
    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.TopStart)
            .clip(RoundedCornerShape(26.dp))
            .border(1.dp, HemiusColors.current.lines, RoundedCornerShape(26.dp))
    ) {
        Box(
            modifier = Modifier
                .background(HemiusColors.current.background)
                .clickable(onClick = onClick)
        ) {
            Box(
                modifier = Modifier
                    .padding(19.5.dp),
            ) {
                Icon(
                    painter = painterResource(iconId), // Здесь используется ваш ресурс SVG
                    tint = color,
                    contentDescription = "Photo Icon"
                )
            }

        }
    }
}

@Composable
fun MenuButton(
    svgId: Int,
    onItemClick: () -> Unit
) {
    val buttonSize = 87.dp

    IconButton(
        onClick = { onItemClick() },
        modifier = Modifier
            .width(buttonSize)
            .height(buttonSize)
    ) {
        Icon(
            painter = painterResource(id = svgId),
            tint = HemiusColors.current.fontSecondary,
            contentDescription = "Icon"
        )
    }
}

@Composable
fun TextMenuButton(
    text : String = "Текстовая Кнопка",
    onClick : () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(HemiusColors.current.background)
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .padding(19.5.dp)
                .align(Alignment.TopEnd)
            ,
        ) {
            Text(
                modifier = Modifier
                    .height(28.dp),
                text = text,
                color = HemiusColors.current.font,
                style = MaterialTheme.typography.bodyMedium
            )
        }

    }
}
