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
