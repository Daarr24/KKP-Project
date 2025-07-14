package com.example.kkp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.compose.ui.text.font.FontFamily

// Neon & glassmorphism inspired colors
val NeonBlue = Color(0xFF00CFFF)
val NeonPurple = Color(0xFF7F00FF)
val NeonGreen = Color(0xFF00FFB0)
val NeonPink = Color(0xFFFF00C8)
val GlassBg = Color(0xCC1A1A2E)
val CardBg = Color(0xCC23233A)
val SurfaceGlass = Color(0xB31A1A2E)

private val DarkColorScheme = darkColorScheme(
    primary = RedPrimary,
    secondary = RedAccent,
    tertiary = BlueInfo,
    background = Black,
    surface = GrayDark,
    onPrimary = White,
    onSecondary = White,
    onTertiary = White,
    onBackground = White,
    onSurface = White
)

private val LightColorScheme = lightColorScheme(
    primary = RedPrimary,
    secondary = RedAccent,
    tertiary = BlueInfo,
    background = WhiteSoft,
    surface = White,
    onPrimary = White,
    onSecondary = White,
    onTertiary = White,
    onBackground = Black,
    onSurface = Black
)

@Composable
fun KKPTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}