package com.worldsnas.daggercore.navigator

import com.bluelinelabs.conductor.Router
import com.worldsnas.navigation.Navigator
import dagger.Module
import dagger.Provides

@Module
object NavigatorModule {

    @JvmStatic
    @Provides
    fun provideNavigator(router: Router): Navigator =
        ControllerNavigator(router)
}