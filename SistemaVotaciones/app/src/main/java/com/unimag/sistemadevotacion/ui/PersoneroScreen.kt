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
import com.unimag.sistemadevotacion.data.VoteManager
import com.unimag.sistemadevotacion.data.VoteState

@Composable
fun PersoneroScreen(navController: NavHostController, candidates: List<Candidate>, voteManager: VoteManager) {
    var selectedId by remember { mutableStateOf(VoteState.selectedPersoneroId) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Seleccione al PERSONERO", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            candidates.filter { it.role == "personero" }.forEach { c ->
                val isSelected = selectedId == c.id
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = if (isSelected) 3.dp else 1.dp,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable {
                            selectedId = c.id
                            VoteState.selectedPersoneroId = c.id
                        }
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = c.imageRes),
                        contentDescription = c.name,
                        modifier = Modifier.size(72.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(text = c.name, style = MaterialTheme.typography.titleMedium)
                        Text(text = "Candidato", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                // Guardar ambos votos: contralor y personero
                VoteState.selectedContralorId?.let { voteManager.addVote(it) }
                VoteState.selectedPersoneroId?.let { voteManager.addVote(it) }
                voteManager.incrementTotalVotantes()
                voteManager.setHasVoted(true)
                VoteState.reset()
                // navegar a confirmacion
                navController.navigate("confirmation") {
                    popUpTo("welcome") // limpia stack si quieres
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = selectedId != null
        ) {
            Text("CONFIRMAR VOTO")
        }
    }
}
