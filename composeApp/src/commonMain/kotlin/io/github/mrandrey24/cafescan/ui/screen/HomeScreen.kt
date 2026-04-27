package io.github.mrandrey24.cafescan.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.mrandrey24.cafescan.domain.entities.OrderItem
import io.github.mrandrey24.cafescan.domain.entities.Product
import io.github.mrandrey24.cafescan.presentation.ScannerViewModel
import io.github.mrandrey24.cafescan.presentation.state.ScanError
import io.github.mrandrey24.cafescan.ui.components.OrderItemRow
import io.github.mrandrey24.cafescan.ui.components.QrPreviewCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToDashboard: () -> Unit,
    onNavigateToInventory: () -> Unit,
    onNavigateToCatalog: () -> Unit,
//    onNavigateToScanner: () -> Unit,
    viewModel: ScannerViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.scanError) {
        when (uiState.scanError) {
            is ScanError.InvalidFormat   ->
                snackbarHostState.showSnackbar("QR inválido — no es un producto de CaféScan")
            is ScanError.ProductNotFound ->
                snackbarHostState.showSnackbar("Producto no encontrado en el catálogo")
            null -> Unit
        }
        viewModel.clearScanError()
    }

  Scaffold(
      snackbarHost = { SnackbarHost(snackbarHostState) },
      topBar = {
        TopAppBar(
            title = {
              Text(
                  "CaféScan",
                  style = MaterialTheme.typography.headlineSmall,
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onSurface,
              )
            },
            actions = {
              // Items Badge
              Surface(
                  color = MaterialTheme.colorScheme.surfaceVariant,
                  shape = RoundedCornerShape(12.dp),
                  modifier = Modifier.padding(end = 16.dp),
              ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                  Box(
                      modifier =
                          Modifier.size(8.dp)
                              .background(MaterialTheme.colorScheme.primary, CircleShape)
                  )
                  Text(
                      "${uiState.totalItems} items",
                      style = MaterialTheme.typography.labelMedium,
                      fontWeight = FontWeight.SemiBold,
                      color = MaterialTheme.colorScheme.onSurfaceVariant,
                  )
                }
              }
            },
            colors =
                TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
        )
      },
      bottomBar = {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            tonalElevation = 8.dp,
        ) {
          NavigationBarItem(
              selected = true,
              onClick = { /* Already here */ },
              icon = { Icon(Icons.Default.QrCodeScanner, contentDescription = "Scanner") },
              label = { Text("Scanner") },
              colors =
                  NavigationBarItemDefaults.colors(
                      selectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                      selectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                      indicatorColor = MaterialTheme.colorScheme.surfaceVariant,
                  ),
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
        contentPadding = PaddingValues(bottom = 24.dp),
    ) {
      // Scanner Section
      item {
        QrPreviewCard(
            onScan = { rawString -> viewModel.onQrScanned(rawString) },
            modifier = Modifier.padding(top = 8.dp))
      }

      // Basket Header
      item {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
          Text(
              "Scanned Basket",
              style = MaterialTheme.typography.titleLarge,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface,
          )
          TextButton(onClick = { viewModel.clearBasket() }) {
            Text("Clear all", color = MaterialTheme.colorScheme.primary)
          }
        }
      }

      // Basket Items
      items(
          uiState.basket,
          key = { it.product.id }
      ) { item ->
        OrderItemRow(
            item = item,
            onIncrease = { viewModel.onIncreaseQty(item.product.id.toString()) },
            onDecrease = { viewModel.onDecreaseQty(item.product.id.toString()) },
        )
      }

      // Total Section
      item {
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
        ) {
          Text(
              "Total Balance",
              style = MaterialTheme.typography.bodyLarge,
              color = MaterialTheme.colorScheme.outline,
              modifier = Modifier.padding(bottom = 4.dp),
          )
          Text(
              "$${uiState.totalAmount}",
              style = MaterialTheme.typography.headlineLarge,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface,
              fontSize = 36.sp,
          )
        }
      }
    }
  }
}
