package com.example.finanfinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontFamily
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FieldValue

class Pantallaventas : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VentasPantalla(navController = rememberNavController())
        }
    }
}

@Composable
fun VentasPantalla(navController: NavController) {
    var monto by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }

    val backgroundBrush = Brush.verticalGradient(
        colors = listOf(Color(0xFFA5D5A6), Color(0xFFA4D7E1))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = backgroundBrush)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = { navController.navigate("pantallacomercial") }) {
                Icon(
                    painter = painterResource(id = R.drawable.retroceder),
                    contentDescription = "Retroceder",
                    tint = Color.Green,
                    modifier = Modifier.size(40.dp)
                )
            }
        }

        Button(
            onClick = { navController.navigate("VentaPantalla") },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text(text = "Pantalla Venta", color = Color.Black)
        }

        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ventascomercial),
                contentDescription = "Icono de ventas",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Text(
                text = "VENTAS",
                color = Color.Black,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = robotoFont
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de Monto
        CampoTexto(label = "MONTO", isWhiteBackground = true, textState = monto, onValueChange = { monto = it })

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de Descripción
        CampoTexto(label = "DESCRIPCION", isWhiteBackground = true, textState = descripcion, onValueChange = { descripcion = it })

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de Fecha
        CampoTexto(label = "FECHA", isWhiteBackground = true, textState = fecha, onValueChange = { fecha = it })

        Spacer(modifier = Modifier.height(32.dp))

        // Botón Guardar
        Button(
            onClick = {
                saveVentaData(monto, descripcion, fecha)
                navController.navigate("pantallaresumencomercial")
            },
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF388E3C))
        ) {
            Text(text = "GUARDAR", fontSize = 18.sp, color = Color.White, fontFamily = robotoFont)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón Resumen
        Button(
            onClick = { /* Acción resumen */ },
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF388E3C)) // Color verde
        ) {
            Text(text = "RESUMEN", fontSize = 18.sp, color = Color.White, fontFamily = robotoFont)
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun CampoTexto(label: String, isWhiteBackground: Boolean = false, textState: String, onValueChange: (String) -> Unit) {
    Column {
        Text(text = label, fontSize = 16.sp, color = Color.Black, fontFamily = robotoFont)
        BasicTextField(
            value = textState,
            onValueChange = onValueChange, // Actualiza el estado
            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp, fontFamily = robotoFont),
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(if (isWhiteBackground) Color.White else Color.Transparent) // Fondo blanco si se indica
                .border(1.dp, Color.Black),
            cursorBrush = SolidColor(Color.Black),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp)
                        .padding(8.dp)
                ) {
                    innerTextField()
                }
            }
        )
    }
}

fun saveVentaData(monto: String, descripcion: String, fecha: String) {
    val db = FirebaseFirestore.getInstance()

    val ventaData = hashMapOf(
        "monto" to monto,
        "descripcion" to descripcion,
        "fecha" to fecha,
        "timestamp" to FieldValue.serverTimestamp()
    )

    db.collection("ventasComerciales")
        .add(ventaData)
        .addOnSuccessListener { documentReference ->
            Log.d("Firestore", "Venta added with ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            Log.w("Firestore", "Error adding venta", e)
        }
}
