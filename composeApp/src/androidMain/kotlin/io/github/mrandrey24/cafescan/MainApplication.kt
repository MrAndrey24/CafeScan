/**
 * Copyright (c) 2026 Andrey Acosta Ramirez
 * All Rights Reserved.
 *
 * This software is confidential and proprietary. Unauthorized copying,
 * distribution, or modification of this file is strictly prohibited.
 *
 */
package io.github.mrandrey24.cafescan

import android.app.Application
import io.github.mrandrey24.cafescan.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import qrgenerator.AppContext

class MainApplication : Application() {
  override fun onCreate() {
    super.onCreate()

    AppContext.apply { set(applicationContext) }

    initKoin {
      androidContext(this@MainApplication)
      androidLogger()
    }
  }
}
