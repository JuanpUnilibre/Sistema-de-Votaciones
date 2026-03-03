package com.unimag.sistemadevotacion.ui.theme

import androidx.compose.ui.graphics.Color

// --- Paleta "Verde Tóxico" ---
val ToxicGreen = Color(0xFF39FF14)      // Verde neón principal
val ToxicGreenDark = Color(0xFF2ECC71)  // Un verde más profundo para contrastes
val ToxicGreenLight = Color(0xFFB3FFB3) // Un verde muy suave para fondos o detalles

// Colores de soporte
val PureWhite = Color(0xFFFFFFFF)
val ErrorRed = Color(0xFFFF3131)        // Rojo vibrante para errores
val DarkGrey = Color(0xFF121212)

// Mapeo para el sistema de Material3
val LightPrimary = ToxicGreen
val LightOnPrimary = Color.Black        // El verde tóxico brilla más con texto negro
val LightSecondary = ToxicGreenDark
val LightBackground = PureWhite
val LightSurface = PureWhite
val LightError = ErrorRed

val DarkPrimary = ToxicGreen
val DarkOnPrimary = Color.Black
val DarkBackground = DarkGrey
val DarkSurface = Color(0xFF1E1E1E)
val DarkError = ErrorRed
