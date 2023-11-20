package com.charmflex.sportgether.app.home.di

import com.charmflex.sportgether.app.home.di.modules.HomeUIModule
import com.charmflex.sportgether.app.home.ui.event.EventBoardViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        HomeUIModule::class
    ]
)
interface HomeUIComponent {

    @Component.Factory
    interface Factory {
        fun create(): HomeUIComponent
    }

    companion object {
        fun injectCreate(): HomeUIComponent {
            return DaggerHomeUIComponent.factory().create()
        }
    }

    fun getEventBoardViewModel(): EventBoardViewModel
}