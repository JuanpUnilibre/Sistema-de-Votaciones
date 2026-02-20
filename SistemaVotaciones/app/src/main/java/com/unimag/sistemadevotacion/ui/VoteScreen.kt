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
import com.unimag.sistemadevotacion.R

/**
 * VoteScreen es un composable reutilizable que muestra una pantalla de votación genérica.
 *
 * @param titulo El título que se mostrará en la parte superior de la pantalla.
 * @param onNext La acción a ejecutar cuando el usuario pulsa el botón "Siguiente".
 */
@Composable
fun VoteScreen(
    titulo: String,
    onNext: () -> Unit
) {

    // `selectedCandidate` mantiene el ID del candidato seleccionado.
    // `remember` se usa para que el estado persista a través de las recomposiciones.
    var selectedCandidate by remember { mutableStateOf<Int?>(null) }

    // Columna principal que organiza los elementos verticalmente.
    // safeDrawingPadding() añade un relleno automático para respetar las barras del sistema.
    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Muestra el título de la pantalla de votación.
        Text(
            text = titulo,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Fila que contiene los elementos de los candidatos, distribuidos uniformemente.
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {

            // Muestra un candidato. En una implementación real, esto debería ser dinámico
            // en lugar de estar codificado con valores fijos.
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

        // Botón para ir a la siguiente pantalla.
        // Solo se habilita si se ha seleccionado un candidato.
        Button(
            onClick = onNext,
            enabled = selectedCandidate != null
        ) {
            Text("Siguiente")
        }
    }
}

/**
 * CandidateItem es un composable que muestra un único candidato en la lista.
 *
 * @param name El nombre del candidato.
 * @param image El recurso de imagen del candidato.
 * @param selected `true` si el candidato está seleccionado, `false` en caso contrario.
 * @param onClick La acción a ejecutar cuando se hace clic en el candidato.
 */
@Composable
fun CandidateItem(
    name: String,
    image: Int,
    selected: Boolean,
    onClick: () -> Unit
) {

    // Columna que contiene la imagen y el nombre del candidato.
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            // El borde cambia de color si el candidato está seleccionado.
            .border(
                width = if (selected) 3.dp else 1.dp,
                color = if (selected) Color.Green else Color.Gray,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(10.dp)
            // Hace que el item sea clickeable.
            .clickable { onClick() }
    ) {

        // Muestra la imagen del candidato.
        Image(
            painter = painterResource(image),
            contentDescription = name,
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Muestra el nombre del candidato.
        Text(name)
    }
}
