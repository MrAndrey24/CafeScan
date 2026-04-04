package io.github.mrandrey24.cafescan.data.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver

actual class DriverFactory {
  actual fun createDriver(): SqlDriver {
    return NativeSqliteDriver(AppDatabase.Schema, "cafescan.db")
  }
}
