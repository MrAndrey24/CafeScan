package io.github.mrandrey24.soda.components

import io.github.mrandrey24.soda.getPlatform

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}