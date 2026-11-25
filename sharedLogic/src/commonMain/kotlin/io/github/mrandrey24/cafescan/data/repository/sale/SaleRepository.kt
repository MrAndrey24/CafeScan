/**
 * MIT License
 *
 * Copyright (c) 2026 Andrey Acosta  Ramirez
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.github.mrandrey24.cafescan.data.repository.sale

import io.github.mrandrey24.cafescan.data.local.sale.ISaleLocalDataSource
import io.github.mrandrey24.cafescan.data.remote.sale.ISaleRemoteDataSource
import io.github.mrandrey24.cafescan.domain.entities.Sale
import io.github.mrandrey24.cafescan.domain.entities.TopProduct
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SaleRepository(
    private val localSaleDataSource: ISaleLocalDataSource,
    private val remoteSaleDataSource: ISaleRemoteDataSource,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
) : ISaleRepository {

  override suspend fun createSale(sale: Sale): Sale {
    val savedLocalSale = localSaleDataSource.saveSale(sale)
    runCatching { remoteSaleDataSource.saveSaleRemote(savedLocalSale) }
        .onFailure { exception ->
          println("Failed saving sale to remote: $exception")
        }
    return savedLocalSale
  }

  override fun getSalesForDateRangeFlow(startMs: Long, endMs: Long): Flow<List<Sale>> = flow {
    val cachedSales = localSaleDataSource.getSalesForDateRange(startMs, endMs)
    if (cachedSales.isNotEmpty()) {
      emit(cachedSales)
    }

    try {
      remoteSaleDataSource
          .getSalesForDateRangeRemote(startMs, endMs)
          .catch { exception ->
            println("Failed fetching sales from network. Using local cache: $exception")
          }
          .collect { remoteSales ->
            remoteSales.forEach { sale -> localSaleDataSource.saveSale(sale) }
            emit(localSaleDataSource.getSalesForDateRange(startMs, endMs))
          }
    } catch (exception: Exception) {
      println("Failed setting up remote sales listener: $exception")
    }
  }
      .flowOn(defaultDispatcher)

  override suspend fun getTopProducts(startMs: Long, endMs: Long): List<TopProduct> {
    return localSaleDataSource.getTopProducts(startMs, endMs)
  }
}
