/**
 * Copyright (c) 2026 Andrey Acosta Ramirez
 * All Rights Reserved.
 *
 * This software is confidential and proprietary. Unauthorized copying,
 * distribution, or modification of this file is strictly prohibited.
 *
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.mrandrey24.cafescan.domain.entities.OrderItem
import io.github.mrandrey24.cafescan.presentation.SaleViewModel
import io.github.mrandrey24.cafescan.presentation.ScannerViewModel
import io.github.mrandrey24.cafescan.ui.components.MetricCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onBack: () -> Unit,
    onNavigateToScanner: () -> Unit,
    onNavigateToInventory: () -> Unit,
    onNavigateToCatalog: () -> Unit,
    onNavigateToSummary: () -> Unit = {},
    saleViewModel: SaleViewModel,
    scannerViewModel: ScannerViewModel,
) {
  val saleUiState by saleViewModel.uiState.collectAsState()
  val scannerUiState by scannerViewModel.uiState.collectAsState()
  val lastSale = scannerUiState.lastSale

  LaunchedEffect(Unit) {
    saleViewModel.loadTodaySales()
  }

  val displayTotalCharged = if (saleUiState.todaySales.isNotEmpty()) saleUiState.totalChargedToday else (lastSale?.totalCharged ?: 0.0)
  val displayEstProfit = if (saleUiState.todaySales.isNotEmpty()) saleUiState.totalProfitToday else (lastSale?.estProfit ?: 0.0)
  val displayItems = if (saleUiState.todaySales.isNotEmpty()) saleUiState.totalItemsToday else (lastSale?.items?.sumOf { it.quantity } ?: 0)

  Scaffold(
      topBar = {
        CenterAlignedTopAppBar(
            title = {
              Text(
                  "Sale registered",
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
              IconButton(onClick = onNavigateToSummary) {
                Icon(
                    imageVector = Icons.Default.Analytics,
                    contentDescription = "Analytics",
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
              selected = true,
              onClick = { /* Already here */ },
              icon = { Icon(Icons.Default.History, contentDescription = "Today") },
              label = { Text("Today") },
              colors =
                  NavigationBarItemDefaults.colors(
                      selectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                      selectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                      indicatorColor = MaterialTheme.colorScheme.surfaceVariant,
                  ),
          )
          NavigationBarItem(
              selected = false,
              onClick = onNavigateToInventory,
              icon = { Icon(Icons.Default.Inventory, contentDescription = "Stock") },
              label = { Text("Stock") },
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
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(bottom = 24.dp),
    ) {
      // Success Indicator
      item {
        Box(
            modifier =
                Modifier.size(80.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
          Icon(
              imageVector = Icons.Default.Check,
              contentDescription = null,
              modifier = Modifier.size(40.dp),
              tint = MaterialTheme.colorScheme.primary,
          )
        }
      }

      // Metrics Grid
      item {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
          Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            MetricCard(
                label = "Total Charged",
                value = "₡${displayTotalCharged}",
                modifier = Modifier.weight(1f),
            )
            MetricCard(
                label = "Est. Profit",
                value = "+₡${displayEstProfit}",
                valueColor = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.weight(1f),
            )
          }
          Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            MetricCard(
                label = "Items",
                value = "${displayItems}",
                icon = Icons.Default.Inventory,
                modifier = Modifier.weight(1f),
            )
            MetricCard(
                label = "Daily Total",
                value = "₡${displayTotalCharged}",
                modifier = Modifier.weight(1f),
            )
          }
        }
      }

      // Sales Summary & Top Products Analytics Banner
      item {
        Surface(
            modifier = Modifier.fillMaxWidth().clickable { onNavigateToSummary() },
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.primaryContainer,
        ) {
          Row(
              modifier = Modifier.padding(16.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween,
          ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
              Icon(
                  imageVector = Icons.Default.Analytics,
                  contentDescription = null,
                  tint = MaterialTheme.colorScheme.onPrimaryContainer,
                  modifier = Modifier.size(24.dp),
              )
              Column {
                Text(
                    "Sales Analytics & Top Products",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
                Text(
                    "Tap to filter by day & view most sold items",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f),
                )
              }
            }
          }
        }
      }

      // Section Header
      item {
        Text(
            text = "Today's Sales Breakdown",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
        )
      }

      // Detail List
      val recentSaleItems = if (saleUiState.todaySales.isNotEmpty()) {
        saleUiState.todaySales.flatMap { it.items }
      } else if (lastSale != null) {
        lastSale.items
      } else {
        emptyList()
      }

      if (recentSaleItems.isNotEmpty()) {
        items(recentSaleItems) { item -> OrderItemDetailRow(item) }
      } else {
        item {
          Box(
              modifier = Modifier.fillMaxWidth().padding(24.dp),
              contentAlignment = Alignment.Center,
          ) {
            Text(
                text =
                    "No sales registered today. Scan products on the Scanner screen to make a purchase.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline,
            )
          }
        }
      }

      // Status Banner
      if (lastSale != null) {
        item {
          Surface(
              modifier = Modifier.fillMaxWidth(),
              shape = RoundedCornerShape(12.dp),
              color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
          ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
              Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(8.dp),
              ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.size(16.dp),
                )
                Text(
                    "Stock updated automatically",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.outline,
                )
              }
              Text(
                  lastSale.timestamp,
                  style = MaterialTheme.typography.bodySmall,
                  color = MaterialTheme.colorScheme.outline,
              )
            }
          }
        }
      }
    }
  }
}

@Composable
fun OrderItemDetailRow(item: OrderItem) {
  Card(
      modifier = Modifier.fillMaxWidth(),
      shape = MaterialTheme.shapes.medium,
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
  ) {
    Row(
        modifier = Modifier.padding(16.dp).fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
      Row(
          horizontalArrangement = Arrangement.spacedBy(12.dp),
          verticalAlignment = Alignment.CenterVertically,
      ) {
        Text(item.product.emoji ?: "📦", fontSize = 24.sp)
        Column {
          Text(
              text = item.product.name,
              style = MaterialTheme.typography.bodyLarge,
              fontWeight = FontWeight.Bold,
          )
          Text(
              text = "Qty: ${item.quantity} · ₡${item.product.price} unit",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.outline,
          )
        }
      }
      Text(
          text = "₡${item.subtotal}",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
      )
    }
  }
}
