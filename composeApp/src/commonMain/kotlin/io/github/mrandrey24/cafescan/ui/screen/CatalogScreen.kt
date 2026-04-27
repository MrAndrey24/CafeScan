package io.github.mrandrey24.cafescan.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.mrandrey24.cafescan.presentation.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    onBack: () -> Unit,
    onNavigateToScanner: () -> Unit,
    onNavigateToDashboard: () -> Unit,
    onNavigateToInventory: () -> Unit,
    onNavigateToQrPreview: (Long) -> Unit,
    viewModel: ProductViewModel,
) {

    val uiState by viewModel.uiState.collectAsState()
    val emojis = listOf("☕", "🥐", "🍫", "🥤", "🧁", "🍵", "🥗")

    LaunchedEffect(uiState.isSaved, uiState.savedProductId) {
        if (uiState.isSaved && uiState.savedProductId != null) {
            val id = uiState.savedProductId!!
            viewModel.clearSaveState()
            onNavigateToQrPreview(id)
        }
    }

  Scaffold(
      topBar = {
        TopAppBar(
            title = {
              Column(
                  horizontalAlignment = Alignment.CenterHorizontally,
                  modifier = Modifier.fillMaxWidth(),
              ) {
                Text(
                    "New product",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
              }
            },
            navigationIcon = {
              Row(
                  verticalAlignment = Alignment.CenterVertically,
                  modifier = Modifier.clickable { onBack() }.padding(8.dp),
              ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.primary,
                )
              }
            },
            actions = {
              TextButton(onClick = { viewModel.saveProduct() },
                  enabled = uiState.isFormValid) {
                Text(
                    "Save",
                    color = if (uiState.isFormValid) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold,
                )
              }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
        )
      },
      bottomBar = {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            tonalElevation = 8.dp,
        ) {
          NavigationBarItem(
              selected = false,
              onClick = onNavigateToScanner,
              icon = { Icon(Icons.Default.QrCodeScanner, contentDescription = "Scanner") },
              label = { Text("Scanner") },
          )
          NavigationBarItem(
              selected = false,
              onClick = onNavigateToDashboard,
              icon = { Icon(Icons.Default.History, contentDescription = "Today") },
              label = { Text("Today") },
          )
          NavigationBarItem(
              selected = false,
              onClick = onNavigateToInventory,
              icon = { Icon(Icons.Default.Inventory, contentDescription = "Stock") },
              label = { Text("Stock") },
          )
          NavigationBarItem(
              selected = true,
              onClick = { /* Already here */ },
              icon = { Icon(Icons.Default.LocalOffer, contentDescription = "Catalog") },
              label = { Text("Catalog") },
              colors =
                  NavigationBarItemDefaults.colors(
                      selectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                      selectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                      indicatorColor = MaterialTheme.colorScheme.surfaceVariant,
                  ),
          )
        }
      },
      containerColor = MaterialTheme.colorScheme.background,
  ) { padding ->
    Column(
        modifier =
            Modifier.fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
      // Icon Selector
      Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            "Icon",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.outline,
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 4.dp),
        ) {
            items(emojis) { itemEmoji ->
            val isSelected = uiState.emoji == itemEmoji
            Box(
                modifier =
                    Modifier.size(56.dp)
                        .background(
                            if (isSelected) MaterialTheme.colorScheme.surfaceVariant
                            else MaterialTheme.colorScheme.surface,
                            RoundedCornerShape(12.dp),
                        )
                        .border(
                            width = if (isSelected) 2.dp else 1.dp,
                            color =
                                if (isSelected) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(12.dp),
                        )
                        .clickable { viewModel.onEmojiChange(itemEmoji) },
                contentAlignment = Alignment.Center,
            ) {
              Text(itemEmoji, fontSize = 24.sp)
            }
          }
        }
      }

      // Product Name
      Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            "Product name",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.outline,
        )
        OutlinedTextField(
            value = uiState.name,
            onValueChange = {  viewModel.onNameChanged(it) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            isError = uiState.isProductNameExists,
            colors =
                OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    errorBorderColor = MaterialTheme.colorScheme.error
                ),
        )

          if (uiState.isProductNameExists) {
              Text(
                  "Product name already exists",
                  color = MaterialTheme.colorScheme.error,
                  style = MaterialTheme.typography.labelSmall
              )
          }
      }

      // Price & Cost Row
      Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Text(
              "Sale price",
              style = MaterialTheme.typography.labelLarge,
              color = MaterialTheme.colorScheme.outline,
          )
          OutlinedTextField(
              value = uiState.price,
              onValueChange = { viewModel.onPriceChange(it)},
              prefix = { Text("₡ ") },
              shape = RoundedCornerShape(12.dp),
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
              colors =
                  OutlinedTextFieldDefaults.colors(
                      focusedContainerColor = MaterialTheme.colorScheme.surface,
                      unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                  ),
          )
        }
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Text(
              "Cost",
              style = MaterialTheme.typography.labelLarge,
              color = MaterialTheme.colorScheme.outline,
          )
          OutlinedTextField(
              value = uiState.cost,
              onValueChange = { viewModel.onCostChange(it) },
              prefix = { Text("₡ ") },
              shape = RoundedCornerShape(12.dp),
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
              colors =
                  OutlinedTextFieldDefaults.colors(
                      focusedContainerColor = MaterialTheme.colorScheme.surface,
                      unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                  ),
          )
        }
      }

      // Profit line
      Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(
            "Estimated profit",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.outline,
        )
        Text(
            "₡${uiState.margin} · ${uiState.marginPercent}%",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
            fontWeight = FontWeight.Bold,
        )
      }

      // Initial Stock
      Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            "Initial stock",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.outline,
        )
        OutlinedTextField(
            value = uiState.stock,
            onValueChange = { viewModel.onStockChange(it) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors =
                OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                ),
        )
      }

      Spacer(modifier = Modifier.height(8.dp))

      // Submit Button
      Button(
          onClick = {
            viewModel.saveProduct()
          },
          modifier = Modifier.fillMaxWidth().height(56.dp),
          shape = MaterialTheme.shapes.large,
          enabled = uiState.isFormValid,
          colors =
              ButtonDefaults.buttonColors(
                  containerColor = MaterialTheme.colorScheme.secondary,
                  contentColor = MaterialTheme.colorScheme.onSecondary,
              ),
      ) {
        Text("Save and generate QR", fontWeight = FontWeight.Bold, fontSize = 16.sp)
      }

      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}
