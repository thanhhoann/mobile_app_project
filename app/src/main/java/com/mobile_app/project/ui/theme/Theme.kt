package com.mobile_app.project.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme =
    darkColorScheme(
        // Primary colors: Main theme color and associated variations.
        primary = primary,
        onPrimary = on_primary,
        primaryContainer = primary_variant,
        onPrimaryContainer = on_primary,

        // Secondary colors: Supporting color and its variations.
        secondary = secondary,
        onSecondary = on_secondary,
        secondaryContainer = secondary_variant,
        onSecondaryContainer = on_secondary,

        // Tertiary colors: Additional accent color and its variations.
        tertiary = tertiary,
        onTertiary = on_tertiary,
        tertiaryContainer = tertiary_container,
        onTertiaryContainer = on_tertiary_container,

        // Background and surface: Define the app background and other surfaces.
        background = primary_background,
        onBackground = on_background,
        surface = surface,
        onSurface = on_background,
        surfaceVariant = elevated_surface,
        onSurfaceVariant = on_background,

        // Error colors: Indicate errors and their related states.
        error = error,
        onError = on_error,
        errorContainer = warning,
        onErrorContainer = on_error,

        // Outlines and borders: Used for UI elements like input borders.
        outline = borders,
        outlineVariant = divider,

        // Scrim: Used for modal overlays.
        scrim = scrim
    )


@Composable
fun MobileAppProjectTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = DarkColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}
