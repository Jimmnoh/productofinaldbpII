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

class PantallaResumenComercial : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ResumenComercialPantalla()
        }
    }
}

@Composable
fun ResumenComercialPantalla() {
    // Firebase Instance
    val db = FirebaseFirestore.getInstance()

    // State variables
    val ventasData = remember { mutableStateOf<List<Map<String, Any>>>(emptyList()) }
    val comprasData = remember { mutableStateOf<List<Map<String, Any>>>(emptyList()) }
    val ingresosData = remember { mutableStateOf<List<Map<String, Any>>>(emptyList()) }
    val gastosData = remember { mutableStateOf<List<Map<String, Any>>>(emptyList()) }

    // Fetch data
    LaunchedEffect(Unit) {
        fetchFirestoreData(db, "ventasComerciales", ventasData)
        fetchFirestoreData(db, "comprasComerciales", comprasData)
        fetchFirestoreData(db, "ingresosComerciales", ingresosData)
        fetchFirestoreData(db, "gastosComerciales", gastosData)
    }

    // UI - Gradient Background
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
            // Título
            item {
                Text(
                    text = "Resumen Comercial",
                    style = MaterialTheme.typography.headlineLarge.copy(fontSize = 28.sp),
                    color = Color(0xFF388E3C),
                    modifier = Modifier.padding(8.dp)
                )
            }

            // Secciones
            item { SectionCard("Ventas Comerciales", ventasData.value) }
            item { SectionCard("Compras Comerciales", comprasData.value) }
            item { SectionCard("Ingresos Comerciales", ingresosData.value) }
            item { SectionCard("Gastos Comerciales", gastosData.value) }
        }
    }
}

@Composable
fun SectionCard(title: String, dataList: List<Map<String, Any>>) {
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
            // Section Title
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp),
                color = Color(0xFF388E3C),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Data Items
            if (dataList.isEmpty()) {
                Text("No hay datos disponibles", color = Color.Gray)
            } else {
                dataList.forEach { item ->
                    Text(
                        text = "Monto: ${item["monto"]}, Descripción: ${item["descripcion"]}, Fecha: ${item["fecha"]}",
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                        color = Color.Black,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
            }
        }
    }
}

fun fetchFirestoreData(
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
