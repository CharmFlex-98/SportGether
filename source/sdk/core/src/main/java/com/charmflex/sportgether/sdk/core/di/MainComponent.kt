package com.charmflex.sportgether.sdk.core.di

import android.content.Context
import com.charmflex.sportgether.sdk.core.di.modules.ToolsModule
import com.charmflex.sportgether.sdk.core.storage.KeyStorageProvider
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ToolsModule::class
    ]
)
interface MainComponent : MainInjector{

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): MainComponent
    }

    companion object {
        fun injectCreate(context: Context): MainComponent {
            return DaggerMainComponent.factory().create(context)
        }
    }
}