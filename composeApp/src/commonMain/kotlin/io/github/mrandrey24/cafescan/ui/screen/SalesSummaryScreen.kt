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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.mrandrey24.cafescan.domain.entities.TopProduct
import io.github.mrandrey24.cafescan.presentation.SaleViewModel
import io.github.mrandrey24.cafescan.ui.components.MetricCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalesSummaryScreen(
    onBack: () -> Unit,
    viewModel: SaleViewModel,
) {
  val uiState by viewModel.uiState.collectAsState()
  val filterOptions = listOf(1 to "Today", 7 to "7 Days", 30 to "30 Days", 365 to "All Time")

  Scaffold(
      topBar = {
        CenterAlignedTopAppBar(
            title = {
              Text(
                  "Daily Sales Summary",
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
      // Date Filter Chips
      item {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 4.dp),
        ) {
          items(filterOptions) { (days, label) ->
            val isSelected = uiState.selectedFilterDays == days
            Surface(
                shape = RoundedCornerShape(20.dp),
                color =
                    if (isSelected) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.clickable { viewModel.onFilterChanged(days) },
            ) {
              Text(
                  text = label,
                  style = MaterialTheme.typography.labelMedium,
                  fontWeight = FontWeight.Bold,
                  color =
                      if (isSelected) MaterialTheme.colorScheme.onPrimary
                      else MaterialTheme.colorScheme.onSurfaceVariant,
                  modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
              )
            }
          }
        }
      }

      // Metrics Summary
      item {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
          Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            MetricCard(
                label = "Total Revenue",
                value = "₡${uiState.totalChargedToday}",
                modifier = Modifier.weight(1f),
            )
            MetricCard(
                label = "Total Profit",
                value = "+₡${uiState.totalProfitToday}",
                valueColor = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.weight(1f),
            )
          }
          Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            MetricCard(
                label = "Sales Count",
                value = "${uiState.todaySales.size}",
                icon = Icons.AutoMirrored.Filled.TrendingUp,
                modifier = Modifier.weight(1f),
            )
            MetricCard(
                label = "Items Sold",
                value = "${uiState.totalItemsToday}",
                icon = Icons.Default.Inventory,
                modifier = Modifier.weight(1f),
            )
          }
        }
      }

      // Top Products Header
      item {
        Text(
            text = "Most Sold Products",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
        )
      }

      // Top Products List
      if (uiState.topProducts.isEmpty()) {
        item {
          Box(
              modifier = Modifier.fillMaxWidth().padding(24.dp),
              contentAlignment = Alignment.Center,
          ) {
            Text(
                "No product sales registered for this period.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline,
            )
          }
        }
      } else {
        itemsIndexed(uiState.topProducts) { index, topProduct ->
          TopProductRowItem(rank = index + 1, product = topProduct)
        }
      }
    }
  }
}

@Composable
fun TopProductRowItem(rank: Int, product: TopProduct) {
  val badgeText =
      when (rank) {
        1 -> "🥇"
        2 -> "🥈"
        3 -> "🥉"
        else -> "#$rank"
      }

  Card(
      modifier = Modifier.fillMaxWidth(),
      shape = MaterialTheme.shapes.medium,
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
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
        Box(
            modifier =
                Modifier.size(44.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
          Text(badgeText, fontSize = 20.sp)
        }

        Text(product.emoji, fontSize = 24.sp)

        Column {
          Text(
              text = product.name,
              style = MaterialTheme.typography.bodyLarge,
              fontWeight = FontWeight.Bold,
          )
          Text(
              text = "${product.totalSold} sold · ₡${product.price} unit",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.outline,
          )
        }
      }

      Text(
          text = "₡${product.totalRevenue}",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.primary,
      )
    }
  }
}
