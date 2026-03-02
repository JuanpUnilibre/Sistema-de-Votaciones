package com.unimag.sistemadevotacion.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.unimag.sistemadevotacion.data.Candidate
import com.unimag.sistemadevotacion.data.Role
import com.unimag.sistemadevotacion.data.VoteManager
import com.unimag.sistemadevotacion.data.VoteState

// El PIN debe ser el mismo que el definido en PinScreen.kt
private const val ADMIN_PIN = "2003"

/**
 * AdminScreen es la pantalla que muestra los resultados de la votación.
 *
 * @param candidates La lista completa de todos los candidatos.
 * @param voteManager La instancia de VoteManager para obtener los resultados.
 */
@Composable
fun AdminScreen(candidates: List<Candidate>, voteManager: VoteManager) {
    var showResetDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(16.dp)
    ) {
        Text("RESULTADOS", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(12.dp))

        // (El resto del código que muestra los resultados se mantiene igual)
        // ...
        Spacer(modifier = Modifier.weight(1f))

        Text("TOTAL VOTANTES: ${voteManager.getTotalVotantes()}", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(12.dp))

        // Botón que ahora abre el diálogo de confirmación.
        Button(onClick = { showResetDialog = true }) {
            Text("RESET VOTACIONES")
        }
    }

    // Diálogo de confirmación para el reseteo.
    if (showResetDialog) {
        var pinInput by remember { mutableStateOf("") }
        var pinError by remember { mutableStateOf(false) }

        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = { Text("Confirmar Reseteo") },
            text = {
                Column {
                    Text("Esta acción es irreversible y borrará todos los votos. Ingrese el PIN de administrador para continuar.")
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = pinInput,
                        onValueChange = { newValue ->
                            if (newValue.length <= 4 && newValue.all { it.isDigit() }) {
                                pinInput = newValue
                                pinError = false
                            }
                        },
                        label = { Text("PIN") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                        visualTransformation = PasswordVisualTransformation(),
                        isError = pinError,
                        supportingText = { if (pinError) Text("PIN incorrecto") }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (pinInput == ADMIN_PIN) {
                            val ids = candidates.map { it.id } + listOf(VoteState.CONTRALOR_BLANCO_ID, VoteState.PERSONERO_BLANCO_ID)
                            voteManager.resetAll(ids)
                            showResetDialog = false
                        } else {
                            pinError = true
                        }
                    },
                    enabled = pinInput.length == 4
                ) {
                    Text("CONFIRMAR")
                }
            },
            dismissButton = {
                Button(onClick = { showResetDialog = false }) {
                    Text("CANCELAR")
                }
            }
        )
    }
}