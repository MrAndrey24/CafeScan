package io.github.mrandrey24.cafescan.domain.qr

import io.github.mrandrey24.cafescan.domain.product.Product

data class QrPayload(val id: String, val name: String, val price: Double, val cost: Double) {
  fun toProduct() = Product(id = id, name = name, price = price, cost = cost)
}
