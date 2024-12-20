package com.example.finanfinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

class PantallaComercial : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComercialPantalla(navController = rememberNavController())
        }
    }
}

@Composable
fun ComercialPantalla(navController: NavController) {
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
            BotonRetroceder(navController)
            Spacer(modifier = Modifier.height(25.dp))
            TarjetaPersonal()
            Spacer(modifier = Modifier.height(20.dp))

            // Opciones con navegación
            SeccionOpciones(
                text = "VENTAS",
                iconResId = R.drawable.ventascomercial,
                onClick = { navController.navigate("pantallaVentas") }
            )
            Spacer(modifier = Modifier.height(30.dp))

            SeccionOpciones(
                text = "COMPRAS",
                iconResId = R.drawable.comprascomercio,
                onClick = { navController.navigate("pantallaCompras") }
            )
            Spacer(modifier = Modifier.height(30.dp))

            SeccionOpciones(
                text = "INGRESO",
                iconResId = R.drawable.ingreso,
                onClick = { navController.navigate("pantallaIngresoCom") }
            )
            Spacer(modifier = Modifier.height(30.dp))

            SeccionOpciones(
                text = "GASTO",
                iconResId = R.drawable.gastocomercial,
                onClick = { navController.navigate("pantallaGastoCom") }
            )

            // Nuevo botón RESUMEN
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                onClick = { navController.navigate("pantallaResumenComercial") }, // Redirigir a la pantalla de resumen
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF388E3C))
            ) {
                Text(text = "RESUMEN", fontSize = 18.sp, color = Color.White)
            }
        }
    }
}

@Composable
fun BotonRetroceder(navController: NavController? = null) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.retroceder),
            contentDescription = "Volver",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .clickable {
                    navController?.navigate("pantallapersonacomercial") // Navegar a pantallaPersonaComercial
                }
                .align(Alignment.TopStart)
        )
    }
}

@Composable
fun TarjetaPersonal() {
    val robotoFont = FontFamily(Font(R.font.roboto_regular))
    Box(
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.comercio),
            contentDescription = "Comercio",
            modifier = Modifier
                .align(Alignment.Center)
                .size(300.dp),
            contentScale = ContentScale.Fit
        )
        Text(
            text = "COMERCIO",
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
fun SeccionOpciones(text: String, iconResId: Int, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .clickable { onClick() } // Acción de navegación
            .background(Color(0xFF388E3C), shape = CircleShape)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(iconResId),
            contentDescription = text,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                fontSize = 16.sp,
                color = Color.White
            )
        )
    }
}
