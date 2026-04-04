package io.github.mrandrey24.cafescan.di.modules

import io.github.mrandrey24.cafescan.data.local.AppDatabase
import io.github.mrandrey24.cafescan.data.local.DriverFactory
import org.koin.dsl.module

val dataModule = module {
    single { get<DriverFactory>().createDriver() }
    single { AppDatabase(get()) }
    single { get<AppDatabase>().appDatabaseQueries }
}
