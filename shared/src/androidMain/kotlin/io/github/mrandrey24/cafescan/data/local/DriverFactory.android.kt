package io.github.mrandrey24.cafescan.data.local

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import io.github.mrandrey24.soda.data.local.AppDatabase

actual class DriverFactory(private val context: Context) {
  actual fun createDriver(): SqlDriver {
    return AndroidSqliteDriver(AppDatabase.Schema, context, "soda.db")
  }
}
