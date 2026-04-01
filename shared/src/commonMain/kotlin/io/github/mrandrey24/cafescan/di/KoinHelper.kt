package io.github.mrandrey24.cafescan.di

import io.github.mrandrey24.cafescan.di.modules.sharedModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes

fun initKoin(config: KoinAppDeclaration? = null): KoinApplication {
  return startKoin {
    includes(config)
    modules(sharedModule)
  }
}
