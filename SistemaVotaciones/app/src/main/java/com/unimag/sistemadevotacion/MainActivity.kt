package com.unimag.sistemadevotacion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.unimag.sistemadevotacion.ui.AppNavigation
import com.unimag.sistemadevotacion.ui.theme.SistemaDeVotacionTheme

/**
 * MainActivity es la actividad principal y el punto de entrada de la aplicación.
 */
class MainActivity : ComponentActivity() {

    /**
     * Este método se llama cuando se crea la actividad.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Habilita el modo Edge-to-Edge para que la app se dibuje a pantalla completa.
        enableEdgeToEdge()
        // setContent es una función de Jetpack Compose que define el layout de la actividad.
        setContent {
            // SistemaDeVotacionTheme aplica el tema personalizado de la app.
            SistemaDeVotacionTheme {
                // AppNavigation es el componente Composable que gestiona la navegación
                // entre las diferentes pantallas de la aplicación.
                AppNavigation(applicationContext)
            }
        }
    }
}
