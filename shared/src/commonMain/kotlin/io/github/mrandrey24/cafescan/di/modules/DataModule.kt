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

import io.github.mrandrey24.cafescan.data.local.AppDatabase
import io.github.mrandrey24.cafescan.data.local.DriverFactory
import io.github.mrandrey24.cafescan.data.local.inventory.IInventoryLocalDataSource
import io.github.mrandrey24.cafescan.data.local.inventory.InventoryLocalDataSource
import io.github.mrandrey24.cafescan.data.local.product.IProductLocalDataSource
import io.github.mrandrey24.cafescan.data.local.product.ProductLocalDataSource
import io.github.mrandrey24.cafescan.data.local.sale.ISaleLocalDataSource
import io.github.mrandrey24.cafescan.data.local.sale.SaleLocalDataSource
import io.github.mrandrey24.cafescan.data.repository.inventory.IInventoryRepository
import io.github.mrandrey24.cafescan.data.repository.inventory.InventoryRepository
import io.github.mrandrey24.cafescan.data.repository.product.IProductRepository
import io.github.mrandrey24.cafescan.data.repository.product.ProductRepository
import io.github.mrandrey24.cafescan.data.repository.sale.ISaleRepository
import io.github.mrandrey24.cafescan.data.repository.sale.SaleRepository
import org.koin.dsl.module

val dataModule = module {
  single { get<DriverFactory>().createDriver() }
  single { AppDatabase(get()) }
  single { get<AppDatabase>().appDatabaseQueries }
  single<IProductLocalDataSource> { ProductLocalDataSource(get()) }
  single<IProductRepository> { ProductRepository(get()) }
  single<IInventoryLocalDataSource> { InventoryLocalDataSource(get()) }
  single<IInventoryRepository> { InventoryRepository(get()) }
  single<ISaleLocalDataSource> { SaleLocalDataSource(get()) }
  single<ISaleRepository> { SaleRepository(get()) }
}
