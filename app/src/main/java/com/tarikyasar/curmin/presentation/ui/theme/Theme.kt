package com.tarikyasar.curmin.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = PrimaryColor,
    primaryVariant = PrimaryColorVariant,
    secondary = SecondaryColorDark,
    secondaryVariant = SecondaryColorVariantDark,
    background = BackgroundColorDark,
    surface = SurfaceColorDark,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = BackgroundColorLight,
    onSurface = OnSurfaceColorDark
)

private val LightColorPalette = lightColors(
    primary = PrimaryColor,
    primaryVariant = PrimaryColorVariant,
    secondary = SecondaryColorLight,
    secondaryVariant = SecondaryColorVariantLight,
    background = BackgroundColorLight,
    surface = SurfaceColorLight,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = BackgroundColorDark,
    onSurface = OnSurfaceColorLight
)

@Composable
fun CurminTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}