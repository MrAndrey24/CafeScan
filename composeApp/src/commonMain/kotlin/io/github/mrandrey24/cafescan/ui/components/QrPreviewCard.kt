package io.github.mrandrey24.cafescan.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun QrPreviewCard(onScan: (String) -> Unit, modifier: Modifier = Modifier) {
  Card(
      modifier = modifier.fillMaxWidth().height(320.dp),
      shape = RoundedCornerShape(24.dp),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
  ) {
    Box(modifier = Modifier.fillMaxSize()) {
      // QR Scanner Component
      //            QrScanner(
      //                modifier = Modifier.fillMaxSize(),
      //                flashlightOn = TODO(),
      //                cameraLens = TODO(),
      //                openImagePicker = TODO(),
      //                onCompletion = TODO(),
      //                imagePickerHandler = TODO(),
      //                onFailure = TODO(),
      //                overlayShape = TODO(),
      //                overlayColor = TODO(),
      //                overlayBorderColor = TODO(),
      //                zoomLevel = TODO(),
      //                maxZoomLevel = TODO(),
      //                imagePickerDialogStyle = TODO(),
      //                customOverlay = TODO(),
      //                permissionDeniedView = TODO()
      //            )

      // Custom Overlay
      ScannerOverlay(modifier = Modifier.fillMaxSize())

      // Hint Text
      Text(
          text = "Point at a product QR code",
          color = Color.White.copy(alpha = 0.8f),
          fontSize = 14.sp,
          modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 24.dp),
      )
    }
  }
}

@Composable
fun ScannerOverlay(modifier: Modifier = Modifier) {
  Box(modifier = modifier, contentAlignment = Alignment.Center) {
    Canvas(modifier = Modifier.size(220.dp)) {
      val strokeWidth = 4.dp.toPx()
      val cornerLength = 40.dp.toPx()
      val color = Color.White

      // Top-left corner
      drawPath(
          path =
              Path().apply {
                moveTo(0f, cornerLength)
                lineTo(0f, 0f)
                lineTo(cornerLength, 0f)
              },
          color = color,
          style = Stroke(width = strokeWidth),
      )

      // Top-right corner
      drawPath(
          path =
              Path().apply {
                moveTo(size.width - cornerLength, 0f)
                lineTo(size.width, 0f)
                lineTo(size.width, cornerLength)
              },
          color = color,
          style = Stroke(width = strokeWidth),
      )

      // Bottom-left corner
      drawPath(
          path =
              Path().apply {
                moveTo(0f, size.height - cornerLength)
                lineTo(0f, size.height)
                lineTo(cornerLength, size.height)
              },
          color = color,
          style = Stroke(width = strokeWidth),
      )

      // Bottom-right corner
      drawPath(
          path =
              Path().apply {
                moveTo(size.width - cornerLength, size.height)
                lineTo(size.width, size.height)
                lineTo(size.width, size.height - cornerLength)
              },
          color = color,
          style = Stroke(width = strokeWidth),
      )

      // Middle horizontal line (indicator)
      drawLine(
          color = Color(0xFFD4A373).copy(alpha = 0.7f), // CoffeeBrown
          start = Offset(20f, size.height / 2),
          end = androidx.compose.ui.geometry.Offset(size.width - 20f, size.height / 2),
          strokeWidth = 2.dp.toPx(),
      )
    }
  }
}
