package io.github.mrandrey24.soda.data.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import compose.project.demo.composedemo.data.local.AppDatabase

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        TODO("Not yet implemented")
        return NativeSqliteDriver(AppDatabase.Schema, "soda.db")
    }
}