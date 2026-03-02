package com.unimag.sistemadevotacion.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.unimag.sistemadevotacion.R
import com.unimag.sistemadevotacion.data.Candidate
import com.unimag.sistemadevotacion.data.Role
import com.unimag.sistemadevotacion.data.VoteManager

/**
 * AppNavigation es el Composable que define el grafo de navegación de la aplicación.
 * Gestiona qué pantalla se muestra en cada momento.
 *
 * @param appContext El contexto de la aplicación, necesario para inicializar VoteManager.
 */
@Composable
fun AppNavigation(appContext: Context) {
    // Crea un NavController que se encargará de gestionar la pila de navegación.
    val navController = rememberNavController()

    // Se crea una lista de candidatos predefinida con 2 por cada rol.
    val candidates = listOf(
        Candidate(1, "Juan Perez", Role.CONTRALOR, imageRes = R.drawable.contralor_1),
        Candidate(2, "Maria Gomez", Role.CONTRALOR, imageRes = R.drawable.contralor_2),
        Candidate(4, "Ana Lopez", Role.PERSONERO, imageRes = R.drawable.personero_1),
        Candidate(5, "Luis Diaz", Role.PERSONERO, imageRes = R.drawable.personero_2)
    )

    val voteManager = VoteManager(appContext)

    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") { WelcomeScreen(navController) }
        composable("pin") { PinScreen(navController) }
        composable("contralor") { ContralorScreen(navController, candidates) }
        composable("personero") { PersoneroScreen(navController, candidates, voteManager) }
        composable("confirmation") { ConfirmationScreen(navController) }

        // La ruta "admin" ahora apunta al nuevo AdminDashboardScreen.
        composable("admin") { AdminDashboardScreen(voteManager, candidates) }
    }
}
