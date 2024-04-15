package com.example.hemius.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    background = BlackBackground,
    surface = BlackBackground
)

private val LightColorScheme = lightColorScheme(
    background = WhiteBackground,
    surface = WhiteBackground
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

val HemiusColors = compositionLocalOf<Colors> {
    error("No Colors provided")
}

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
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}
