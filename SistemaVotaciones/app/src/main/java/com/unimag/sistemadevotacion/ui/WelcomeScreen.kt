package com.unimag.sistemadevotacion.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.unimag.sistemadevotacion.R

/**
 * WelcomeScreen es la pantalla de bienvenida de la aplicación.
 *
 * @param navController El NavController para gestionar la navegación.
 */
@Composable
fun WelcomeScreen(navController: NavController) {

    // Box se usa como contenedor raíz para poder posicionar elementos con align.
    // safeDrawingPadding() añade un relleno automático para evitar que el contenido
    // se solape con las barras del sistema (barra de estado, barra de navegación, etc.).
    Box(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
    ) {

        // Botón para ir a la pantalla de PIN de administrador, alineado arriba a la derecha.
        IconButton(
            onClick = { navController.navigate("pin") },
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Admin"
            )
        }

        // Columna para centrar el contenido principal vertical y horizontalmente.
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Muestra el logo del colegio.
            Image(
                painter = painterResource(id = R.drawable.ic_school_logo),
                contentDescription = "Logo del Colegio",
                modifier = Modifier.size(150.dp)
            )

            // Espaciador vertical.
            Spacer(modifier = Modifier.height(20.dp))

            // Título de la aplicación, usando el estilo definido en el tema.
            Text(
                text = "Sistema de Votaciones",
               style = MaterialTheme.typography.titleLarge
            )

            // Espaciador vertical.
            Spacer(modifier = Modifier.height(30.dp))

            // Botón para iniciar el proceso de votación.
            // Navega a la pantalla de "contralor".
            Button(
                onClick = { navController.navigate("contralor") },
                modifier = Modifier.width(200.dp)
            ) {
                Text("Comencemos")
            }
        }
    }
}