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
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

class GetTodaySalesUseCase(private val saleRepository: ISaleRepository) {
  suspend operator fun invoke(): List<Sale> {
    val timeZone = TimeZone.currentSystemDefault()
    val today = Clock.System.now().toLocalDateTime(timeZone).date
    val startMs = today.atStartOfDayIn(timeZone).toEpochMilliseconds()
    val endMs = today.plus(1, DateTimeUnit.DAY).atStartOfDayIn(timeZone).toEpochMilliseconds()
    return saleRepository.getSalesForDateRange(startMs, endMs)
  }
}
