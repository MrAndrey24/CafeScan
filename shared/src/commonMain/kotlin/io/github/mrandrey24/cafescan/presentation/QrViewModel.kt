package io.github.mrandrey24.cafescan.presentation

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.mrandrey24.cafescan.data.repository.product.IProductRepository
import io.github.mrandrey24.cafescan.presentation.state.QrPreviewUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QrViewModel(
    private val productRepository: IProductRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(QrPreviewUiState())
    val uiState: StateFlow<QrPreviewUiState> = _uiState.asStateFlow()

    fun loadProduct(productId: Long) {
        viewModelScope.launch {
            _uiState.update{ it.copy(isLoading = true) }
            try {
                val product = productRepository.retrieveProductById(productId)
                _uiState.update { it.copy(product = product, isLoading = false) }
            }catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    fun onQrBitmapReady(bitmap: ImageBitmap) {
        _uiState.update { it.copy(qrBitmap = bitmap) }
    }

    fun clearError() = _uiState.update { it.copy(errorMessage = null) }

}