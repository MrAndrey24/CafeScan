package io.github.mrandrey24.soda.di.modules

import org.koin.dsl.module

val sharedModule = module {
    includes(dataModule, domainModule, presentationModule, networkModule)
}