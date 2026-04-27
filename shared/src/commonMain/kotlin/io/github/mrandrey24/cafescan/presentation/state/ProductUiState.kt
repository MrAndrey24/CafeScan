package io.github.mrandrey24.cafescan.presentation.state

data class ProductUiState(
    val name: String = "",
    val price: String = "",
    val cost: String = "",
    val stock: String = "",
    val emoji: String = "",
    val isSaving: Boolean = false,
    val isSaved: Boolean = false,
    val isProductNameExists: Boolean = false,
    val savedProductId: Long? = null,
    val errorMessage: String? = null
) {
    val priceDouble: Double get() = price.toDoubleOrNull() ?: 0.0
    val costDouble:  Double get() = cost.toDoubleOrNull()  ?: 0.0
    val stockInt: Long get() = stock.toLongOrNull()    ?: 0

    val margin: Double get() = priceDouble - costDouble
    val marginPercent: Int get() = if (priceDouble > 0) ((margin / priceDouble) * 100).toInt() else 0

    val isNameValid:  Boolean get() = name.isNotBlank()
    val isPriceValid: Boolean get() = price.toDoubleOrNull() != null && priceDouble > 0.0
    val isCostValid:  Boolean get() = cost.toDoubleOrNull() != null && costDouble >= 0.0 && costDouble <= priceDouble
    val isStockValid: Boolean get() = stock.toLongOrNull() != null && stockInt >= 0
    val isEmojiValid: Boolean get() = emoji.isNotBlank()
    val isFormValid:  Boolean get() =
        isNameValid && isPriceValid && isCostValid && isStockValid && isEmojiValid && !isProductNameExists

}