/**
 * Copyright (c) 2026 Andrey Acosta Ramirez
 * All Rights Reserved.
 *
 * This software is confidential and proprietary. Unauthorized copying,
 * distribution, or modification of this file is strictly prohibited.
 *
 */
package io.github.mrandrey24.cafescan.domain.entities

import kotlinx.datetime.LocalDate

data class DailySummary(val date: LocalDate, val sales: List<Sale>) {
  val totalRevenue: Double
    get() = sales.sumOf { it.total }

  val totalProfit: Double
    get() = sales.sumOf { it.profit }

  val salesCount: Int
    get() = sales.size

  val topProduct: Product?
    get() =
        sales
            .flatMap { sale -> sale.items.map { it.product to it.quantity } }
            .groupBy({ it.first }, { it.second })
            .mapValues { it.value.sum() }
            .maxByOrNull { it.value }
            ?.key
}
