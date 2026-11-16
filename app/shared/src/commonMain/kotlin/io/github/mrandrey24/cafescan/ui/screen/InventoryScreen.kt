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
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.ShoppingCart
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
import androidx.compose.ui.unit.sp
import io.github.mrandrey24.cafescan.domain.entities.StockItem
import io.github.mrandrey24.cafescan.domain.entities.StockStatus
import io.github.mrandrey24.cafescan.presentation.InventoryViewModel
import io.github.mrandrey24.cafescan.ui.components.StockBarItem
import io.github.mrandrey24.cafescan.ui.components.UpdateStockDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryScreen(
    onBack: () -> Unit,
    onNavigateToScanner: () -> Unit,
    onNavigateToDashboard: () -> Unit,
    onNavigateToCatalog: () -> Unit,
    onNavigateToLowStock: () -> Unit = {},
    viewModel: InventoryViewModel,
) {
  val uiState by viewModel.uiState.collectAsState()
  var selectedItemForEdit by remember { mutableStateOf<StockItem?>(null) }

  LaunchedEffect(Unit) { viewModel.loadInventory() }

  Scaffold(
      topBar = {
        CenterAlignedTopAppBar(
            title = {
              Text(
                  "Today's Stock",
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
            actions = {
              IconButton(onClick = { /* Open Cart */ }) {
                Icon(
                    Icons.Default.ShoppingCart,
                    contentDescription = "Cart",
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
              onClick = { /* Already here */ },
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
      // Date Header
      item {
        Column {
          Text(
              "Today's Stock",
              style = MaterialTheme.typography.headlineSmall,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface,
          )
          Text(
              "${uiState.items.size} active products",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.outline,
          )
        }
      }

      // Low stock warning banner if any
      val lowStockCount =
          uiState.items.count {
            it.status == StockStatus.LOW ||
                it.status == StockStatus.CRITICAL ||
                it.status == StockStatus.OUT_OF_STOCK
          }
      if (lowStockCount > 0) {
        item {
          Surface(
              modifier = Modifier.fillMaxWidth().clickable { onNavigateToLowStock() },
              shape = MaterialTheme.shapes.large,
              color = MaterialTheme.colorScheme.error,
          ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
              Box(
                  modifier =
                      Modifier.size(32.dp)
                          .background(
                              MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f),
                              CircleShape,
                          ),
                  contentAlignment = Alignment.Center,
              ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(20.dp),
                )
              }

              Text(
                  text = "$lowStockCount products with low stock - tap to view",
                  style = MaterialTheme.typography.bodyMedium,
                  color = MaterialTheme.colorScheme.onPrimary,
                  modifier = Modifier.weight(1f),
              )

              Icon(
                  imageVector = Icons.Default.ChevronRight,
                  contentDescription = null,
                  tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
              )
            }
          }
        }
      }

      // Category: PRODUCTS
      item { CategoryHeader(title = "PRODUCTS", count = "${uiState.items.size} items") }

      if (uiState.items.isEmpty()) {
        item {
          Box(
              modifier = Modifier.fillMaxWidth().padding(32.dp),
              contentAlignment = Alignment.Center,
          ) {
            Text(
                "No products in inventory yet.\nCreate one in the Catalog screen!",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline,
            )
          }
        }
      } else {
        items(uiState.items, key = { it.product.id }) { itemWithStock ->
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

data class Quad<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)

@Composable
fun CategoryHeader(title: String, count: String) {
  Row(
      modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically,
  ) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.outline,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp,
    )
    Text(
        text = count.uppercase(),
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.outline,
        letterSpacing = 0.5.sp,
    )
  }
}
