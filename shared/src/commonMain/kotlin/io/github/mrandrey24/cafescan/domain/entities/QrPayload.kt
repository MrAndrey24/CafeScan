package io.github.mrandrey24.cafescan.domain.entities

data class QrPayload(
  val name: String,
  val price: String,
  val cost: String
) {
  fun toQrString(): String = """{""name":"$name","price":$price,"cost":$cost}"""

  companion object {
    fun fromQrString(raw: String): QrPayload? = runCatching {
      val name = raw.substringAfter("name\":\"").substringBefore("\",")
      val price = raw.substringAfter("price\":").substringBefore(",").trim()
      val cost = raw.substringAfter("cost\":").substringBefore("}").trim()
      QrPayload( name, price, cost)
    }.getOrNull()
  }
}
