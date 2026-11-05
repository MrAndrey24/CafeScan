/**
 * Copyright (c) 2026 Andrey Acosta Ramirez
 * All Rights Reserved.
 *
 * This software is confidential and proprietary. Unauthorized copying,
 * distribution, or modification of this file is strictly prohibited.
 *
 */
package io.github.mrandrey24.cafescan.presentation

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.mrandrey24.cafescan.domain.usecases.scanner.GetProductByIdUseCase
import io.github.mrandrey24.cafescan.presentation.state.QrPreviewUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QrViewModel(private val getProductByIdUseCase: GetProductByIdUseCase) : ViewModel() {
  private val _uiState = MutableStateFlow(QrPreviewUiState())
  val uiState: StateFlow<QrPreviewUiState> = _uiState.asStateFlow()

  fun loadProduct(productId: Long) {
    viewModelScope.launch {
      _uiState.update {
        it.copy(product = null, qrBitmap = null, isLoading = true, errorMessage = null)
      }
      try {
        val product = getProductByIdUseCase(productId)
        _uiState.update { it.copy(product = product, isLoading = false) }
      } catch (e: Exception) {
        _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
      }
    }
  }

  fun onQrBitmapReady(bitmap: ImageBitmap) {
    _uiState.update { it.copy(qrBitmap = bitmap) }
  }
}
