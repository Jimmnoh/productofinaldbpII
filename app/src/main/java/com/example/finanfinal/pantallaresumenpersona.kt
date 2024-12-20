package com.example.finanfinal

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore

class PantallaResumenPersona : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                ResumenPantalla()
            }
        }
    }
}

@Composable
fun ResumenPantalla() {
    val db = FirebaseFirestore.getInstance()

    // Variables para almacenar los datos recuperados
    val ingresoData = remember { mutableStateOf<List<Map<String, Any>>>(emptyList()) }
    val gastoData = remember { mutableStateOf<List<Map<String, Any>>>(emptyList()) }
    val pendienteData = remember { mutableStateOf<List<Map<String, Any>>>(emptyList()) }

    // Obtener los datos de Firestore
    LaunchedEffect(true) {
        // Obtener datos de ingresos, gastos y pendientes
        fetchFirestoreData(db, "ingresos", ingresoData)
        fetchFirestoreData(db, "gastos", gastoData)
        fetchFirestoreData(db, "pendientes", pendienteData)
    }

    // Fondo degradado
    val backgroundBrush = Brush.verticalGradient(
        colors = listOf(Color(0xFFA5D5A6), Color(0xFFA4D7E1))
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = backgroundBrush)
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título de la pantalla
            item {
                Text(
                    text = "Resumen de la sesión",
                    style = MaterialTheme.typography.headlineLarge.copy(fontSize = 24.sp),
                    color = Color(0xFF388E3C),
                    modifier = Modifier.padding(8.dp)
                )
            }

            // Sección de Ingresos
            item { SectionCards("Ingresos", ingresoData.value) }

            // Sección de Gastos
            item { SectionCards("Gastos", gastoData.value) }

            // Sección de Pendientes
            item { SectionCards("Pendientes", pendienteData.value) }

            // Botón para cerrar sesión
            item {
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = { /* Acción para cerrar sesión o regresar */ },
                    modifier = Modifier.fillMaxWidth(0.6f).height(50.dp)
                ) {
                    Text(text = "CERRAR SESIÓN", fontSize = 18.sp)
                }
            }
        }
    }
}

@Composable
fun SectionCards(title: String, dataList: List<Map<String, Any>>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            // Título de la sección
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp),
                color = Color(0xFF388E3C),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Mostrar los datos de la sección
            if (dataList.isEmpty()) {
                Text("No hay datos disponibles", color = Color.Gray)
            } else {
                dataList.forEach { item ->
                    Text(
                        text = "Monto: ${item["monto"]}, Descripción: ${item["descripcion"]}",
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                        color = Color.Black,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
            }
        }
    }
}

fun fetchFirestoreDatas(
    db: FirebaseFirestore,
    collectionName: String,
    dataState: MutableState<List<Map<String, Any>>>
) {
    db.collection(collectionName)
        .get()
        .addOnSuccessListener { result ->
            dataState.value = result.documents.mapNotNull { it.data }
        }
        .addOnFailureListener { exception ->
            Log.w("Firestore", "Error fetching $collectionName", exception)
        }
}
