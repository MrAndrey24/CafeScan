/**
 * Copyright (c) 2026 Andrey Acosta Ramirez
 * All Rights Reserved.
 *
 * This software is confidential and proprietary. Unauthorized copying,
 * distribution, or modification of this file is strictly prohibited.
 *
 */
package io.github.mrandrey24.cafescan.presentation.state

import io.github.mrandrey24.cafescan.domain.entities.Sale
import io.github.mrandrey24.cafescan.domain.entities.TopProduct

data class DashboardUiState(
    val selectedFilterDays: Int = 7,
    val todaySales: List<Sale> = emptyList(),
    val topProducts: List<TopProduct> = emptyList(),
    val totalChargedToday: Double = 0.0,
    val totalProfitToday: Double = 0.0,
    val totalItemsToday: Int = 0,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
