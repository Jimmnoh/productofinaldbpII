package com.example.finanfinal

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.text.input.PasswordVisualTransformation


@Composable
fun PantallaBienvenida(navController: NavController) {
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
            CircularImageExample()
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "BIENVENIDO A FINAN",
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(20.dp))

            // Campos de texto para correo y contraseña
            OutlinedTextField(
                value = "", // Puedes enlazar esta variable con un estado de correo
                onValueChange = { /* Actualiza el correo */ },
                label = { Text("Correo Gmail") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = "", // Puedes enlazar esta variable con un estado de contraseña
                onValueChange = { /* Actualiza la contraseña */ },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))

            // Botón para continuar sin registrar
            Button(
                onClick = { navController.navigate("pantallaPersonaComercial") },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text(text = "Continuar sin registrar", color = Color.Black)
            }
            Spacer(modifier = Modifier.height(10.dp))

            // Botón para registrarse
            TextButton(
                onClick = { navController.navigate("pantallaRegistro") } // Navegar a la pantalla de registro
            ) {
                Text("Registrarse", color = Color.Black)
            }
        }
    }
}

@Composable
fun CircularImageExample() {
    Image(
        painter = painterResource(id = R.drawable.libertad),
        contentDescription = null,
        modifier = Modifier
            .size(150.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppTheme {
        PantallaBienvenida(navController = rememberNavController())
    }
}

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(),
        typography = Typography(),
        content = content
    )
}
