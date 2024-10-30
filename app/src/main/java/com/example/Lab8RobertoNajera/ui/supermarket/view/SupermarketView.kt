package com.example.Lab8RobertoNajera.ui.supermarket.view

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import coil.compose.rememberImagePainter
import com.example.Lab8RobertoNajera.database.supermarket.SupermarketItem
import com.example.Lab8RobertoNajera.ui.supermarket.viewmodel.SupermarketViewModel
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupermarketScreen(viewModel: SupermarketViewModel) {
    val items by viewModel.items.collectAsState()
    val context = LocalContext.current

    var showEditDialog by remember { mutableStateOf(false) }
    var itemToEdit by remember { mutableStateOf<SupermarketItem?>(null) }

    var showAddDialog by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var selectedItemId by remember { mutableStateOf<Int?>(null) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            imageUri?.let { uri ->
                selectedItemId?.let { itemId ->
                    viewModel.addImagePathToItem(uri.toString(), itemId)
                    selectedItemId = null // Limpiar el itemId después de actualizar
                }
            }
        }
    }

    // Al abrir la cámara, actualiza la URI y lanza el Intent
    fun captureImageForItem(itemId: Int) {
        imageUri = getImageUri(context)
        selectedItemId = itemId
        launcher.launch(imageUri!!)
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Lista de Supermercado") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar artículo")
            }
        }
    ) { paddingValues ->
        LazyColumn(contentPadding = paddingValues) {
            items(items) { item ->
                SupermarketItemCard(item = item,
                    onDelete = { viewModel.deleteItem(item)},
                    onCaptureImage = { captureImageForItem(item.id) },
                    onEdit = {
                        itemToEdit = it
                        showEditDialog = true
                    }
                )
            }
        }
    }
    itemToEdit?.let { item ->
        if (showEditDialog) {
            EditItemDialog(
                item = item,
                onConfirm = { newName, newQuantity ->
                    viewModel.updateItem(item, newName, newQuantity)
                    showEditDialog = false
                },
                onDismiss = { showEditDialog = false }
            )
        }
    }
    if (showAddDialog) {
        AddItemDialog(viewModel = viewModel, onDismiss = { showAddDialog = false })
    }
}

@Composable
fun SupermarketItemCard(
    item: SupermarketItem,
    onDelete: () -> Unit,
    onCaptureImage: () -> Unit,
    onEdit: (SupermarketItem) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onEdit(item) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            if (item.imagePath != null) {
                Image(
                    painter = rememberImagePainter(data = item.imagePath),
                    contentDescription = null,
                    modifier = Modifier.size(64.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = item.itemName, style = MaterialTheme.typography.headlineSmall)
                Text(text = "Cantidad: ${item.quantity}", style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar artículo")
            }
            IconButton(onClick = onCaptureImage) {
                Icon(Icons.Default.Add, contentDescription = "Capturar imagen")
            }
            IconButton(onClick = { onEdit(item) }) {
                Icon(Icons.Default.Edit, contentDescription = "Editar artículo")
            }
        }
    }
}

@Composable
fun AddItemDialog(viewModel: SupermarketViewModel, onDismiss: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf(1) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Agregar Artículo") },
        text = {
            Column {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre del artículo") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = quantity.toString(),
                    onValueChange = { quantity = it.toIntOrNull() ?: 1 },
                    label = { Text("Cantidad") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                viewModel.addItem(SupermarketItem(itemName = name, quantity = quantity))
                onDismiss()
            }) {
                Text("Agregar")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun EditItemDialog(
    item: SupermarketItem,
    onConfirm: (String, Int) -> Unit,
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf(item.itemName) }
    var quantity by remember { mutableStateOf(item.quantity) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Editar Artículo") },
        text = {
            Column {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre del artículo") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = quantity.toString(),
                    onValueChange = { quantity = it.toIntOrNull() ?: 1 },
                    label = { Text("Cantidad") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                if (name.isNotEmpty()) {
                    onConfirm(name, quantity)
                    onDismiss()
                }
            }) {
                Text("Guardar")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

fun getImageUri(context: Context): Uri {
    val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val imageFile = File.createTempFile("item_image", ".jpg", storageDir)
    return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", imageFile)
}