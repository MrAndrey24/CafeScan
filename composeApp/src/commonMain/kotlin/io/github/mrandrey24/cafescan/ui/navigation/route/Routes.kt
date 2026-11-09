/**
 * Copyright (c) 2026 Andrey Acosta Ramirez
 * All Rights Reserved.
 *
 * This software is confidential and proprietary. Unauthorized copying,
 * distribution, or modification of this file is strictly prohibited.
 *
 */
package io.github.mrandrey24.cafescan.ui.navigation.route

import kotlinx.serialization.Serializable

@Serializable object HomeRoute

@Serializable object DashboardRoute

@Serializable object CatalogRoute

@Serializable object InventoryRoute

@Serializable object LowStockRoute

@Serializable object ProductFormRoute

@Serializable data class QrPreviewRoute(val productId: Long)

@Serializable object SaleDetailRoute

@Serializable object ScannerRoute

@Serializable object SalesSummaryRoute
