package io.github.mrandrey24.cafescan

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.github.mrandrey24.cafescan.ui.navigation.AppNavigation
import io.github.mrandrey24.cafescan.ui.theme.CafeScanTheme

@Composable
@Preview
fun App() {
  CafeScanTheme {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
      AppNavigation()
    }
  }
}
