/**
 * Copyright (c) 2026 Andrey Acosta Ramirez
 * All Rights Reserved.
 *
 * This software is confidential and proprietary. Unauthorized copying,
 * distribution, or modification of this file is strictly prohibited.
 *
 */
package io.github.mrandrey24.cafescan.domain.entities

data class QrPayload(val id: String, val name: String, val price: String, val cost: String) {
  fun toQrString(): String = """{"id":"$id","name":"$name","price":$price,"cost":$cost}"""

  companion object {
    fun fromQrString(raw: String): QrPayload? =
        runCatching {
              val id = raw.substringAfter("id\":\"").substringBefore("\",")
              val name = raw.substringAfter("name\":\"").substringBefore("\",")
              val price = raw.substringAfter("price\":").substringBefore(",").trim()
              val cost = raw.substringAfter("cost\":").substringBefore("}").trim()
              QrPayload(id, name, price, cost)
            }
            .getOrNull()
  }
}
