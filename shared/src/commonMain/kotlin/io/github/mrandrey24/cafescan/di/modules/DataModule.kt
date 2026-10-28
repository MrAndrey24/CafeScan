package io.github.mrandrey24.cafescan.di.modules

import io.github.mrandrey24.cafescan.data.local.AppDatabase
import io.github.mrandrey24.cafescan.data.local.DriverFactory
import io.github.mrandrey24.cafescan.data.local.inventory.IInventoryLocalDataSource
import io.github.mrandrey24.cafescan.data.local.inventory.InventoryLocalDataSource
import io.github.mrandrey24.cafescan.data.repository.inventory.IInventoryRepository
import io.github.mrandrey24.cafescan.data.repository.inventory.InventoryRepository
import io.github.mrandrey24.cafescan.data.local.product.IProductLocalDataSource
import io.github.mrandrey24.cafescan.data.local.product.ProductLocalDataSource
import io.github.mrandrey24.cafescan.data.repository.product.IProductRepository
import io.github.mrandrey24.cafescan.data.repository.product.ProductRepository
import org.koin.dsl.module

val dataModule = module {
  single { get<DriverFactory>().createDriver() }
  single { AppDatabase(get()) }
  single { get<AppDatabase>().appDatabaseQueries }
  single<IProductLocalDataSource> { ProductLocalDataSource(get()) }
  single<IProductRepository> { ProductRepository(get()) }
  single<IInventoryLocalDataSource> { InventoryLocalDataSource(get()) }
  single<IInventoryRepository> { InventoryRepository(get()) }
}
