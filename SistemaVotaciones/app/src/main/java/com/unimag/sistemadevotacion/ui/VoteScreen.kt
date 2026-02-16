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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import com.unimag.sistemadevotacion.R

@Composable
fun VoteScreen(
    titulo: String,
    onNext: () -> Unit
) {

    // guarda candidato seleccionado
    var selectedCandidate by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = titulo,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {

            CandidateItem(
                name = "Candidato 1",
                image = R.drawable.contralor_1,
                selected = selectedCandidate == 1,
                onClick = { selectedCandidate = 1 }
            )

            CandidateItem(
                name = "Candidato 2",
                image = R.drawable.contralor_2,
                selected = selectedCandidate == 2,
                onClick = { selectedCandidate = 2 }
            )

            CandidateItem(
                name = "Candidato 3",
                image = R.drawable.contralor_3,
                selected = selectedCandidate == 3,
                onClick = { selectedCandidate = 3 }
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = onNext,
            enabled = selectedCandidate != null
        ) {
            Text("Siguiente")
        }
    }
}

@Composable
fun CandidateItem(
    name: String,
    image: Int,
    selected: Boolean,
    onClick: () -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .border(
                width = if (selected) 3.dp else 1.dp,
                color = if (selected) Color.Green else Color.Gray,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(10.dp)
            .clickable { onClick() }
    ) {

        Image(
            painter = painterResource(image),
            contentDescription = name,
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(name)
    }
}



