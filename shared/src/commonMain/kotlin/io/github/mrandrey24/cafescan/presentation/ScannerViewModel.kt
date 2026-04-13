package io.github.mrandrey24.cafescan.presentation

import androidx.lifecycle.ViewModel
import io.github.mrandrey24.cafescan.domain.entities.OrderItem
import io.github.mrandrey24.cafescan.domain.entities.Product
import io.github.mrandrey24.cafescan.presentation.state.ScannerUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ScannerViewModel : ViewModel() {
  private val _uiState = MutableStateFlow(ScannerUiState())
  val uiState = _uiState.asStateFlow()

  init {
    // Mock data to match the PNG
    val mockItems =
        listOf(
            OrderItem(
                product =
                    Product(
                        id = 1,
                        name = "Oat Milk Latte",
                        price = 4.50,
                        cost = 2.0,
                        emoji = "☕",
                    ),
                quantity = 1,
            ),
            OrderItem(
                product =
                    Product(
                        id = 2,
                        name = "Almond Croissant",
                        price = 3.75,
                        cost = 1.5,
                        emoji = "🥐",
                    ),
                quantity = 2,
            ),
            OrderItem(
                product =
                    Product(
                        id = 3,
                        name = "Blueberry Muffin",
                        price = 2.95,
                        cost = 1.0,
                        emoji = "🧁",
                    ),
                quantity = 1,
            ),
        )
    _uiState.update { it.copy(basket = mockItems) }
  }

  fun onQrScanned(payload: String) {
    // Basic implementation: find product or add new one
    // For now, let's just log or add a dummy item if not already there
  }

  fun updateQuantity(productId: Long, delta: Int) {
    _uiState.update { state ->
      val newBasket =
          state.basket
              .map { item ->
                if (item.product.id == productId) {
                  val newQty = (item.quantity + delta).coerceAtLeast(0)
                  item.copy(quantity = newQty)
                } else {
                  item
                }
              }
              .filter { it.quantity > 0 }
      state.copy(basket = newBasket)
    }
  }

  fun clearBasket() {
    _uiState.update { it.copy(basket = emptyList()) }
  }
}
