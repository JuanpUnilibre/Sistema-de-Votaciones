package com.unimag.sistemadevotacion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.unimag.sistemadevotacion.ui.theme.SistemaDeVotacionTheme
import com.unimag.sistemadevotacion.ui.WelcomeScreen
import com.unimag.sistemadevotacion.ui.AppNavigation


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SistemaDeVotacionTheme {
                AppNavigation(applicationContext)
            }
        }

    }
}