package com.example.show.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightThemeColors = lightColors(
    primary = p700,
    primaryVariant = p900,
    onPrimary = Color.White,
    secondary = p700,
    secondaryVariant = p900,
    onSecondary = Color.White,
    error = p800
)

private val DarkThemeColors = darkColors(
    primary = p300,
    primaryVariant = p700,
    onPrimary = Color.Black,
    secondary = p300,
    onSecondary = Color.White,
    error = p200
)


@Composable
fun GoodsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content : @Composable ()->Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkThemeColors else LightThemeColors,
        content = content
    )
}