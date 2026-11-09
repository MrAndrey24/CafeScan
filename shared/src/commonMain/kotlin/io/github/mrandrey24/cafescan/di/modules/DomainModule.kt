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
