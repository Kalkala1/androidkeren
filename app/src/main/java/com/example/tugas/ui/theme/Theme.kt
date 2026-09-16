package com.example.tugas.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = OceanMist,
    secondary = OceanWave,
    tertiary = HappySun,
    background = Color(0xFF023E8A),
    surface = Color(0xFF0077B6),
    onPrimary = Color(0xFF03045E),
    onSecondary = SeaShell,
    onTertiary = Color(0xFF03045E),
    onBackground = SeaShell,
    onSurface = SeaShell
)

private val LightColorScheme = lightColorScheme(
    primary = OceanDeep,
    secondary = OceanWave,
    tertiary = HappySun,
    background = SeaShell,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color(0xFF03045E),
    onTertiary = Color(0xFF03045E),
    onBackground = Color(0xFF03045E),
    onSurface = Color(0xFF03045E)
)

@Composable
fun TugasTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // We want the Happy Style to shine, dynamic color can be optional
    dynamicColor: Boolean = true,
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

    MaterialTheme(
      colorScheme = colorScheme,
      typography = Typography,
      content = content
    )
}