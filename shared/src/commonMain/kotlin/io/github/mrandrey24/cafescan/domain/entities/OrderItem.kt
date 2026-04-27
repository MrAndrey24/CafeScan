package io.github.mrandrey24.cafescan.domain.entities

data class OrderItem(val product: Product, var quantity: Int) {
  val subtotal: Double
    get() = product.price * quantity

  val profit: Double
    get() = product.margin * quantity
}
