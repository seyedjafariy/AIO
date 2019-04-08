package com.worldsnas.navigation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller

class TestController : Controller() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        throw NotImplementedError("this class is only visible for testing and should not be used")
    }
}