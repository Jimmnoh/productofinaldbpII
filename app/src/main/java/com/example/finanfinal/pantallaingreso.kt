package com.example.finanfinal

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.runtime.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.foundation.border
import androidx.compose.ui.draw.clip
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore

class PantallaIngreso : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}

@Composable
fun IngresoPantalla(navController: NavController) {
    var monto by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }

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
            IconButton(onClick = { navController.navigate("pantallaPersona") }) {
                Icon(
                    painter = painterResource(id = R.drawable.retroceder),
                    contentDescription = "Retroceder",
                    tint = Color.Green,
                    modifier = Modifier.size(40.dp)
                )
            }
        }

        // Imagen de Ingreso con texto
        Box(
            modifier = Modifier
                .size(150.dp) // Aumentar tamaño de la imagen
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
                color = Color.Black,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de Monto
        CampoEntradass(label = "MONTO", isWhiteBackground = true, textState = monto, onValueChange = { monto = it })

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de Descripción
        CampoEntradass(label = "DESCRIPCION", isWhiteBackground = true, textState = descripcion, onValueChange = { descripcion = it })

        Spacer(modifier = Modifier.height(32.dp))

        // Guardar
        Button(
            onClick = {
                // Guardar los datos en Firestore
                saveIngresoData(monto, descripcion)
                navController.navigate("pantallaresumenpersona") // Navegar a la pantalla de resumen
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

// Función para guardar los datos en Firestore
// Función de guardado de datos de ingreso
fun saveIngresoData(monto: String, descripcion: String) {
    val db = FirebaseFirestore.getInstance()

    val ingresoData = hashMapOf(
        "monto" to monto,
        "descripcion" to descripcion,
        "fecha" to System.currentTimeMillis() // Timestamp de la fecha
    )

    // Guardar en la colección 'ingresos'
    db.collection("ingresos")
        .add(ingresoData)
        .addOnSuccessListener { documentReference ->
            Log.d("Firestore", "Documento añadido con ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            Log.e("Firestore", "Error al añadir documento", e)
            // Aquí puedes mostrar un mensaje o realizar alguna acción si falla
        }
}


@Composable
fun CampoEntradass(label: String, isWhiteBackground: Boolean = false, textState: String, onValueChange: (String) -> Unit) {
    Column {
        Text(text = label, fontSize = 16.sp, color = Color.Black)
        BasicTextField(
            value = textState,
            onValueChange = onValueChange, // Actualizar el estado del texto
            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
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