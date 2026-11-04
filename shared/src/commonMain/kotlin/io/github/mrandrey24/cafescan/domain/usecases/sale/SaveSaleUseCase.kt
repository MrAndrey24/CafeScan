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
import io.github.mrandrey24.cafescan.domain.entities.OrderItem
import io.github.mrandrey24.cafescan.domain.entities.Sale
import io.github.mrandrey24.cafescan.domain.usecases.scanner.DecrementStockUseCase
import kotlin.time.Clock

class SaveSaleUseCase(
    private val saleRepository: ISaleRepository,
    private val decrementStockUseCase: DecrementStockUseCase,
) {
  suspend operator fun invoke(items: List<OrderItem>): Sale {
    val now = Clock.System.now().toEpochMilliseconds()
    val sale = Sale(id = now.toString(), items = items, timestamp = now)
    val savedSale = saleRepository.saveSale(sale)

    items.forEach { item ->
      decrementStockUseCase(item.product.id, item.quantity.toLong())
    }

    return savedSale
  }
}
