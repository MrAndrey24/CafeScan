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
import io.github.mrandrey24.cafescan.domain.usecases.UpdateStockUseCase
import io.github.mrandrey24.cafescan.domain.usecases.inventory.GetAllActiveWithStockUseCase
import io.github.mrandrey24.cafescan.domain.usecases.product.DeleteProductUseCase
import io.github.mrandrey24.cafescan.presentation.state.InventoryUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InventoryViewModel(
    private val getAllActiveWithStockUseCase: GetAllActiveWithStockUseCase,
    private val updateStockUseCase: UpdateStockUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
) : ViewModel() {
  private val _uiState = MutableStateFlow(InventoryUiState())
  val uiState: StateFlow<InventoryUiState> = _uiState.asStateFlow()

  init {
    loadInventory()
  }

  fun loadInventory() {
    viewModelScope.launch {
      _uiState.update { it.copy(isLoading = true, errorMessage = null) }
      try {
        val items = getAllActiveWithStockUseCase()
        _uiState.update { it.copy(items = items, isLoading = false) }
      } catch (e: Exception) {
        _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
      }
    }
  }

  fun updateStockAndPrice(productId: Long, currentQty: Int, price: Double) {
    viewModelScope.launch {
      try {
        updateStockUseCase(productId, currentQty, price)
        loadInventory()
      } catch (e: Exception) {
        _uiState.update { it.copy(errorMessage = e.message) }
      }
    }
  }

  fun deleteProduct(productId: Long) {
    viewModelScope.launch {
      try {
        deleteProductUseCase(productId)
        loadInventory()
      } catch (e: Exception) {
        _uiState.update { it.copy(errorMessage = e.message) }
      }
    }
  }
}
