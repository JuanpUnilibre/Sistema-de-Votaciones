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
    // rememberNavController() asegura que el NavController persista a través de las recomposiciones.
    val navController = rememberNavController()

    // Se crea una lista de candidatos predefinida con 2 por cada rol.
    val candidates = listOf(
        Candidate(1, "Juan Perez", Role.CONTRALOR, imageRes = R.drawable.contralor_1),
        Candidate(2, "Maria Gomez", Role.CONTRALOR, imageRes = R.drawable.contralor_2),
        // Se elimina el tercer candidato a contralor.
        Candidate(4, "Ana Lopez", Role.PERSONERO, imageRes = R.drawable.personero_1),
        Candidate(5, "Luis Diaz", Role.PERSONERO, imageRes = R.drawable.personero_2)
        // Se elimina el tercer candidato a personero.
    )

    // Inicializa el VoteManager, que se encarga de la lógica de negocio de los votos.
    val voteManager = VoteManager(appContext)

    // NavHost es el contenedor para todas las pantallas (destinos) de la navegación.
    // Se define la pantalla inicial (startDestination) como "welcome".
    NavHost(navController = navController, startDestination = "welcome") {
        // Define la ruta "welcome" y el Composable que se mostrará.
        composable("welcome") { WelcomeScreen(navController) }

        // Define la ruta "pin" para la pantalla de ingreso de PIN.
        composable("pin") { PinScreen(navController) }

        // Define la ruta "contralor" para la pantalla de votación de contralor.
        composable("contralor") { ContralorScreen(navController, candidates) }

        // Define la ruta "personero" para la pantalla de votación de personero.
        composable("personero") { PersoneroScreen(navController, candidates, voteManager) }

        // Define la ruta "confirmation" para la pantalla de confirmación.
        composable("confirmation") { ConfirmationScreen(navController) }

        // Define la ruta "admin" para la pantalla de administración.
        composable("admin") { AdminScreen(candidates, voteManager) }
    }
}
