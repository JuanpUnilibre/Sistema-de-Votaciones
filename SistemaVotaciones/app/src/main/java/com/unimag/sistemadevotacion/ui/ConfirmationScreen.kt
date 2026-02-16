package com.unimag.sistemadevotacion.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun ConfirmationScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Tu voto ha sido registrado", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(18.dp))
        Text(text = "Gracias por participar", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(30.dp))

        Button(onClick = { navController.navigate("welcome") }) {
            Text("FINALIZAR")
        }
    }
}
