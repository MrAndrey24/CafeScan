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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.mrandrey24.cafescan.model.SaleItem
import io.github.mrandrey24.cafescan.ui.components.MetricCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onBack: () -> Unit,
    onNavigateToScanner: () -> Unit,
    onNavigateToInventory: () -> Unit,
    onNavigateToCatalog: () -> Unit,
) {
  // Hardcoded data for the Dashboard PNG
  val hardcodedSaleItems =
      listOf(
          SaleItem(name = "Latte", emoji = "☕", qty = 2, unitPrice = 4.50),
          SaleItem(name = "Croissant", emoji = "🥐", qty = 1, unitPrice = 3.50),
          SaleItem(name = "Avocado Toast", emoji = "🥑", qty = 1, unitPrice = 12.00),
      )

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
            colors =
                TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent),
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
              icon = { Icon(Icons.Default.LocalOffer, contentDescription = "Catálogo") },
              label = { Text("Catálogo") },
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
            MetricCard(label = "Total Charged", value = "$24.50", modifier = Modifier.weight(1f))
            MetricCard(
                label = "Est. Profit",
                value = "+$12.30",
                valueColor = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.weight(1f),
            )
          }
          Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            MetricCard(
                label = "Items",
                value = "4",
                icon = Icons.Default.Inventory,
                modifier = Modifier.weight(1f),
            )
            MetricCard(label = "Daily Total", value = "$842.00", modifier = Modifier.weight(1f))
          }
        }
      }

      // Section Header
      item {
        Text(
            text = "This sale's detail",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
        )
      }

      // Detail List
      items(hardcodedSaleItems) { item -> SaleDetailItemRow(item) }

      // Status Banner
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
                "9:42 AM",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline,
            )
          }
        }
      }
    }
  }
}

@Composable
fun SaleDetailItemRow(item: SaleItem) {
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
        Text(item.emoji, fontSize = 24.sp)
        Column {
          Text(
              text = item.name,
              style = MaterialTheme.typography.bodyLarge,
              fontWeight = FontWeight.Bold,
          )
          Text(
              text = "Qty: ${item.qty} - $${item.unitPrice} unit",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.outline,
          )
        }
      }
      Text(
          text = "$${item.qty * item.unitPrice}",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
      )
    }
  }
}
