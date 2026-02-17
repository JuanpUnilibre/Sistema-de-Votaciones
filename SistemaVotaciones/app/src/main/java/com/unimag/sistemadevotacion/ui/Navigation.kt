package com.unimag.sistemadevotacion.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.unimag.sistemadevotacion.R
import com.unimag.sistemadevotacion.data.Candidate
import com.unimag.sistemadevotacion.data.VoteManager
import com.unimag.sistemadevotacion.data.Role


@Composable
fun AppNavigation(appContext: Context) {
    val navController = rememberNavController()

    // lista de candidatos prepopulada (usa drawables que agregues)
    val candidates = listOf(
        Candidate(1, "Juan Perez", Role.CONTRALOR, imageRes = R.drawable.contralor_1),
        Candidate(2, "Maria Gomez", Role.CONTRALOR, imageRes = R.drawable.contralor_2),
        Candidate(3, "Pedro Ruiz", Role.CONTRALOR, imageRes = R.drawable.contralor_3),
        Candidate(4, "Ana Lopez", Role.PERSONERO, imageRes = R.drawable.personero_1),
        Candidate(5, "Luis Diaz", Role.PERSONERO, imageRes = R.drawable.personero_2),
        Candidate(6, "Camila Rios", Role.PERSONERO, imageRes = R.drawable.personero_3)
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
