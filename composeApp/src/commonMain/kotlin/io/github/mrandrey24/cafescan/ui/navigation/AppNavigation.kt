package io.github.mrandrey24.cafescan.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import io.github.mrandrey24.cafescan.presentation.InventoryViewModel
import io.github.mrandrey24.cafescan.presentation.ProductViewModel
import io.github.mrandrey24.cafescan.presentation.QrViewModel
import io.github.mrandrey24.cafescan.presentation.ScannerViewModel
import io.github.mrandrey24.cafescan.ui.screen.CatalogScreen
import io.github.mrandrey24.cafescan.ui.screen.DashboardScreen
import io.github.mrandrey24.cafescan.ui.screen.HomeScreen
import io.github.mrandrey24.cafescan.ui.screen.InventoryScreen
import io.github.mrandrey24.cafescan.ui.screen.LowStockScreen
import io.github.mrandrey24.cafescan.ui.screen.QrPreviewScreen
import io.github.mrandrey24.cafescan.ui.screen.SaleDetailScreen
import io.github.mrandrey24.cafescan.ui.screen.ScannerScreen
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

@Serializable object HomeRoute

@Serializable object DashboardRoute

@Serializable object CatalogRoute

@Serializable object InventoryRoute

@Serializable object LowStockRoute

@Serializable object ProductFormRoute

@Serializable data class QrPreviewRoute(val productId: Long)

@Serializable object SaleDetailRoute

@Serializable object ScannerRoute

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val productViewModel = koinViewModel<ProductViewModel>()
    val qrViewModel = koinViewModel<QrViewModel>()
    val scannerViewModel = koinViewModel<ScannerViewModel>()
    val inventoryViewModel = koinViewModel<InventoryViewModel>()

  NavHost(navController = navController, startDestination = HomeRoute) {
    composable<HomeRoute> {
      HomeScreen(
          onNavigateToDashboard = { navController.navigate(DashboardRoute) },
          onNavigateToInventory = { navController.navigate(InventoryRoute) },
          onNavigateToCatalog = { navController.navigate(CatalogRoute) },
//          onNavigateToScanner = { navController.navigate(HomeScreen()) },
          scannerViewModel,
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
          onNavigateToQrPreview = { navController.navigate(QrPreviewRoute(productId = it)) },
          productViewModel
      )
    }
    composable<InventoryRoute> {
      InventoryScreen(
          onBack = { navController.popBackStack() },
          onNavigateToScanner = { navController.navigate(HomeRoute) },
          onNavigateToDashboard = { navController.navigate(DashboardRoute) },
          onNavigateToCatalog = { navController.navigate(CatalogRoute) },
          onNavigateToLowStock = { navController.navigate(LowStockRoute) },
          inventoryViewModel
      )
    }
    composable<LowStockRoute> {
      LowStockScreen(
          onBack = { navController.popBackStack() },
          onNavigateToScanner = { navController.navigate(HomeRoute) },
          onNavigateToDashboard = { navController.navigate(DashboardRoute) },
          onNavigateToCatalog = { navController.navigate(CatalogRoute) },
          inventoryViewModel
      )
    }
    composable<QrPreviewRoute> { backStackEntry ->
      val route: QrPreviewRoute = backStackEntry.toRoute()
      QrPreviewScreen(
          productId = route.productId,
          onBack = { navController.popBackStack() },
          onNavigateToScanner = { navController.navigate(HomeRoute) },
          onNavigateToDashboard = { navController.navigate(DashboardRoute) },
          onNavigateToInventory = { navController.navigate(InventoryRoute) },
          qrViewModel
      )
    }
    composable<SaleDetailRoute> { SaleDetailScreen(onBack = { navController.popBackStack() }) }
    composable<ScannerRoute> { ScannerScreen(onBack = { navController.popBackStack() }) }
  }
}
