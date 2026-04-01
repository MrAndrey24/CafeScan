package io.github.mrandrey24.cafescan

interface Platform {
  val name: String
}

expect fun getPlatform(): Platform
