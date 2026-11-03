/**
 * Copyright (c) 2026 Andrey Acosta Ramirez
 * All Rights Reserved.
 *
 * This software is confidential and proprietary. Unauthorized copying,
 * distribution, or modification of this file is strictly prohibited.
 *
 */
package io.github.mrandrey24.cafescan.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.mrandrey24.cafescan.data.repository.product.IProductRepository
import io.github.mrandrey24.cafescan.domain.entities.OrderItem
import io.github.mrandrey24.cafescan.domain.entities.QrPayload
import io.github.mrandrey24.cafescan.presentation.state.ScanError
import io.github.mrandrey24.cafescan.presentation.state.ScannerUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Clock

class ScannerViewModel(private val productRepository: IProductRepository) : ViewModel() {
  private val _uiState = MutableStateFlow(ScannerUiState())
  val uiState = _uiState.asStateFlow()

  private var lastScannedString: String? = null
  private var lastScannedTime: Long = 0L

  fun onQrScanned(rawString: String) {
    val now = Clock.System.now().toEpochMilliseconds()
    if (rawString == lastScannedString && (now - lastScannedTime) < 1500L) {
      return
    }
    lastScannedString = rawString
    lastScannedTime = now

    viewModelScope.launch {
      try {
        val payload = QrPayload.fromQrString(rawString)

        if (payload == null) {
          _uiState.update { it.copy(scanError = ScanError.InvalidFormat) }
          return@launch
        }

        val product = productRepository.retrieveProductById(payload.id.toLong())

        if (product == null) {
          _uiState.update { it.copy(scanError = ScanError.ProductNotFound) }
          return@launch
        }

        _uiState.update { state ->
          val existing = state.basket.find { it.product.id == product.id }
          val updatedOrder =
              if (existing != null) {
                state.basket.map {
                  if (it.product.id == product.id) it.copy(quantity = it.quantity + 1) else it
                }
              } else {
                state.basket + OrderItem(product = product, quantity = 1)
              }
          state.copy(basket = updatedOrder, scannedProduct = product, scanError = null)
        }
      } catch (e: Exception) {
        _uiState.update { it.copy(errorMessage = e.message) }
      }
    }
  }

  fun onIncreaseQty(productId: String) {
    _uiState.update { state ->
      state.copy(
          basket =
              state.basket.map {
                if (it.product.id == productId.toLong()) it.copy(quantity = it.quantity + 1) else it
              }
      )
    }
  }

  fun onDecreaseQty(productId: String) {
    _uiState.update { state ->
      val updated =
          state.basket.mapNotNull {
            when {
              it.product.id != productId.toLong() -> it
              it.quantity > 1 -> it.copy(quantity = it.quantity - 1)
              else -> null
            }
          }
      state.copy(basket = updated)
    }
  }

  fun checkout() {
    viewModelScope.launch {
      try {
        val currentBasket = _uiState.value.basket
        if (currentBasket.isEmpty()) return@launch

        currentBasket.forEach { item ->
          productRepository.decrementStock(item.product.id, item.quantity.toLong())
        }

        clearBasket()
      } catch (e: Exception) {
        _uiState.update { it.copy(errorMessage = e.message) }
      }
    }
  }

  fun clearBasket() {
    _uiState.update { it.copy(basket = emptyList()) }
  }

  fun clearScanError() {
    _uiState.update { it.copy(scanError = null) }
  }

  fun clearOrder() {
    _uiState.update { ScannerUiState() }
  }
}
