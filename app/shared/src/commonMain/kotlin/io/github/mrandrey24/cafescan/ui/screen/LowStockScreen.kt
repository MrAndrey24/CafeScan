/**
 * MIT License
 *
 * Copyright (c) 2026 Andrey Acosta  Ramirez
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.github.mrandrey24.cafescan.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.mrandrey24.cafescan.domain.entities.StockItem
import io.github.mrandrey24.cafescan.domain.entities.StockStatus
import io.github.mrandrey24.cafescan.presentation.InventoryViewModel
import io.github.mrandrey24.cafescan.ui.components.StockBarItem
import io.github.mrandrey24.cafescan.ui.components.UpdateStockDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LowStockScreen(
    onBack: () -> Unit,
    onNavigateToScanner: () -> Unit,
    onNavigateToDashboard: () -> Unit,
    onNavigateToCatalog: () -> Unit,
    viewModel: InventoryViewModel,
) {
  val uiState by viewModel.uiState.collectAsState()
  var selectedItemForEdit by remember { mutableStateOf<StockItem?>(null) }

  LaunchedEffect(Unit) { viewModel.loadInventory() }

  val lowStockItems =
      remember(uiState.items) { uiState.items.filter { it.status != StockStatus.HEALTHY } }

  Scaffold(
      topBar = {
        CenterAlignedTopAppBar(
            title = {
              Text(
                  "Low Stock Alert",
                  style = MaterialTheme.typography.titleLarge,
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onSurface,
              )
            },
            navigationIcon = {
              IconButton(onClick = onBack) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.primary,
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
              selected = true,
              onClick = onBack,
              icon = { Icon(Icons.Default.Inventory, contentDescription = "Stock") },
              label = { Text("Stock") },
              colors =
                  NavigationBarItemDefaults.colors(
                      selectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                      selectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                      indicatorColor = MaterialTheme.colorScheme.surfaceVariant,
                  ),
          )
          NavigationBarItem(
              selected = false,
              onClick = onNavigateToCatalog,
              icon = { Icon(Icons.Default.LocalOffer, contentDescription = "Catalog") },
              label = { Text("Catalog") },
          )
        }
      },
      containerColor = MaterialTheme.colorScheme.background,
  ) { padding ->
    if (uiState.isLoading) {
      Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
      }
      return@Scaffold
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 24.dp, top = 8.dp),
    ) {
      // Low Stock Summary Banner
      item {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.errorContainer,
        ) {
          Row(
              modifier = Modifier.padding(16.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(12.dp),
          ) {
            Box(
                modifier =
                    Modifier.size(36.dp)
                        .background(
                            MaterialTheme.colorScheme.error.copy(alpha = 0.2f),
                            CircleShape,
                        ),
                contentAlignment = Alignment.Center,
            ) {
              Icon(
                  imageVector = Icons.Default.WarningAmber,
                  contentDescription = null,
                  tint = MaterialTheme.colorScheme.error,
                  modifier = Modifier.size(22.dp),
              )
            }

            Column(modifier = Modifier.weight(1f)) {
              Text(
                  text = "${lowStockItems.size} Products Require Attention",
                  style = MaterialTheme.typography.titleSmall,
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onErrorContainer,
              )
              Text(
                  text = "Tap any product to update stock and price",
                  style = MaterialTheme.typography.bodySmall,
                  color = MaterialTheme.colorScheme.onErrorContainer.copy(alpha = 0.8f),
              )
            }
          }
        }
      }

      // Products Section
      item { CategoryHeader(title = "LOW STOCK PRODUCTS", count = "${lowStockItems.size} items") }

      if (lowStockItems.isEmpty()) {
        item {
          Box(
              modifier = Modifier.fillMaxWidth().padding(48.dp),
              contentAlignment = Alignment.Center,
          ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
              Text("🎉", style = MaterialTheme.typography.displayMedium)
              Text(
                  "All products are well stocked!",
                  style = MaterialTheme.typography.titleMedium,
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onSurface,
              )
              Text(
                  "There are currently no items running low on stock.",
                  style = MaterialTheme.typography.bodyMedium,
                  color = MaterialTheme.colorScheme.outline,
              )
            }
          }
        }
      } else {
        items(lowStockItems, key = { it.product.id }) { itemWithStock ->
          val product = itemWithStock.product
          val current = itemWithStock.currentQty
          val total = itemWithStock.initialQty.coerceAtLeast(current)
          val status = itemWithStock.status

          val (statusLabel, statusColor, badgeLabel, badgeColor) =
              when (status) {
                StockStatus.HEALTHY -> Quad("Healthy", Color(0xFF4CAF50), null, Color.Transparent)
                StockStatus.LOW -> Quad("Running Out", Color(0xFFD4A373), "LOW", Color(0xFFD4A373))
                StockStatus.CRITICAL ->
                    Quad("Low Stock", Color(0xFFBA1A1A), "CRITICAL", Color(0xFFBA1A1A))
                StockStatus.OUT_OF_STOCK ->
                    Quad("Out of Stock", Color(0xFFBA1A1A), "OUT", Color(0xFFBA1A1A))
              }

          StockBarItem(
              name = product.name,
              subtitle = "₡${product.price}",
              current = current,
              total = if (total > 0) total else 10,
              statusLabel = statusLabel,
              statusColor = statusColor,
              badgeLabel = badgeLabel,
              badgeColor = badgeColor,
              emoji = product.emoji ?: "📦",
              onEditClick = { selectedItemForEdit = itemWithStock },
          )
        }
      }
    }
  }

  selectedItemForEdit?.let { itemToEdit ->
    UpdateStockDialog(
        item = itemToEdit,
        onDismiss = { selectedItemForEdit = null },
        onSave = { newStock, newPrice ->
          viewModel.updateStockAndPrice(itemToEdit.product.id, newStock, newPrice)
          selectedItemForEdit = null
        },
        onDelete = { productId ->
          viewModel.deleteProduct(productId)
          selectedItemForEdit = null
        },
    )
  }
}
