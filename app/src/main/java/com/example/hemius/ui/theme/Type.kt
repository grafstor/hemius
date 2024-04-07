package com.example.hemius.ui.theme


import androidx.compose.material3.Typography
import androidx.compose.ui.text.PlatformParagraphStyle
import androidx.compose.ui.text.PlatformSpanStyle
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.hemius.R

val GeologicaFont = FontFamily(Font(R.font.geologicares))


val Typography = Typography(
    bodyMedium = TextStyle(
        fontFamily = GeologicaFont,
        fontWeight = FontWeight.Normal,
        lineHeight = 28.sp,
        platformStyle = PlatformTextStyle(spanStyle = PlatformSpanStyle() , paragraphStyle = PlatformParagraphStyle(includeFontPadding = false)),
        lineHeightStyle = LineHeightStyle(alignment = LineHeightStyle.Alignment(topRatio = 1f), trim = LineHeightStyle.Trim.Both),
        fontSize = 22.sp,
        letterSpacing = 0.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)