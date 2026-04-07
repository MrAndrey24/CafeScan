package io.github.mrandrey24.cafescan.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrPreviewScreen(
    onBack: () -> Unit,
    onNavigateToScanner: () -> Unit,
    onNavigateToDashboard: () -> Unit,
    onNavigateToInventory: () -> Unit,
) {
  val hardcodedJson = """{"id":"CAP-001","name":"Cappuccino","price":1800,"cost":900}"""

  Scaffold(
      topBar = {
        TopAppBar(
            title = {
              Column(
                  horizontalAlignment = Alignment.CenterHorizontally,
                  modifier = Modifier.fillMaxWidth(),
              ) {
                Text(
                    "QR generated",
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
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      // QR Code Main Card
      Card(
          modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
          shape = MaterialTheme.shapes.large,
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
      ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
          // QR Placeholder
          Box(
              modifier =
                  Modifier.size(200.dp)
                      .background(
                          MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                          RoundedCornerShape(12.dp),
                      )
                      .border(
                          1.dp,
                          MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                          RoundedCornerShape(12.dp),
                      ),
              contentAlignment = Alignment.Center,
          ) {
            Icon(
                imageVector = Icons.Default.QrCodeScanner,
                contentDescription = null,
                modifier = Modifier.size(100.dp),
                tint = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
            )
          }

          // Product Details
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
              Text("☕", fontSize = 20.sp)
              Text(
                  "Cappuccino",
                  style = MaterialTheme.typography.titleMedium,
                  fontWeight = FontWeight.Bold,
              )
            }
            Text(
                "₡1,800 · profit ₡900",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline,
            )
          }
        }
      }

      // QR Content Section
      Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            "QR CONTENT",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.outline,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
        )
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        ) {
          Text(
              text = hardcodedJson,
              modifier = Modifier.padding(16.dp),
              style = MaterialTheme.typography.bodySmall,
              fontFamily = FontFamily.Monospace,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
          )
        }
      }

      // Stats Row
      Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        StatSmallCard(label = "Price", value = "₡1,800", modifier = Modifier.weight(1f))
        StatSmallCard(
            label = "Profit",
            value = "50%",
            valueColor = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.weight(1f),
        )
      }

      // Action Buttons
      Column(
          verticalArrangement = Arrangement.spacedBy(12.dp),
          modifier = Modifier.padding(top = 8.dp),
      ) {
        Button(
            onClick = { /* Print */ },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = MaterialTheme.shapes.large,
            colors =
                ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary,
                ),
        ) {
          Text("Print QR", fontWeight = FontWeight.Bold)
        }

        OutlinedButton(
            onClick = { /* Share */ },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = MaterialTheme.shapes.large,
            colors =
                ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
            border =
                androidx.compose.foundation.BorderStroke(
                    1.dp,
                    MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                ),
        ) {
          Text("Share image", fontWeight = FontWeight.Bold)
        }
      }

      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}

@Composable
fun StatSmallCard(
    label: String,
    value: String,
    valueColor: Color = Color.Black,
    modifier: Modifier = Modifier,
) {
  Card(
      modifier = modifier.height(80.dp),
      shape = MaterialTheme.shapes.large,
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
  ) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
      Text(
          label,
          style = MaterialTheme.typography.labelSmall,
          color = MaterialTheme.colorScheme.outline,
      )
      Text(
          value,
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
          color = valueColor,
      )
    }
  }
}
