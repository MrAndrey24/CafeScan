/**
 * Copyright (c) 2026 Andrey Acosta Ramirez
 * All Rights Reserved.
 *
 * This software is confidential and proprietary. Unauthorized copying,
 * distribution, or modification of this file is strictly prohibited.
 *
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
