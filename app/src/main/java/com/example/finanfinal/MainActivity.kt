package com.example.finanfinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainAppContent()
                }
            }
        }
    }
}

@Composable
fun MainAppContent() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "pantallaBienvenida") {
        // Pantalla de bienvenida
        composable("pantallaBienvenida") { PantallaBienvenida(navController) }

        // Pantalla intermedia: Personal/Comercial
        composable("pantallaPersonaComercial") { PantallaPersonaComercial(navController) }
        composable("pantallaRegistro") { PantallaRegistro(navController) }
        // Opciones dentro de "PERSONAL"
        composable("pantallaPersona") { PersonaPantalla(navController) }
        composable("pantallaIngreso") { IngresoPantalla(navController) }
        composable("pantallaGasto") { GastoPantalla(navController) }
        composable("pantallaPendiente") { PendientePantalla(navController) }
        composable("pantallaresumenpersona") { ResumenPantalla() }
        composable("pantallaresumencomercial") { ResumenComercialPantalla() }


        // Opciones dentro de "COMERCIAL"
        composable("pantallaComercial") { ComercialPantalla(navController) }
        composable("pantallaVentas") { VentasPantalla(navController) }
        composable("pantallaCompras") { ComprasPantalla(navController) }
        composable("pantallaIngresoCom") { IngresoCom(navController) }
        composable("pantallaGastoCom") { GastosComScreen(navController) }
    }
}

