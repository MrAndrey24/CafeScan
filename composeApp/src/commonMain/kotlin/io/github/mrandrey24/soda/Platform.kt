package io.github.mrandrey24.soda

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform