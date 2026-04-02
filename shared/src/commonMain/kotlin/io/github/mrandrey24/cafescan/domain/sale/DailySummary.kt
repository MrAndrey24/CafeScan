package io.github.mrandrey24.cafescan.domain.sale

import io.github.mrandrey24.cafescan.domain.product.Product
import kotlinx.datetime.LocalDate

data class DailySummary(
    val date: LocalDate,
    val sales: List<Sale>,
    val totalRevenue: Double = sales.sumOf { it.total },
    val totalProfit: Double = sales.sumOf { it.profit },
    val salesCount: Int = sales.size,
    val topProduct: Product? = null,
)
