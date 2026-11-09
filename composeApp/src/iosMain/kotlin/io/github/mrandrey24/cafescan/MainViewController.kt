/**
 * Copyright (c) 2026 Andrey Acosta Ramirez
 * All Rights Reserved.
 *
 * This software is confidential and proprietary. Unauthorized copying,
 * distribution, or modification of this file is strictly prohibited.
 *
 */
package io.github.mrandrey24.cafescan

import androidx.compose.ui.window.ComposeUIViewController
import io.github.mrandrey24.cafescan.di.initKoin

fun MainViewController() = ComposeUIViewController(configure = { initKoin() }) { App() }
