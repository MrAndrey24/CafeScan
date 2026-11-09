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
package io.github.mrandrey24.cafescan.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.mrandrey24.cafescan.domain.entities.Product
import io.github.mrandrey24.cafescan.domain.usecases.product.CheckProductNameExistsUseCase
import io.github.mrandrey24.cafescan.domain.usecases.product.SaveProductUseCase
import io.github.mrandrey24.cafescan.presentation.state.ProductUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductViewModel(
    private val saveProductUseCase: SaveProductUseCase,
    private val checkProductNameExistsUseCase: CheckProductNameExistsUseCase,
) : ViewModel() {
  private val _uiState = MutableStateFlow(ProductUiState())
  val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()
  private var nameCheckJob: Job? = null

  fun saveProduct() {
    val state = _uiState.value
    viewModelScope.launch {
      _uiState.update { it.copy(isSaving = true, errorMessage = null) }
      try {
        val product =
            Product(
                name = state.name,
                price = state.priceDouble,
                cost = state.costDouble,
                emoji = state.emoji,
                stock = state.stockInt,
            )
        val savedProduct = saveProductUseCase(product)
        _uiState.update {
          it.copy(isSaving = false, isSaved = true, savedProductId = savedProduct.id)
        }
      } catch (e: Exception) {
        _uiState.update { it.copy(isSaving = false, errorMessage = e.message) }
      }
    }
  }

  fun onNameChanged(name: String) {
    _uiState.update { it.copy(name = name, isProductNameExists = false) }

    nameCheckJob?.cancel()
    if (name.isBlank()) return

    nameCheckJob =
        viewModelScope.launch {
          val exists = checkProductNameExistsUseCase(name.trim())
          _uiState.update { it.copy(isProductNameExists = exists) }
        }
  }

  fun onPriceChange(price: String) = _uiState.update { it.copy(price = price) }

  fun onCostChange(cost: String) = _uiState.update { it.copy(cost = cost) }

  fun onStockChange(stock: String) = _uiState.update { it.copy(stock = stock) }

  fun onEmojiChange(emoji: String) = _uiState.update { it.copy(emoji = emoji) }

  fun clearSaveState() =
      _uiState.update {
        it.copy(
            isSaved = false,
            savedProductId = null,
            name = "",
            price = "",
            cost = "",
            stock = "",
            emoji = "",
            isProductNameExists = false,
        )
      }

  fun clearError() {
    _uiState.update { it.copy(errorMessage = null) }
  }
}
