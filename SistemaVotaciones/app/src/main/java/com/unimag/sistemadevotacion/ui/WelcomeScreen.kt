package com.unimag.sistemadevotacion.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.unimag.sistemadevotacion.R


@Composable
fun WelcomeScreen(navController: NavController ) {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        // Boton admin arriba derecha
        IconButton(
            onClick = { navController.navigate("admin") },
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Admin"
            )
        }

        // Contenido centrado
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Logo colegio
            Image(
                painter = painterResource(id = R.drawable.ic_school_logo),
                contentDescription = "Logo",
                modifier = Modifier.size(150.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Titulo
            Text(
                text = "Sistema de Votaciones",
                fontSize = 26.sp
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Boton votar
            Button(
                onClick = { navController.navigate("contralor") },
                modifier = Modifier.width(200.dp)
            ) {
                Text("Comencemos")
            }
        }
    }
}