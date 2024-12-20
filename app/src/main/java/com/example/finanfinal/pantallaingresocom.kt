package com.example.finanfinal

import android.os.Bundle
import android.util.Log
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FieldValue
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

class Pantallaingresocom : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IngresoCom(navController = rememberNavController())
        }
    }
}

@Composable
fun IngresoCom(navController: NavController) {
    var monto by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }

    // Degradado para el fondo
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
        // Botón de retroceso
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

        // Imagen superior (icono de ingreso) con texto
        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ingreso),
                contentDescription = "Icono de INGRESO",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Text(
                text = "INGRESO",
                color = Color.Cyan,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de Monto
        InputField(label = "MONTO", onValueChange = { monto = it })

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de Descripción
        InputField(label = "DESCRIPCION", onValueChange = { descripcion = it })

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de Fecha
        InputField(label = "FECHA", onValueChange = { fecha = it })

        Spacer(modifier = Modifier.height(32.dp))

        // Botón Guardar
        Button(
            onClick = {
                saveIngresoData(monto, descripcion, fecha)
                navController.navigate("pantallaresumencomercial")
            },
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF388E3C))
        ) {
            Text(text = "GUARDAR", fontSize = 18.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón Resumen
        Button(
            onClick = { /* Acción resumen */ },
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF388E3C))
        ) {
            Text(text = "RESUMEN", fontSize = 18.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun InputField(label: String, onValueChange: (String) -> Unit) {
    var textState by remember { mutableStateOf("") }

    Column {
        Text(text = label, fontSize = 16.sp, color = Color.Black)
        BasicTextField(
            value = textState,
            onValueChange = {
                textState = it
                onValueChange(it)
            },
            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(Color.White)
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

fun saveIngresoData(monto: String, descripcion: String, fecha: String) {
    val db = FirebaseFirestore.getInstance()

    val ingresoData = hashMapOf(
        "monto" to monto,
        "descripcion" to descripcion,
        "fecha" to fecha,
        "timestamp" to FieldValue.serverTimestamp()
    )

    db.collection("ingresosComerciales")
        .add(ingresoData)
        .addOnSuccessListener { documentReference ->
            Log.d("Firestore", "Ingreso added with ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            Log.w("Firestore", "Error adding ingreso", e)
        }
}