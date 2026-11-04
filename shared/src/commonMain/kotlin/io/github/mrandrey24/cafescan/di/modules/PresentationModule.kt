/**
 * Copyright (c) 2026 Andrey Acosta Ramirez
 * All Rights Reserved.
 *
 * This software is confidential and proprietary. Unauthorized copying,
 * distribution, or modification of this file is strictly prohibited.
 *
 */
package io.github.mrandrey24.cafescan.di.modules

import io.github.mrandrey24.cafescan.presentation.InventoryViewModel
import io.github.mrandrey24.cafescan.presentation.ProductViewModel
import io.github.mrandrey24.cafescan.presentation.QrViewModel
import io.github.mrandrey24.cafescan.presentation.ScannerViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
  viewModel { ProductViewModel(get(), get()) }
  viewModel { QrViewModel(get()) }
  viewModel { ScannerViewModel(get(), get()) }
  viewModel { InventoryViewModel(get(), get(), get()) }
}
