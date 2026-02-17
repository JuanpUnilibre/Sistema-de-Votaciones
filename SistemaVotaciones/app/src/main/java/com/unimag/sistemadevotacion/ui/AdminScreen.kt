package com.unimag.sistemadevotacion.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.unimag.sistemadevotacion.data.Candidate
import com.unimag.sistemadevotacion.data.VoteManager
import com.unimag.sistemadevotacion.data.Role


@Composable
fun AdminScreen(candidates: List<Candidate>, voteManager: VoteManager) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("RESULTADOS", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(12.dp))

        Text("CONTRALOR", style = MaterialTheme.typography.titleMedium)
        val contralores = candidates.filter { it.role == Role.CONTRALOR }
        LazyColumn {
            items(contralores) { c ->
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(c.name)
                    Text("${voteManager.getVotes(c.id)}")
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        Text("PERSONERO", style = MaterialTheme.typography.titleMedium)
        val personeros = candidates.filter { it.role == Role.PERSONERO }
        LazyColumn {
            items(personeros) { c ->
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(c.name)
                    Text("${voteManager.getVotes(c.id)}")
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))
        Text("TOTAL VOTANTES: ${voteManager.getTotalVotantes()}", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(12.dp))
        // Reset button (implementar con dialog en produccion)
        Button(onClick = {
            val ids = candidates.map { it.id }
            voteManager.resetAll(ids)
        }) {
            Text("RESET VOTACIONES")
        }
    }
}