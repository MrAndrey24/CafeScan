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
    val errorMessage: String? = null,
) {
  val priceDouble: Double
    get() = price.toDoubleOrNull() ?: 0.0

  val costDouble: Double
    get() = cost.toDoubleOrNull() ?: 0.0

  val stockInt: Long
    get() = stock.toLongOrNull() ?: 0

  val margin: Double
    get() = priceDouble - costDouble

  val marginPercent: Int
    get() = if (priceDouble > 0) ((margin / priceDouble) * 100).toInt() else 0

  val isNameValid: Boolean
    get() = name.isNotBlank()

  val isPriceValid: Boolean
    get() = price.toDoubleOrNull() != null && priceDouble > 0.0

  val isCostValid: Boolean
    get() = cost.toDoubleOrNull() != null && costDouble >= 0.0 && costDouble <= priceDouble

  val isStockValid: Boolean
    get() = stock.toLongOrNull() != null && stockInt >= 0

  val isEmojiValid: Boolean
    get() = emoji.isNotBlank()

  val isFormValid: Boolean
    get() =
        isNameValid &&
            isPriceValid &&
            isCostValid &&
            isStockValid &&
            isEmojiValid &&
            !isProductNameExists
}
