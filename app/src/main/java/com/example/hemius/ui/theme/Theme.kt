package com.example.hemius.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.example.hemius.HemiusColors

private val DarkColorScheme = darkColorScheme(
//    primary = WhiteFont,
//    secondary = WhiteSecondFont,
    primary = Color.Red,
    secondary = Color.Red,
    tertiary = Color.Red,
    background = Color.Red,
    surface = Color.Red,
    onPrimary = Color.Red,
    onSecondary  = Color.Red,
    onTertiary  = Color.Red,
    onBackground  = Color.Red,
    onSurface   = Color.Red,
)

private val LightColorScheme = lightColorScheme(
//    primary = BlackFont,
//    secondary = BlackSecondFont,
    primary = Color.Red,
    onPrimary = Color.Yellow,
    secondary = Color.Blue,
    onSecondary = Color.Cyan,
    background  = Color.Magenta,
    onBackground  = Color.LightGray,


)

@Immutable
class Colors(
    val font: Color,
    val fontSecondary: Color,

    val blueFirst: Color,
    val blueSecond: Color,

    val redFirst: Color,
    val redSecond: Color,

    val background: Color,
    val lines: Color,

    val select: Color,

    ){
}

val LightColors = Colors(
    font = BlackFont,
    fontSecondary = BlackSecondFont,

    blueFirst = BlueFirst,
    blueSecond = BlueSecond,

    redFirst = RedFirst,
    redSecond = RedSecond,

    background = WhiteBackground,
    lines = LightLines,

    select = SelectBackgroundLight
)

val DarkColors = Colors(
    font = WhiteFont,
    fontSecondary = WhiteSecondFont,

    blueFirst = BlueFirst,
    blueSecond = BlueSecond,

    redFirst = RedFirst,
    redSecond = RedSecond,

    background = BlackBackground,
    lines = LightLinesBlack,

    select = SelectBackgroundDark
)

@Composable
fun HemiusTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (isDarkTheme) {
        DarkColorScheme

    } else {
        LightColorScheme
    }
    val colors = if (isDarkTheme) {
        DarkColors
    } else {
        LightColors
    }

    CompositionLocalProvider(HemiusColors provides colors) {
        MaterialTheme(
            colorScheme = colorScheme ,
            typography = Typography ,
            content = content
        )
    }
}
