package com.unimag.sistemadevotacion.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.unimag.sistemadevotacion.R
import com.unimag.sistemadevotacion.data.Candidate
import com.unimag.sistemadevotacion.data.VoteManager

@Composable
fun AppNavigation(appContext: Context) {
    val navController = rememberNavController()

    // lista de candidatos prepopulada (usa drawables que agregues)
    val candidates = listOf(
        Candidate(1, "Juan Perez", "contralor", imageRes = R.drawable.contralor_1),
        Candidate(2, "Maria Gomez", "contralor", imageRes = R.drawable.contralor_2),
        Candidate(3, "Pedro Ruiz", "contralor", imageRes = R.drawable.contralor_3),
        Candidate(4, "Ana Lopez", "personero", imageRes = R.drawable.personero_1),
        Candidate(5, "Luis Diaz", "personero", imageRes = R.drawable.personero_2),
        Candidate(6, "Camila Rios", "personero", imageRes = R.drawable.personero_3)
    )

    val voteManager = VoteManager(appContext)

    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") { WelcomeScreen(navController) }
        composable("contralor") { ContralorScreen(navController, candidates) }
        composable("personero") { PersoneroScreen(navController, candidates, voteManager) }
        composable("confirmation") { ConfirmationScreen(navController) }
        composable("admin") { AdminScreen(candidates, voteManager) }
    }
}
