package com.example.finanfinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

class Pantallapersona : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                PersonaPantalla(navController = rememberNavController())
            }
        }
    }
}

@Composable
fun PersonaPantalla(navController: NavController) {
    val gradientColors = listOf(Color(0xFFA5D5A6), Color(0xFFA4D7E1))
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(gradientColors))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            BackButton(navController)
            Spacer(modifier = Modifier.height(25.dp))
            PersonalCard()
            Spacer(modifier = Modifier.height(20.dp))

            // Opciones con navegación
            OptionSection(
                text = "INGRESO",
                iconResId = R.drawable.ingreso,
                onClick = { navController.navigate("pantallaIngreso") }
            )
            Spacer(modifier = Modifier.height(30.dp))
            OptionSection(
                text = "GASTOS",
                iconResId = R.drawable.gastopersonaldos,
                onClick = { navController.navigate("pantallaGasto") }
            )
            Spacer(modifier = Modifier.height(30.dp))
            OptionSection(
                text = "PENDIENTES",
                iconResId = R.drawable.pendientepersonal,
                onClick = { navController.navigate("pantallaPendiente") }
            )
            Spacer(modifier = Modifier.height(30.dp))

            // Nuevo botón RESUMEN
            OptionSection(
                text = "RESUMEN",
                iconResId = R.drawable.resume,  // Debes agregar un ícono para resumen
                onClick = { navController.navigate("pantallaresumenpersona") } // Navega a la pantalla de resumen
            )
        }
    }
}


@Composable
fun BackButton(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.retroceder),
            contentDescription = "Back",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .clickable { navController.navigate("pantallaPersonaComercial") } // Navega a PersonaComercial
                .align(Alignment.TopStart)
                .shadow(4.dp, CircleShape)
        )
    }
}


@Composable
fun PersonalCard() {
    val robotoFont = FontFamily(Font(R.font.roboto_regular))
    Box(
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth()
            .padding(16.dp)
            .shadow(4.dp, RoundedCornerShape(16.dp))
            .background(Color(0xFF2E7D32), shape = RoundedCornerShape(16.dp))
    ) {
        Image(
            painter = painterResource(R.drawable.finanzas),
            contentDescription = "Personal",
            modifier = Modifier
                .align(Alignment.Center)
                .size(300.dp),
            contentScale = ContentScale.Fit
        )
        Text(
            text = "PERSONAL",
            style = TextStyle(
                fontFamily = robotoFont,
                fontSize = 20.sp,
                color = Color.White
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(8.dp)
        )
    }
}

@Composable
fun OptionSection(text: String, iconResId: Int, onClick: () -> Unit) {
    val robotoFont = FontFamily(Font(R.font.roboto_regular))
    Row(
        modifier = Modifier
            .padding(horizontal = 32.dp)
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(8.dp))
            .background(Color(0xFF388E3C), shape = RoundedCornerShape(8.dp))
            .clickable { onClick() } // Acción de navegación
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(iconResId),
            contentDescription = text,
            modifier = Modifier.size(50.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            style = TextStyle(
                fontFamily = robotoFont,
                fontSize = 16.sp,
                color = Color.White
            )
        )
    }
}
