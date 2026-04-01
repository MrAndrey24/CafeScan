package io.github.mrandrey24.soda.data.local

import app.cash.sqldelight.db.SqlDriver

expect class DriverFactory {
    fun createDriver() : SqlDriver
}