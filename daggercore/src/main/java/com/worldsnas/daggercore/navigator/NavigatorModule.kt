package com.worldsnas.daggercore.navigator

import android.app.Application
import com.bluelinelabs.conductor.Router
import com.worldsnas.navigation.Navigator
import dagger.Module
import dagger.Provides

@Module
object NavigatorModule {

    @JvmStatic
    @Provides
    fun provideNavigator(app : Application, router: Router): Navigator =
        ControllerNavigator(app, router)
}