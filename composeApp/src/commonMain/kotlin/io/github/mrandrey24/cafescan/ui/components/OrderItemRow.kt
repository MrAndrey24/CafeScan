package io.github.mrandrey24.cafescan.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.mrandrey24.cafescan.domain.entities.OrderItem

@Composable
fun OrderItemRow(
    item: OrderItem,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    modifier: Modifier = Modifier,
) {
  Card(
      modifier = modifier.fillMaxWidth(),
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
  ) {
    Row(
        modifier = Modifier.padding(16.dp).fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
      // Icon Placeholder
      Box(
          modifier =
              Modifier.size(56.dp)
                  .background(
                      MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                      RoundedCornerShape(12.dp),
                  ),
          contentAlignment = Alignment.Center,
      ) {
          item.product.emoji?.let { Text(it, fontSize = 24.sp) }
      }

      // Product Info
      Column(modifier = Modifier.weight(1f)) {
        Text(
            text = item.product.name,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            text = "$${item.product.price}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.outline,
        )
      }

      // Quantity Selector
      Surface(
          modifier = Modifier.height(40.dp),
          shape = RoundedCornerShape(12.dp),
          color = MaterialTheme.colorScheme.surfaceVariant,
      ) {
        Row(
            modifier = Modifier.padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
          IconButton(onClick = onDecrease, modifier = Modifier.size(32.dp)) {
            Icon(
                Icons.Default.Remove,
                contentDescription = "Decrease",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(16.dp),
            )
          }
          Text(
              text = item.quantity.toString(),
              style = MaterialTheme.typography.bodyLarge,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface,
          )
          IconButton(onClick = onIncrease, modifier = Modifier.size(32.dp)) {
            Icon(
                Icons.Default.Add,
                contentDescription = "Increase",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(16.dp),
            )
          }
        }
      }
    }
  }
}
