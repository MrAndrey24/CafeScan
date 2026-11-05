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
import io.github.mrandrey24.cafescan.domain.usecases.sale.GetSalesSummaryUseCase
import io.github.mrandrey24.cafescan.presentation.state.DashboardUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SaleViewModel(
    private val getSalesSummaryUseCase: GetSalesSummaryUseCase,
) : ViewModel() {
  private val _uiState = MutableStateFlow(DashboardUiState())
  val uiState = _uiState.asStateFlow()

  init {
    loadSalesSummary(7)
  }

  fun onFilterChanged(days: Int) {
    _uiState.update { it.copy(selectedFilterDays = days) }
    loadSalesSummary(days)
  }

  fun loadSalesSummary(days: Int = _uiState.value.selectedFilterDays) {
    viewModelScope.launch {
      _uiState.update { it.copy(isLoading = true, errorMessage = null) }
      try {
        val summary = getSalesSummaryUseCase(days)
        _uiState.update {
          it.copy(
              selectedFilterDays = days,
              todaySales = summary.sales,
              topProducts = summary.topProducts,
              totalChargedToday = summary.totalRevenue,
              totalProfitToday = summary.totalProfit,
              totalItemsToday = summary.totalItemsSold,
              isLoading = false,
          )
        }
      } catch (e: Exception) {
        _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
      }
    }
  }

  fun loadTodaySales() {
    loadSalesSummary(_uiState.value.selectedFilterDays)
  }
}
