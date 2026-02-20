package com.unimag.sistemadevotacion.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.unimag.sistemadevotacion.data.Candidate
import com.unimag.sistemadevotacion.data.Role
import com.unimag.sistemadevotacion.data.VoteManager
import com.unimag.sistemadevotacion.data.VoteState

/**
 * PersoneroScreen es la pantalla donde el usuario selecciona un candidato para el rol de Personero.
 *
 * @param navController El NavHostController para gestionar la navegación.
 * @param candidates La lista completa de todos los candidatos.
 * @param voteManager La instancia de VoteManager para registrar los votos.
 */
@Composable
fun PersoneroScreen(navController: NavHostController, candidates: List<Candidate>, voteManager: VoteManager) {
    // `selectedId` mantiene el ID del candidato a personero seleccionado.
    // Se inicializa con el valor guardado en VoteState para mantener la selección si el usuario vuelve atrás.
    var selectedId by remember { mutableStateOf(VoteState.selectedPersoneroId) }

    // Columna principal que organiza los elementos verticalmente.
    // safeDrawingPadding() añade un relleno automático para respetar las barras del sistema.
    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título de la pantalla.
        Text(text = "Seleccione al PERSONERO", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        // Columna para la lista de candidatos.
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            // Filtra la lista de candidatos para mostrar solo los que tienen el rol de PERSONERO.
            candidates.filter { it.role == Role.PERSONERO }.forEach { candidate ->
                val isSelected = selectedId == candidate.id
                // Fila que representa a un candidato.
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        // El borde cambia si el candidato está seleccionado.
                        .border(
                            width = if (isSelected) 3.dp else 1.dp,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray,
                            shape = RoundedCornerShape(8.dp)
                        )
                        // Permite seleccionar al candidato al hacer clic.
                        .clickable {
                            selectedId = candidate.id
                            VoteState.selectedPersoneroId = candidate.id
                        }
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Imagen del candidato.
                    Image(
                        painter = painterResource(id = candidate.imageRes),
                        contentDescription = candidate.name,
                        modifier = Modifier.size(72.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    // Nombre y rol del candidato.
                    Column {
                        Text(text = candidate.name, style = MaterialTheme.typography.titleMedium)
                        Text(text = "Candidato", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Opción para Voto en Blanco
        val isVotoEnBlancoSelected = selectedId == VoteState.PERSONERO_BLANCO_ID
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = if (isVotoEnBlancoSelected) 3.dp else 1.dp,
                    color = if (isVotoEnBlancoSelected) MaterialTheme.colorScheme.primary else Color.LightGray,
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable {
                    selectedId = VoteState.PERSONERO_BLANCO_ID
                    VoteState.selectedPersoneroId = VoteState.PERSONERO_BLANCO_ID
                }
                .padding(vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "VOTO EN BLANCO", style = MaterialTheme.typography.titleMedium)
        }

        // Spacer que empuja el botón de confirmación hacia abajo.
        Spacer(modifier = Modifier.weight(1f))

        // Botón para confirmar y registrar el voto.
        Button(
            onClick = {
                // Se guardan los votos para el contralor y el personero seleccionados.
                VoteState.selectedContralorId?.let { voteManager.addVote(it) }
                VoteState.selectedPersoneroId?.let { voteManager.addVote(it) }
                // Se registra que un votante ha completado el proceso.
                voteManager.incrementTotalVotantes()
                // Se marca que este usuario ya ha votado.
                voteManager.setHasVoted(true)
                // Se resetea el estado de la votación actual para el siguiente votante.
                VoteState.reset()
                // Se navega a la pantalla de confirmación.
                navController.navigate("confirmation") {
                    // Limpia la pila de navegación hasta la pantalla de bienvenida
                    // para que el usuario no pueda volver atrás a las pantallas de votación.
                    popUpTo("welcome")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            // El botón solo se activa si se ha seleccionado un personero o el voto en blanco.
            enabled = selectedId != null
        ) {
            Text("CONFIRMAR VOTO")
        }
    }
}
