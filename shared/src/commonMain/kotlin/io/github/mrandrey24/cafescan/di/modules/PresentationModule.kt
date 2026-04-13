package io.github.mrandrey24.cafescan.di.modules

import io.github.mrandrey24.cafescan.presentation.ProductViewModel
import io.github.mrandrey24.cafescan.presentation.QrPreviewViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { ProductViewModel(get()) }
    viewModel { QrPreviewViewModel(get()) }
}
