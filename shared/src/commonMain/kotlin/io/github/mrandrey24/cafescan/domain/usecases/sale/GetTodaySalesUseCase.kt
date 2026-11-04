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
import io.github.mrandrey24.cafescan.domain.entities.Sale
import kotlin.time.Clock

class GetTodaySalesUseCase(private val saleRepository: ISaleRepository) {
  suspend operator fun invoke(): List<Sale> {
    val nowMs = Clock.System.now().toEpochMilliseconds()
    val msPerDay = 86400000L
    val startMs = nowMs - (nowMs % msPerDay)
    val endMs = startMs + msPerDay
    return saleRepository.getSalesForDateRange(startMs, endMs)
  }
}
