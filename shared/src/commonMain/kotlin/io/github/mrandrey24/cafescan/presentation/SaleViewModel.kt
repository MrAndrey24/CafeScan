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
import io.github.mrandrey24.cafescan.domain.usecases.sale.GetTodaySalesUseCase
import io.github.mrandrey24.cafescan.presentation.state.DashboardUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SaleViewModel(private val getTodaySalesUseCase: GetTodaySalesUseCase) : ViewModel() {
  private val _uiState = MutableStateFlow(DashboardUiState())
  val uiState = _uiState.asStateFlow()

  init {
    loadTodaySales()
  }

  fun loadTodaySales() {
    viewModelScope.launch {
      _uiState.update { it.copy(isLoading = true, errorMessage = null) }
      try {
        val todaySales = getTodaySalesUseCase()
        val totalCharged = todaySales.sumOf { it.total }
        val totalProfit = todaySales.sumOf { it.profit }
        val totalItems = todaySales.sumOf { it.itemCount }

        _uiState.update {
          it.copy(
              todaySales = todaySales,
              totalChargedToday = totalCharged,
              totalProfitToday = totalProfit,
              totalItemsToday = totalItems,
              isLoading = false,
          )
        }
      } catch (e: Exception) {
        _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
      }
    }
  }
}
