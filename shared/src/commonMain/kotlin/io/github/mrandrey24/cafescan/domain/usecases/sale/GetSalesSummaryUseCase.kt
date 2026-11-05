/**
 * Copyright (c) 2026 Andrey Acosta Ramirez
 * All Rights Reserved.
 *
 * This software is confidential and proprietary. Unauthorized copying,
 * distribution, or modification of this file is strictly prohibited.
 *
 */
package io.github.mrandrey24.cafescan.domain.usecases.sale

import io.github.mrandrey24.cafescan.data.repository.sale.ISaleRepository
import io.github.mrandrey24.cafescan.domain.entities.SalesSummaryResult
import kotlin.time.Clock


class GetSalesSummaryUseCase(private val saleRepository: ISaleRepository) {
  suspend operator fun invoke(filterDays: Int): SalesSummaryResult {
    val nowMs = Clock.System.now().toEpochMilliseconds()
    val msPerDay = 86400000L
    val startMs = if (filterDays >= 365) 0L else nowMs - (filterDays.toLong() * msPerDay)
    val endMs = nowMs + msPerDay

    val sales = saleRepository.getSalesForDateRange(startMs, endMs)
    val topProducts = saleRepository.getTopProducts(startMs, endMs)

    val totalRevenue = sales.sumOf { it.total }
    val totalProfit = sales.sumOf { it.profit }
    val salesCount = sales.size
    val totalItemsSold = sales.sumOf { it.itemCount }

    return SalesSummaryResult(
        filterDays = filterDays,
        sales = sales,
        topProducts = topProducts,
        totalRevenue = totalRevenue,
        totalProfit = totalProfit,
        salesCount = salesCount,
        totalItemsSold = totalItemsSold,
    )
  }
}
