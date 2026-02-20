package com.unimag.sistemadevotacion.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

/**
 * ConfirmationScreen es la pantalla que se muestra después de que un usuario ha completado su votación.
 *
 * @param navController El NavHostController para gestionar la navegación.
 */
@Composable
fun ConfirmationScreen(navController: NavHostController) {
    // Columna que centra todo el contenido en la pantalla.
    // safeDrawingPadding() añade un relleno automático para respetar las barras del sistema.
    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Mensaje de confirmación principal.
        Text(text = "Tu voto ha sido registrado", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(18.dp))

        // Mensaje de agradecimiento.
        Text(text = "Gracias por participar", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(30.dp))

        // Botón para finalizar y volver a la pantalla de bienvenida.
        Button(onClick = { navController.navigate("welcome") }) {
            Text("FINALIZAR")
        }
    }
}
