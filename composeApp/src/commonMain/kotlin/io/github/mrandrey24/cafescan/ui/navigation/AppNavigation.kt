/**
 * Copyright (c) 2026 Andrey Acosta Ramirez
 * All Rights Reserved.
 *
 * This software is confidential and proprietary. Unauthorized copying,
 * distribution, or modification of this file is strictly prohibited.
 *
 */
package io.github.mrandrey24.cafescan.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import io.github.mrandrey24.cafescan.presentation.InventoryViewModel
import io.github.mrandrey24.cafescan.presentation.ProductViewModel
import io.github.mrandrey24.cafescan.presentation.QrViewModel
import io.github.mrandrey24.cafescan.presentation.SaleViewModel
import io.github.mrandrey24.cafescan.presentation.ScannerViewModel
import io.github.mrandrey24.cafescan.ui.navigation.route.CatalogRoute
import io.github.mrandrey24.cafescan.ui.navigation.route.DashboardRoute
import io.github.mrandrey24.cafescan.ui.navigation.route.HomeRoute
import io.github.mrandrey24.cafescan.ui.navigation.route.InventoryRoute
import io.github.mrandrey24.cafescan.ui.navigation.route.LowStockRoute
import io.github.mrandrey24.cafescan.ui.navigation.route.ProductFormRoute
import io.github.mrandrey24.cafescan.ui.navigation.route.QrPreviewRoute
import io.github.mrandrey24.cafescan.ui.navigation.route.SaleDetailRoute
import io.github.mrandrey24.cafescan.ui.navigation.route.SalesSummaryRoute
import io.github.mrandrey24.cafescan.ui.navigation.route.ScannerRoute
import io.github.mrandrey24.cafescan.ui.screen.CatalogScreen
import io.github.mrandrey24.cafescan.ui.screen.DashboardScreen
import io.github.mrandrey24.cafescan.ui.screen.HomeScreen
import io.github.mrandrey24.cafescan.ui.screen.InventoryScreen
import io.github.mrandrey24.cafescan.ui.screen.LowStockScreen
import io.github.mrandrey24.cafescan.ui.screen.ProductFormScreen
import io.github.mrandrey24.cafescan.ui.screen.QrPreviewScreen
import io.github.mrandrey24.cafescan.ui.screen.SaleDetailScreen
import io.github.mrandrey24.cafescan.ui.screen.SalesSummaryScreen
import io.github.mrandrey24.cafescan.ui.screen.ScannerScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppNavigation() {
  val navController = rememberNavController()
  val productViewModel = koinViewModel<ProductViewModel>()
  val qrViewModel = koinViewModel<QrViewModel>()
  val scannerViewModel = koinViewModel<ScannerViewModel>()
  val inventoryViewModel = koinViewModel<InventoryViewModel>()
  val saleViewModel = koinViewModel<SaleViewModel>()

  NavHost(navController = navController, startDestination = HomeRoute) {
    composable<HomeRoute> {
      HomeScreen(
          onNavigateToDashboard = { navController.navigate(DashboardRoute) },
          onNavigateToInventory = { navController.navigate(InventoryRoute) },
          onNavigateToCatalog = { navController.navigate(CatalogRoute) },
          viewModel = scannerViewModel,
      )
    }
    composable<DashboardRoute> {
      DashboardScreen(
          onBack = { navController.popBackStack() },
          onNavigateToScanner = { navController.navigate(HomeRoute) },
          onNavigateToInventory = { navController.navigate(InventoryRoute) },
          onNavigateToCatalog = { navController.navigate(CatalogRoute) },
          onNavigateToSummary = { navController.navigate(SalesSummaryRoute) },
          saleViewModel = saleViewModel,
          scannerViewModel = scannerViewModel,
      )
    }
    composable<CatalogRoute> {
      CatalogScreen(
          onBack = { navController.popBackStack() },
          onNavigateToScanner = { navController.navigate(HomeRoute) },
          onNavigateToDashboard = { navController.navigate(DashboardRoute) },
          onNavigateToInventory = { navController.navigate(InventoryRoute) },
          onNavigateToQrPreview = { navController.navigate(QrPreviewRoute(productId = it)) },
          viewModel = productViewModel,
      )
    }
    composable<InventoryRoute> {
      InventoryScreen(
          onBack = { navController.popBackStack() },
          onNavigateToScanner = { navController.navigate(HomeRoute) },
          onNavigateToDashboard = { navController.navigate(DashboardRoute) },
          onNavigateToCatalog = { navController.navigate(CatalogRoute) },
          onNavigateToLowStock = { navController.navigate(LowStockRoute) },
          viewModel = inventoryViewModel,
      )
    }
    composable<LowStockRoute> {
      LowStockScreen(
          onBack = { navController.popBackStack() },
          onNavigateToScanner = { navController.navigate(HomeRoute) },
          onNavigateToDashboard = { navController.navigate(DashboardRoute) },
          onNavigateToCatalog = { navController.navigate(CatalogRoute) },
          viewModel = inventoryViewModel,
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
          viewModel = qrViewModel,
      )
    }
    composable<ProductFormRoute> { ProductFormScreen(onBack = { navController.popBackStack() }) }
    composable<SaleDetailRoute> { SaleDetailScreen(onBack = { navController.popBackStack() }) }
    composable<ScannerRoute> { ScannerScreen(onBack = { navController.popBackStack() }) }
    composable<SalesSummaryRoute> {
      SalesSummaryScreen(
          onBack = { navController.popBackStack() },
          viewModel = saleViewModel,
      )
    }
  }
}
