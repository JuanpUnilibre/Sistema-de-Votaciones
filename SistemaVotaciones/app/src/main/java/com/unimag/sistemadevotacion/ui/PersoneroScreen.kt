package com.unimag.sistemadevotacion.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.unimag.sistemadevotacion.R
import com.unimag.sistemadevotacion.data.Candidate
import com.unimag.sistemadevotacion.data.Role
import com.unimag.sistemadevotacion.data.VoteManager
import com.unimag.sistemadevotacion.data.VoteState

@Composable
fun PersoneroScreen(navController: NavHostController, candidates: List<Candidate>, voteManager: VoteManager) {
    var selectedId by remember { mutableStateOf(VoteState.selectedPersoneroId) }
    var showDialog by remember { mutableStateOf(false) }
    val selectedCandidate = candidates.find { it.id == selectedId }
    val successColor = Color(0xFF00C389)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Seleccione al PERSONERO", 
            style = MaterialTheme.typography.titleLarge,
            fontSize = 30.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(20.dp)) {
            candidates.filter { it.role == Role.PERSONERO }.forEach { candidate ->
                val isSelected = selectedId == candidate.id
                
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = if (isSelected) 5.dp else 1.dp,
                            color = if (isSelected) successColor else Color.LightGray,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clickable {
                            if (isSelected) {
                                showDialog = true
                            } else {
                                selectedId = candidate.id
                                VoteState.selectedPersoneroId = candidate.id
                            }
                        }
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = candidate.imageRes),
                        contentDescription = candidate.name,
                        modifier = Modifier.size(90.dp).clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = candidate.name, 
                            style = MaterialTheme.typography.titleMedium,
                            fontSize = 24.sp
                        )
                        Text(text = "Candidato", style = MaterialTheme.typography.bodySmall, fontSize = 14.sp)
                    }

                    if (isSelected) {
                        Surface(
                            color = successColor,
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Text(
                                text = "VOTAR",
                                color = Color.White,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Opción Voto en Blanco
        val isBlancoSelected = selectedId == VoteState.PERSONERO_BLANCO_ID
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = if (isBlancoSelected) 5.dp else 1.dp,
                    color = if (isBlancoSelected) successColor else Color.LightGray,
                    shape = RoundedCornerShape(16.dp)
                )
                .clickable {
                    if (isBlancoSelected) {
                        showDialog = true
                    } else {
                        selectedId = VoteState.PERSONERO_BLANCO_ID
                        VoteState.selectedPersoneroId = VoteState.PERSONERO_BLANCO_ID
                    }
                }
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "VOTO EN BLANCO", style = MaterialTheme.typography.titleMedium, fontSize = 22.sp)
            if (isBlancoSelected) {
                Spacer(modifier = Modifier.width(20.dp))
                Surface(
                    color = successColor,
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = "VOTAR",
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        TextButton(onClick = { navController.popBackStack() }) {
            Text("VOLVER AL ANTERIOR", color = Color.Gray, fontSize = 16.sp)
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(
                    text = "Confirmar Voto",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Image(
                        painter = painterResource(id = selectedCandidate?.imageRes ?: R.drawable.ic_school_logo),
                        contentDescription = null,
                        modifier = Modifier.size(140.dp).clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "¿Este es el personero por el que quieres votar, cierto?",
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 18.sp,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Text(
                        text = if (selectedId != VoteState.PERSONERO_BLANCO_ID) (selectedCandidate?.name ?: "") else "VOTO EN BLANCO",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }
            },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    OutlinedButton(
                        onClick = { showDialog = false },
                        modifier = Modifier.height(60.dp).weight(1f).padding(horizontal = 8.dp)
                    ) {
                        Text("NO", color = Color.Gray, fontSize = 20.sp)
                    }
                    Button(
                        onClick = {
                            showDialog = false
                            // REGISTRO DE VOTOS
                            VoteState.selectedContralorId?.let { voteManager.addVote(it) }
                            VoteState.selectedPersoneroId?.let { voteManager.addVote(it) }
                            voteManager.incrementTotalVotantes()
                            VoteState.reset()
                            
                            navController.navigate("confirmation") {
                                popUpTo("welcome")
                            }
                        },
                        modifier = Modifier.height(60.dp).weight(1f).padding(horizontal = 8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = successColor)
                    ) {
                        Text("¡SÍ!", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
                    }
                }
            }
        )
    }
}
