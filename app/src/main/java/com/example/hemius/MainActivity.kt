package com.example.hemius

import androidx.compose.ui.graphics.Color
import android.os.Bundle
import android.widget.HorizontalScrollView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.hemius.ui.theme.Colors
import com.example.hemius.ui.theme.HemiusTheme


val HemiusColors = compositionLocalOf<Colors> {
    error("No Colors provided")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HemiusTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(0.dp),
                    color = HemiusColors.current.background
                ) {
                    val folders = listOf("Все", "Игрушки", "Важное")
                    var selectedItem by remember { mutableStateOf<String?>(null) }

                    val onItemClick: (String) -> Unit = { selectedFolder ->
                        selectedItem = selectedFolder
                    }
                    Column (modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
                        FoldersMenu(
                            folders = folders,
                            selectedItem = selectedItem,
                            onItemClick = onItemClick
                        )
                        FoldersMenu(
                            folders = folders,
                            selectedItem = selectedItem,
                            onItemClick = onItemClick
                        )
                    }
                    Column (modifier = Modifier.wrapContentSize(Alignment.BottomStart)) {
                        BottomMenu(onItemClick = { index -> })
                        BottomMenu(onItemClick = { index -> })
                    }
                }
            }
        }
    }
}

@Composable
fun FoldersMenu(
    folders: List<String>,
    selectedItem: String?,
    onItemClick: (String) -> Unit
) {
    Box {
            val scrollState = rememberScrollState()
            Row(
                modifier = Modifier
                    .horizontalScroll(scrollState)
                    .height(87.dp)
                    .fillMaxWidth()
                    .background(HemiusColors.current.background)
                    .align(Alignment.TopStart)
                    .padding(horizontal = 18.dp , vertical = 0.dp) ,
                horizontalArrangement = Arrangement.spacedBy(9.dp) ,
            ) {
                folders.forEach { folder ->
                    val isSelected = folder == selectedItem
                    Box(
                        modifier = Modifier
                            .height(87.dp)
                            .wrapContentHeight()
                            .align(
                                Alignment.CenterVertically
                            ) ,
                    ) {
                        TextButton(
                            text = folder ,
                            fontColor = if (isSelected) HemiusColors.current.blueFirst else HemiusColors.current.font,
                            onClick = { onItemClick(folder) }
                        )
                    }
                }
            }

    }
}



@Composable
fun BottomMenu(
    onItemClick : (Int) -> Unit,
) {
    Box (modifier = Modifier
        .height(87.dp)
        .wrapContentSize(Alignment.BottomStart)
        .background(Color.Green)) {
        //LINE 1 px

        Row(
            modifier = Modifier
                .height(87.dp)
                .fillMaxWidth()
                .background(HemiusColors.current.background)
                .padding(horizontal = 18.dp , vertical = 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            MenuButton(index = 1 , svgId = R.drawable.ic_home) {}
            Box (
                modifier = Modifier
                    .height(87.dp)
                    .wrapContentHeight()
                    .align(
                        Alignment.CenterVertically
                    ),
            ) {
                PhotoButton()
            }
            MenuButton(index = 1 , svgId = R.drawable.ic_menu) {}
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .align(Alignment.TopStart)
                .background(color = HemiusColors.current.lines)
        )
    }
}

@Composable
fun TextButton(
    text : String = "Текстовая Кнопка" ,
    fontColor : Color ,
    onClick : () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.TopStart)
            .clip(RoundedCornerShape(26.dp))
            .border(1.dp , HemiusColors.current.lines , RoundedCornerShape(26.dp))
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
    onClick : () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.TopStart)
            .clip(RoundedCornerShape(26.dp))
            .border(1.dp , HemiusColors.current.lines , RoundedCornerShape(26.dp))
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
                    painter = painterResource(id = R.drawable.ic_photo), // Здесь используется ваш ресурс SVG
                    tint = HemiusColors.current.blueFirst,
                    contentDescription = "Photo Icon"
                )
            }

        }
    }
}

@Composable
fun MenuButton(
    index: Int,
    svgId: Int,
    onItemClick: (Int) -> Unit
) {
    val buttonSize = 87.dp

    IconButton(
        onClick = { onItemClick(index) },
        modifier = Modifier
            .width(buttonSize)
            .height(buttonSize)
    ) {
        Icon(
            painter = painterResource(id = svgId), // Здесь используется ваш ресурс SVG
            tint = HemiusColors.current.fontSecondary,
            contentDescription = "Icon"
        )
    }
}