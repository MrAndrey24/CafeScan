package io.github.mrandrey24.cafescan.presentation.state

sealed class ScanError {
    object InvalidFormat: ScanError()
    object ProductNotFound: ScanError()
}
