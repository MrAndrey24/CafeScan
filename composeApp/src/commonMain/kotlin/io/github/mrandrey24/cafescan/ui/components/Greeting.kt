package io.github.mrandrey24.cafescan.components

import io.github.mrandrey24.cafescan.getPlatform

class Greeting {
  private val platform = getPlatform()

  fun greet(): String {
    return "Hello, ${platform.name}!"
  }
}
