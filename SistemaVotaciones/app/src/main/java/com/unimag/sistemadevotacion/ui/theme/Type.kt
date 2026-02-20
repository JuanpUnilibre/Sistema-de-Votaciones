package com.unimag.sistemadevotacion.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.unimag.sistemadevotacion.R

// 1. Se define la nueva familia de fuentes "Raleway".
// Al ser una fuente variable, podemos apuntar todos los grosores al mismo fichero.
val Raleway = FontFamily(
    Font(R.font.raleway_variablefont_wght, FontWeight.Normal),
    Font(R.font.raleway_variablefont_wght, FontWeight.Bold)
)

// 2. Se actualiza la tipografía de la app para usar la nueva fuente.
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Raleway,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Raleway,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 34.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = Raleway,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    )
)
