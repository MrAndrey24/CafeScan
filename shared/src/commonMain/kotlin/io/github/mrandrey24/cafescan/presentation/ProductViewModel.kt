package io.github.mrandrey24.cafescan.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.mrandrey24.cafescan.data.repository.product.IProductRepository
import io.github.mrandrey24.cafescan.domain.entities.Product
import io.github.mrandrey24.cafescan.presentation.state.ProductUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductViewModel(
    private val productRepository: IProductRepository,
): ViewModel() {
    private val _uiState = MutableStateFlow(ProductUiState())
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    fun saveProduct() {
        val state = _uiState.value
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true, errorMessage = null) }
            try {
                val product = Product(
                    name = state.name,
                    price = state.priceDouble,
                    cost = state.costDouble,
                    emoji = state.emoji,
                    stock = state.stockInt
                )
                val productId = productRepository.saveProduct(product)
                _uiState.update { it.copy(isSaving = false, isSaved = true, savedProductId = productId) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isSaving = false, errorMessage = e.message) }
            }
        }
    }

    fun onNameChanged(name: String) = _uiState.update { it.copy(name = name) }
    fun onPriceChange(price: String) = _uiState.update { it.copy(price = price) }
    fun onCostChange(cost: String) = _uiState.update { it.copy(cost = cost) }
    fun onStockChange(stock: String) = _uiState.update { it.copy(stock = stock) }
    fun onEmojiChange(emoji: String) = _uiState.update { it.copy(emoji = emoji) }

    fun clearSaveState() = _uiState.update { it.copy(isSaved = false, savedProductId = null) }
}
