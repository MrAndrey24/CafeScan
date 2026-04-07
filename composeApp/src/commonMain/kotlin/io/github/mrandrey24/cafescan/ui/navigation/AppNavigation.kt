package io.github.mrandrey24.cafescan.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.mrandrey24.cafescan.ui.screen.CatalogScreen
import io.github.mrandrey24.cafescan.ui.screen.DashboardScreen
import io.github.mrandrey24.cafescan.ui.screen.HomeScreen
import io.github.mrandrey24.cafescan.ui.screen.InventoryScreen
import io.github.mrandrey24.cafescan.ui.screen.QrPreviewScreen
import io.github.mrandrey24.cafescan.ui.screen.SaleDetailScreen
import io.github.mrandrey24.cafescan.ui.screen.ScannerScreen
import kotlinx.serialization.Serializable

@Serializable object HomeRoute

@Serializable object DashboardRoute

@Serializable object CatalogRoute

@Serializable object InventoryRoute

@Serializable object ProductFormRoute

@Serializable object QrPreviewRoute

@Serializable object SaleDetailRoute

@Serializable object ScannerRoute

@Composable
fun AppNavigation() {
  val navController = rememberNavController()

  NavHost(navController = navController, startDestination = HomeRoute) {
    composable<HomeRoute> {
      HomeScreen(
          onNavigateToDashboard = { navController.navigate(DashboardRoute) },
          onNavigateToInventory = { navController.navigate(InventoryRoute) },
          onNavigateToCatalog = { navController.navigate(CatalogRoute) },
          onNavigateToScanner = { /* Already here */ },
      )
    }
    composable<DashboardRoute> {
      DashboardScreen(
          onBack = { navController.popBackStack() },
          onNavigateToScanner = { navController.navigate(HomeRoute) },
          onNavigateToInventory = { navController.navigate(InventoryRoute) },
          onNavigateToCatalog = { navController.navigate(CatalogRoute) },
      )
    }
    composable<CatalogRoute> {
      CatalogScreen(
          onBack = { navController.popBackStack() },
          onNavigateToScanner = { navController.navigate(HomeRoute) },
          onNavigateToDashboard = { navController.navigate(DashboardRoute) },
          onNavigateToInventory = { navController.navigate(InventoryRoute) },
          onNavigateToQrPreview = { navController.navigate(QrPreviewRoute) },
      )
    }
    composable<InventoryRoute> {
      InventoryScreen(
          onBack = { navController.popBackStack() },
          onNavigateToScanner = { navController.navigate(HomeRoute) },
          onNavigateToDashboard = { navController.navigate(DashboardRoute) },
          onNavigateToCatalog = { navController.navigate(CatalogRoute) },
      )
    }
    composable<QrPreviewRoute> {
      QrPreviewScreen(
          onBack = { navController.popBackStack() },
          onNavigateToScanner = { navController.navigate(HomeRoute) },
          onNavigateToDashboard = { navController.navigate(DashboardRoute) },
          onNavigateToInventory = { navController.navigate(InventoryRoute) },
      )
    }
    composable<SaleDetailRoute> { SaleDetailScreen(onBack = { navController.popBackStack() }) }
    composable<ScannerRoute> { ScannerScreen(onBack = { navController.popBackStack() }) }
  }
}
