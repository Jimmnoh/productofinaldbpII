package com.example.finanfinal

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.finanfinal.robotoFont

@Composable
fun PantallaPersonaComercial(navController: NavController) {
    // Fondo degradado
    val gradientColors = listOf(Color(0xFFA5D5A6), Color(0xFFA4D7E1))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(gradientColors))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // Icono superior
            TopIcon()

            // Sección con imágenes y navegación
            OptionImages(navController)

            // Botón para regresar
            ResumeButton(navController)
        }
    }
}

@Composable
fun TopIcon() {
    Image(
        painter = painterResource(id = R.drawable.logosin), // Reemplaza con tu recurso
        contentDescription = "Icono Principal",
        modifier = Modifier
            .size(150.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun OptionImages(navController: NavController) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Opción PERSONAL con imagen
        OptionItem(
            imageResId = R.drawable.finanzas, // Reemplaza con tu recurso
            title = "PERSONAL"
        ) {
            navController.navigate("pantallaPersona")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Opción COMERCIAL con imagen
        OptionItem(
            imageResId = R.drawable.comercio, // Reemplaza con tu recurso
            title = "COMERCIAL"
        ) {
            navController.navigate("pantallaComercial")
        }
    }
}

@Composable
fun OptionItem(imageResId: Int, title: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        // Imagen circular
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = title,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Botón debajo de la imagen
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = title,
                style = TextStyle(fontSize = 20.sp, color = Color.Black, fontFamily = robotoFont)
            )
        }
    }
}

@Composable
fun ResumeButton(navController: NavController) {
    Button(
        onClick = { navController.navigate("pantallabienvenida") },  // Navega a la pantalla de bienvenida
        colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth(0.6f)
            .height(50.dp)
    ) {
        Text(
            text = "CERRAR SESION",
            style = TextStyle(fontSize = 18.sp, color = Color.White, fontFamily = robotoFont)
        )
    }
}

