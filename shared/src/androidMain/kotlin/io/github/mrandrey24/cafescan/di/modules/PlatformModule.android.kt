package io.github.mrandrey24.cafescan.di.modules

import io.github.mrandrey24.cafescan.data.local.DriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module { single { DriverFactory(get()) } }
