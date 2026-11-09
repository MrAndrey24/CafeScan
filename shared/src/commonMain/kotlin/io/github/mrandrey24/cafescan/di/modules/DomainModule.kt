/**
 * Copyright (c) 2026 Andrey Acosta Ramirez
 * All Rights Reserved.
 *
 * This software is confidential and proprietary. Unauthorized copying,
 * distribution, or modification of this file is strictly prohibited.
 *
 */
package io.github.mrandrey24.cafescan.di.modules

import io.github.mrandrey24.cafescan.domain.usecases.UpdateStockUseCase
import io.github.mrandrey24.cafescan.domain.usecases.inventory.GetAllActiveWithStockUseCase
import io.github.mrandrey24.cafescan.domain.usecases.product.CheckProductNameExistsUseCase
import io.github.mrandrey24.cafescan.domain.usecases.product.DeleteProductUseCase
import io.github.mrandrey24.cafescan.domain.usecases.product.SaveProductUseCase
import io.github.mrandrey24.cafescan.domain.usecases.sale.GetSalesSummaryUseCase
import io.github.mrandrey24.cafescan.domain.usecases.sale.GetTodaySalesUseCase
import io.github.mrandrey24.cafescan.domain.usecases.sale.SaveSaleUseCase
import io.github.mrandrey24.cafescan.domain.usecases.scanner.CheckProductAvailabilityUseCase
import io.github.mrandrey24.cafescan.domain.usecases.scanner.DecrementStockUseCase
import io.github.mrandrey24.cafescan.domain.usecases.scanner.GetProductByIdUseCase
import org.koin.dsl.module

val domainModule = module {
  factory { SaveProductUseCase(get()) }
  factory { CheckProductNameExistsUseCase(get()) }
  factory { GetAllActiveWithStockUseCase(get()) }
  factory { UpdateStockUseCase(get()) }
  factory { DeleteProductUseCase(get()) }
  factory { CheckProductAvailabilityUseCase(get()) }
  factory { DecrementStockUseCase(get()) }
  factory { GetProductByIdUseCase(get()) }
  factory { SaveSaleUseCase(get(), get()) }
  factory { GetTodaySalesUseCase(get()) }
  factory { GetSalesSummaryUseCase(get()) }
}
