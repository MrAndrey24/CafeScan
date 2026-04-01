package io.github.mrandrey24.soda.di.modules

import io.github.mrandrey24.soda.data.local.DriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single { DriverFactory() }
}