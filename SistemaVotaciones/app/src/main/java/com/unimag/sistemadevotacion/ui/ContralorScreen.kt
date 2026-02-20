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
import com.unimag.sistemadevotacion.data.VoteState

/**
 * ContralorScreen es la pantalla donde el usuario selecciona un candidato para el rol de Contralor.
 *
 * @param navController El NavHostController para gestionar la navegación.
 * @param candidates La lista completa de todos los candidatos.
 */
@Composable
fun ContralorScreen(navController: NavHostController, candidates: List<Candidate>) {
    // `selectedId` mantiene el ID del candidato seleccionado.
    // `remember` se usa para que el estado persista a través de las recomposiciones.
    // Se inicializa con el valor guardado en VoteState para mantener la selección si el usuario vuelve atrás.
    var selectedId by remember { mutableStateOf(VoteState.selectedContralorId) }

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
        Text(text = "Seleccione al CONTRALOR", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        // Columna para la lista de candidatos.
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            // Filtra la lista de candidatos para mostrar solo los que tienen el rol de CONTRALOR.
            candidates.filter { it.role == Role.CONTRALOR }.forEach { candidate ->
                // Determina si el candidato actual es el que está seleccionado.
                val isSelected = selectedId == candidate.id
                // Fila que representa a un candidato.
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        // El borde cambia de grosor y color si el candidato está seleccionado.
                        .border(
                            width = if (isSelected) 3.dp else 1.dp,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray,
                            shape = RoundedCornerShape(8.dp)
                        )
                        // Hace que la fila sea clickeable para seleccionar al candidato.
                        .clickable {
                            // Actualiza el ID del candidato seleccionado en el estado local y en el estado global (VoteState).
                            selectedId = candidate.id
                            VoteState.selectedContralorId = candidate.id
                        }
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Muestra la imagen del candidato.
                    Image(
                        painter = painterResource(id = candidate.imageRes),
                        contentDescription = candidate.name,
                        modifier = Modifier.size(72.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    // Columna para el nombre y el rol del candidato.
                    Column {
                        Text(text = candidate.name, style = MaterialTheme.typography.titleMedium)
                        Text(text = "Candidato", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Opción para Voto en Blanco
        val isVotoEnBlancoSelected = selectedId == VoteState.CONTRALOR_BLANCO_ID
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = if (isVotoEnBlancoSelected) 3.dp else 1.dp,
                    color = if (isVotoEnBlancoSelected) MaterialTheme.colorScheme.primary else Color.LightGray,
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable {
                    selectedId = VoteState.CONTRALOR_BLANCO_ID
                    VoteState.selectedContralorId = VoteState.CONTRALOR_BLANCO_ID
                }
                .padding(vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "VOTO EN BLANCO", style = MaterialTheme.typography.titleMedium)
        }

        // Un Spacer con `weight(1f)` empuja el botón "SIGUIENTE" hacia la parte inferior de la pantalla.
        Spacer(modifier = Modifier.weight(1f))

        // Botón para navegar a la siguiente pantalla (personero).
        Button(
            onClick = {
                // Navega a la pantalla "personero". El voto ya se ha guardado en VoteState al hacer click.
                navController.navigate("personero")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            // El botón solo se activa si se ha seleccionado un candidato o el voto en blanco.
            enabled = selectedId != null
        ) {
            Text("SIGUIENTE")
        }
    }
}
