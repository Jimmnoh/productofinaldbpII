package com.example.finanfinal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun PantallaRegistro(navController: NavController) {
    // Variables de estado para correo, contraseña y confirmación de contraseña
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFA5D5A6), Color(0xFFA4D7E1))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "REGISTRO",
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(20.dp))

            // Campo de texto para correo
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo Gmail") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Campo de texto para contraseña
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                trailingIcon = {
                    val icon = if (passwordVisible) "🙈" else "👁️"
                    TextButton(onClick = { passwordVisible = !passwordVisible }) {
                        Text(icon)
                    }
                }
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Campo de texto para confirmar contraseña
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirmar Contraseña") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(20.dp))

            // Botón de registro
            Button(
                onClick = {
                    if (email.isNotBlank() && password == confirmPassword && password.isNotEmpty()) {
                        navController.navigate("pantallaPersonaComercial")
                    } else {
                        // Puedes mostrar una alerta o snackbar aquí
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text(text = "Registrarse", color = Color.Black)
            }
            Spacer(modifier = Modifier.height(10.dp))

            // Botón para regresar
            TextButton(
                onClick = { navController.popBackStack() } // Regresa a la pantalla anterior
            ) {
                Text("Volver", color = Color.Black)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaRegistroPreview() {
    AppTheme {
        PantallaRegistro(navController = rememberNavController())
    }
}
