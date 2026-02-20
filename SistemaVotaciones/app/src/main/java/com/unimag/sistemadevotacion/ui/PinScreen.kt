package com.unimag.sistemadevotacion.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

// PIN correcto (hardcodeado).
private const val CORRECT_PIN = "2003"

@Composable
fun PinScreen(navController: NavController) {
    var pin by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Ingrese el PIN de administrador",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = pin,
            onValueChange = { newValue ->
                // Solo permite 4 dígitos numéricos.
                if (newValue.length <= 4 && newValue.all { it.isDigit() }) {
                    pin = newValue
                }
            },
            label = { Text("PIN") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            visualTransformation = PasswordVisualTransformation(),
            isError = error
        )

        if (error) {
            Text("PIN incorrecto", color = androidx.compose.ui.graphics.Color.Red)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (pin == CORRECT_PIN) {
                    // Navega a la pantalla de admin y limpia la pantalla de PIN del backstack.
                    navController.navigate("admin") {
                        popUpTo("pin") { inclusive = true }
                    }
                } else {
                    error = true
                }
            },
            enabled = pin.length == 4
        ) {
            Text("INGRESAR")
        }
    }
}