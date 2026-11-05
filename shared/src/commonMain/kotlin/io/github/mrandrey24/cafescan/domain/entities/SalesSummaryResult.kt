package io.github.mrandrey24.cafescan.domain.entities

data class SalesSummaryResult(
    val filterDays: Int,
    val sales: List<Sale>,
    val topProducts: List<TopProduct>,
    val totalRevenue: Double,
    val totalProfit: Double,
    val salesCount: Int,
    val totalItemsSold: Int,
)
