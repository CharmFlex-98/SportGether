package com.charmflex.sportgether.sdk.auth.internal.di.modules

import com.charmflex.sportgether.sdk.auth.internal.navigation.AuthNavigator
import com.charmflex.sportgether.sdk.auth.internal.navigation.AuthNavigatorImp
import dagger.Binds
import dagger.Module

@Module
internal interface ToolsModule {

    @Binds
    fun bindNavigator(navigator: AuthNavigatorImp): AuthNavigator
}