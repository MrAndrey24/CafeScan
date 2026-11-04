/**
 * Copyright (c) 2026 Andrey Acosta Ramirez
 * All Rights Reserved.
 *
 * This software is confidential and proprietary. Unauthorized copying,
 * distribution, or modification of this file is strictly prohibited.
 *
 */
package io.github.mrandrey24.cafescan.data.local.sale

import io.github.mrandrey24.cafescan.domain.entities.Sale

interface ISaleLocalDataSource {
  suspend fun saveSale(sale: Sale): Sale

  suspend fun getSalesForDateRange(startMs: Long, endMs: Long): List<Sale>
}
