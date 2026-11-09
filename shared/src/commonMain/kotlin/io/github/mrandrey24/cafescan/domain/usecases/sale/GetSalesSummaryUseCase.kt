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
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

class GetSalesSummaryUseCase(private val saleRepository: ISaleRepository) {
  suspend operator fun invoke(filterDays: Int): SalesSummaryResult {
    val timeZone = TimeZone.currentSystemDefault()
    val today = Clock.System.now().toLocalDateTime(timeZone).date
    val endMs = today.plus(1, DateTimeUnit.DAY).atStartOfDayIn(timeZone).toEpochMilliseconds()

    val startMs =
        when {
          filterDays >= 365 -> 0L
          filterDays <= 1 -> today.atStartOfDayIn(timeZone).toEpochMilliseconds()
          else ->
              today
                  .minus(filterDays.toLong() - 1, DateTimeUnit.DAY)
                  .atStartOfDayIn(timeZone)
                  .toEpochMilliseconds()
        }

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
