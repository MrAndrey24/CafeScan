package io.github.mrandrey24.cafescan.di.modules

import io.github.mrandrey24.cafescan.domain.usecases.inventory.GetAllActiveWithStockUseCase
import io.github.mrandrey24.cafescan.domain.usecases.product.CheckProductNameExistsUseCase
import io.github.mrandrey24.cafescan.domain.usecases.product.SaveProductUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { SaveProductUseCase(get()) }
    factory { CheckProductNameExistsUseCase(get()) }
    factory { GetAllActiveWithStockUseCase(get()) }
}
