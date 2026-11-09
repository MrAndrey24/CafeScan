/**
 * Copyright (c) 2026 Andrey Acosta Ramirez
 * All Rights Reserved.
 *
 * This software is confidential and proprietary. Unauthorized copying,
 * distribution, or modification of this file is strictly prohibited.
 *
 */
package io.github.mrandrey24.cafescan.data.repository.sale

import io.github.mrandrey24.cafescan.data.local.sale.ISaleLocalDataSource
import io.github.mrandrey24.cafescan.domain.entities.Sale
import io.github.mrandrey24.cafescan.domain.entities.TopProduct
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class SaleRepository(
    private val saleLocalDataSource: ISaleLocalDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ISaleRepository {

  override suspend fun saveSale(sale: Sale): Sale =
      withContext(ioDispatcher) { saleLocalDataSource.saveSale(sale) }

  override suspend fun getSalesForDateRange(startMs: Long, endMs: Long): List<Sale> =
      withContext(ioDispatcher) { saleLocalDataSource.getSalesForDateRange(startMs, endMs) }

  override suspend fun getTopProducts(startMs: Long, endMs: Long): List<TopProduct> =
      withContext(ioDispatcher) { saleLocalDataSource.getTopProducts(startMs, endMs) }
}
