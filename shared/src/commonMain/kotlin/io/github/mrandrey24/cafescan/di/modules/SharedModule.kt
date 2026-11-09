/**
 * Copyright (c) 2026 Andrey Acosta Ramirez
 * All Rights Reserved.
 *
 * This software is confidential and proprietary. Unauthorized copying,
 * distribution, or modification of this file is strictly prohibited.
 *
 */
package io.github.mrandrey24.cafescan.di.modules

import org.koin.dsl.module

val sharedModule = module {
  includes(dataModule, domainModule, presentationModule, networkModule, platformModule())
}
