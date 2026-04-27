package io.github.mrandrey24.cafescan

import androidx.compose.ui.window.ComposeUIViewController
import io.github.mrandrey24.cafescan.di.initKoin

fun MainViewController() = ComposeUIViewController(configure = { initKoin() }) { App() }
