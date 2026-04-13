package io.github.mrandrey24.cafescan.di.modules

import io.github.mrandrey24.cafescan.domain.usecases.GenerateQrUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GenerateQrUseCase() }
}
