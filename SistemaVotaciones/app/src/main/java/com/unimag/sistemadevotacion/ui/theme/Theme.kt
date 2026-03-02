package com.unimag.sistemadevotacion.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

// Paleta de colores para el tema claro, usando los nuevos colores vibrantes.
private val LightColorScheme = lightColorScheme(
    primary = PrimaryRed,
    secondary = SecondaryYellow,
    tertiary = TertiaryBlue,
    background = LightBackground,
    surface = LightSurface,
    onPrimary = LightOnPrimary,
    onSecondary = LightOnSecondary,
    onTertiary = LightOnTertiary,
    onBackground = LightOnBackground,
    onSurface = LightOnSurface
)

// Paleta de colores para el tema oscuro.
private val DarkColorScheme = darkColorScheme(
    primary = PrimaryRed,       // Mantenemos los colores vibrantes
    secondary = SecondaryYellow,
    tertiary = TertiaryBlue,
    background = DarkBackground,
    surface = DarkSurface,
    onPrimary = DarkOnPrimary,
    onSecondary = DarkOnSecondary,
    onTertiary = DarkOnTertiary,
    onBackground = DarkOnBackground,
    onSurface = DarkOnSurface
)

@Composable
fun SistemaDeVotacionTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Se desactiva el color dinámico para usar siempre nuestra paleta personalizada.
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
