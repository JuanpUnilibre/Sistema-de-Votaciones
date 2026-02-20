package com.unimag.sistemadevotacion.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.unimag.sistemadevotacion.data.Candidate
import com.unimag.sistemadevotacion.data.Role
import com.unimag.sistemadevotacion.data.VoteManager
import com.unimag.sistemadevotacion.data.VoteState

/**
 * AdminScreen es la pantalla que muestra los resultados de la votación.
 *
 * @param candidates La lista completa de todos los candidatos.
 * @param voteManager La instancia de VoteManager para obtener los resultados.
 */
@Composable
fun AdminScreen(candidates: List<Candidate>, voteManager: VoteManager) {
    // Columna principal que organiza la pantalla.
    // safeDrawingPadding() añade un relleno automático para respetar las barras del sistema.
    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(16.dp)
    ) {
        // Título de la pantalla de resultados.
        Text("RESULTADOS", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(12.dp))

        // Sección para los resultados de Contralor.
        Text("CONTRALOR", style = MaterialTheme.typography.titleMedium)
        val contralores = candidates.filter { it.role == Role.CONTRALOR }
        // Columna para mostrar los resultados de contralor.
        Column {
            contralores.forEach { candidate ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(candidate.name)
                    Text("${voteManager.getVotes(candidate.id)}")
                }
            }
            // Fila para el voto en blanco de contralor.
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("VOTO EN BLANCO")
                Text("${voteManager.getVotes(VoteState.CONTRALOR_BLANCO_ID)}")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Sección para los resultados de Personero.
        Text("PERSONERO", style = MaterialTheme.typography.titleMedium)
        val personeros = candidates.filter { it.role == Role.PERSONERO }
        // Columna para mostrar los resultados de personero.
        Column {
            personeros.forEach { candidate ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(candidate.name)
                    Text("${voteManager.getVotes(candidate.id)}")
                }
            }
            // Fila para el voto en blanco de personero.
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("VOTO EN BLANCO")
                Text("${voteManager.getVotes(VoteState.PERSONERO_BLANCO_ID)}")
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Muestra el número total de votantes que han participado.
        Text("TOTAL VOTANTES: ${voteManager.getTotalVotantes()}", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(12.dp))

        // Botón para reiniciar todas las votaciones.
        Button(onClick = {
            // Obtiene los IDs de todos los candidatos y los votos en blanco.
            val ids = candidates.map { it.id } + listOf(VoteState.CONTRALOR_BLANCO_ID, VoteState.PERSONERO_BLANCO_ID)
            // Llama a la función para reiniciar todos los contadores de votos.
            voteManager.resetAll(ids)
        }) {
            Text("RESET VOTACIONES")
        }
    }
}
