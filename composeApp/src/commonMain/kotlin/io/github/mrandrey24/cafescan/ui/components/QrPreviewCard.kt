/**
 * Copyright (c) 2026 Andrey Acosta Ramirez
 * All Rights Reserved.
 *
 * This software is confidential and proprietary. Unauthorized copying,
 * distribution, or modification of this file is strictly prohibited.
 *
 */
package io.github.mrandrey24.cafescan.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.time.Clock
import qrscanner.CameraLens
import qrscanner.OverlayShape
import qrscanner.QrScanner

@Composable
fun QrPreviewCard(onScan: (String) -> Unit, modifier: Modifier = Modifier) {
  var openImagePicker by remember { mutableStateOf(false) }
  var lastScannedString by remember { mutableStateOf<String?>(null) }
  var lastScannedTime by remember { mutableStateOf(0L) }

  Card(
      modifier = modifier.fillMaxWidth().height(180.dp),
      shape = RoundedCornerShape(24.dp),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
  ) {
    Box(modifier = Modifier.fillMaxSize()) {
      QrScanner(
          modifier = Modifier.fillMaxSize(),
          flashlightOn = false,
          cameraLens = CameraLens.Back,
          openImagePicker = openImagePicker,
          onCompletion = { rawString ->
            val now = Clock.System.now().toEpochMilliseconds()
            if (rawString == lastScannedString && (now - lastScannedTime) < 1500L) {
              return@QrScanner
            }
            lastScannedString = rawString
            lastScannedTime = now
            onScan(rawString)
          },
          imagePickerHandler = { isOpen -> openImagePicker = isOpen },
          onFailure = {},
          overlayShape = OverlayShape.Square,
          customOverlay = {},
      )
    }
  }
}
