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

class SaleRepository(private val saleLocalDataSource: ISaleLocalDataSource) : ISaleRepository {

  override suspend fun saveSale(sale: Sale): Sale {
    return saleLocalDataSource.saveSale(sale)
  }

  override suspend fun getSalesForDateRange(startMs: Long, endMs: Long): List<Sale> {
    return saleLocalDataSource.getSalesForDateRange(startMs, endMs)
  }

  override suspend fun getTopProducts(startMs: Long, endMs: Long): List<TopProduct> {
    return saleLocalDataSource.getTopProducts(startMs, endMs)
  }
}
