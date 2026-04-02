package io.github.mrandrey24.cafescan.domain.qr

sealed class QrGenerationState {
  object Idle : QrGenerationState()

  object Generating : QrGenerationState()

  data class Success(val svgOrBitmap: ByteArray, val payload: QrPayload) : QrGenerationState()

  data class Error(val message: String) : QrGenerationState()
}
