package io.github.mrandrey24.cafescan.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import io.github.mrandrey24.cafescan.presentation.ProductViewModel
import io.github.mrandrey24.cafescan.presentation.QrPreviewViewModel
import io.github.mrandrey24.cafescan.presentation.ScannerViewModel
import io.github.mrandrey24.cafescan.ui.screen.CatalogScreen
import io.github.mrandrey24.cafescan.ui.screen.DashboardScreen
import io.github.mrandrey24.cafescan.ui.screen.HomeScreen
import io.github.mrandrey24.cafescan.ui.screen.InventoryScreen
import io.github.mrandrey24.cafescan.ui.screen.QrPreviewScreen
import io.github.mrandrey24.cafescan.ui.screen.SaleDetailScreen
import io.github.mrandrey24.cafescan.ui.screen.ScannerScreen
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

@Serializable object HomeRoute

@Serializable object DashboardRoute

@Serializable object CatalogRoute

@Serializable object InventoryRoute

@Serializable object ProductFormRoute

@Serializable data class QrPreviewRoute(val productId: Long)

@Serializable object SaleDetailRoute

@Serializable object ScannerRoute

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val productViewModel = koinViewModel<ProductViewModel>()
    val qrPreviewViewModel = koinViewModel<QrPreviewViewModel>()
    val scannerViewModel = koinViewModel<ScannerViewModel>()

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
          onNavigateToQrPreview = { id -> navController.navigate(QrPreviewRoute(id)) },
          productViewModel
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
    composable<QrPreviewRoute> { backStackEntry ->
      val route: QrPreviewRoute = backStackEntry.toRoute()
      QrPreviewScreen(
          productId = route.productId,
          onBack = { navController.popBackStack() },
          onNavigateToScanner = { navController.navigate(HomeRoute) },
          onNavigateToDashboard = { navController.navigate(DashboardRoute) },
          onNavigateToInventory = { navController.navigate(InventoryRoute) },
          qrPreviewViewModel
      )
    }
    composable<SaleDetailRoute> { SaleDetailScreen(onBack = { navController.popBackStack() }) }
    composable<ScannerRoute> { ScannerScreen(onBack = { navController.popBackStack() }) }
  }
}
