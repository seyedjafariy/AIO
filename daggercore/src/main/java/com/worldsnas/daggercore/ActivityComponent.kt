package com.worldsnas.daggercore

import com.worldsnas.navigation.Navigator
import dagger.BindsInstance
import dagger.Component

@Component
interface ActivityComponent {

    fun navigator() : Navigator

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun bindNavigator(nav : Navigator) : Builder

        fun build() : ActivityComponent
    }
}