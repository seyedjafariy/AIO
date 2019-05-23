package com.worldsnas.kotlintesthelpers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router

class TestActivity : AppCompatActivity() {

    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        router = Conductor.attachRouter(
            this,
            findViewById(R.id.rootTest),
            savedInstanceState
        )
    }
}