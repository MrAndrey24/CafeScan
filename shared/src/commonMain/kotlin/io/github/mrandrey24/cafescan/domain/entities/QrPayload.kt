package io.github.mrandrey24.cafescan.domain.entities

data class QrPayload(val id: String, val name: String, val price: Double, val cost: Double) {
  fun toProduct(): Product = Product(id = id, name = name, price = price, cost = cost)
}
