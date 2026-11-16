/**
 * MIT License
 *
 * Copyright (c) 2026 Andrey Acosta  Ramirez
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
