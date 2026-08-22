package com.example.ui.theme

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

// Sophisticated Dark Color Scheme
private val SophisticatedDarkColorScheme = darkColorScheme(
    primary = GoldPrimary,
    onPrimary = Color(0xFF0A0A0A),
    primaryContainer = GoldContainer,
    onPrimaryContainer = GoldSecondary,
    secondary = CyanAccent,
    onSecondary = Color(0xFF0A0A0A),
    secondaryContainer = Color(0xFF132F2B),
    onSecondaryContainer = Color(0xFF99F6E4),
    tertiary = GoldSecondary,
    onTertiary = Color(0xFF0A0A0A),
    tertiaryContainer = Color(0xFF26200A),
    onTertiaryContainer = GoldSecondary,
    background = SophisticatedDarkBg,
    onBackground = TextSlatePrimary,
    surface = SophisticatedDarkSurface,
    onSurface = TextSlatePrimary,
    surfaceVariant = SophisticatedDarkSurfaceVariant,
    onSurfaceVariant = TextSlateSecondary,
    outline = SophisticatedDarkBorder,
    outlineVariant = SophisticatedDarkBorderSubtle,
    error = Color(0xFFF87171),
    onError = Color(0xFF450A0A),
    errorContainer = Color(0xFF7F1D1D),
    onErrorContainer = Color(0xFFFECACA)
)

private val SophisticatedLightColorScheme = lightColorScheme(
    primary = GoldPrimary,
    onPrimary = Color(0xFF0A0A0A),
    primaryContainer = Color(0xFFFEF3C7),
    onPrimaryContainer = Color(0xFF78350F),
    secondary = CyanDark,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFCCFBF1),
    onSecondaryContainer = Color(0xFF115E59),
    tertiary = GoldDark,
    background = SophisticatedLightBg,
    onBackground = TextDarkPrimary,
    surface = SophisticatedLightSurface,
    onSurface = TextDarkPrimary,
    surfaceVariant = SophisticatedLightSurfaceVariant,
    onSurfaceVariant = TextDarkSecondary,
    outline = SophisticatedLightBorder,
    outlineVariant = Color(0x0F000000)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true, // Default to Sophisticated Dark mode
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> SophisticatedDarkColorScheme
        else -> SophisticatedDarkColorScheme // Ensure Sophisticated Dark theme is preserved
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
