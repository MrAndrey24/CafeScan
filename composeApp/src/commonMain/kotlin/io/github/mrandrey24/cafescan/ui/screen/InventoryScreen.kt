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
import io.github.mrandrey24.cafescan.ui.components.StockBarItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryScreen(
    onBack: () -> Unit,
    onNavigateToScanner: () -> Unit,
    onNavigateToDashboard: () -> Unit,
    onNavigateToCatalog: () -> Unit,
) {
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
              icon = { Icon(Icons.Default.LocalOffer, contentDescription = "Catálogo") },
              label = { Text("Catálogo") },
          )
        }
      },
      containerColor = MaterialTheme.colorScheme.background,
  ) { padding ->
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 24.dp, top = 8.dp),
    ) {
      // Date Header
      item {
        Column {
          Text(
              "Thursday, May 24",
              style = MaterialTheme.typography.headlineSmall,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface,
          )
          Text(
              "Last update 9:30 AM",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.outline,
          )
        }
      }

      // Category: COFFEE & BEANS
      item { CategoryHeader(title = "COFFEE & BEANS", count = "4 items") }

      item {
        StockBarItem(
            name = "House Espresso Blend",
            subtitle = "2.5kg bags",
            current = 3,
            total = 20,
            statusLabel = "Low Stock",
            statusColor = Color(0xFFBA1A1A),
            badgeLabel = "CRITICAL",
            emoji = "☕",
        )
      }

      item {
        StockBarItem(
            name = "Whole Milk",
            subtitle = "1L Cartons",
            current = 42,
            total = 48,
            statusLabel = "Healthy",
            statusColor = Color(0xFF4CAF50),
            emoji = "🥛",
        )
      }

      item {
        StockBarItem(
            name = "Butter Croissants",
            subtitle = "Daily Fresh",
            current = 6,
            total = 24,
            statusLabel = "Running Out",
            statusColor = Color(0xFFD4A373),
            badgeLabel = "LOW",
            badgeColor = Color(0xFFD4A373),
            emoji = "🥐",
        )
      }

      // Category: PACKAGING
      item { CategoryHeader(title = "PACKAGING", count = "2 items") }

      // Alert Banner
      item {
        Surface(
            modifier = Modifier.fillMaxWidth().clickable { /* Adjust stock */ },
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
                text = "2 products with low stock - tap to adjust",
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
  }
}

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
