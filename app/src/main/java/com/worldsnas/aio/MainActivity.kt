package com.worldsnas.aio

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.view.DraweeTransition
import com.worldsnas.aio.di.ActivityComponent
import com.worldsnas.aio.di.DaggerActivityComponent
import com.worldsnas.daggercore.lifecycle.LifecycleComponentProvider
import com.worldsnas.daggercore.lifecycle.LifecycleEvent
import com.worldsnas.daggercore.lifecycle.Permissions
import com.worldsnas.daggercore.lifecycle.PermissionsResult
import com.worldsnas.home.view.HomeView
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(),
    LifecycleComponentProvider {

    @Inject
    lateinit var events: PublishSubject<LifecycleEvent>

    private lateinit var router: Router

    private val component: ActivityComponent by lazyComponent {
        createComponent()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)

        window.sharedElementEnterTransition = DraweeTransition.createTransitionSet(
            ScalingUtils.ScaleType.CENTER_CROP, ScalingUtils.ScaleType.CENTER_CROP
        )
        window.sharedElementEnterTransition = DraweeTransition.createTransitionSet(
            ScalingUtils.ScaleType.CENTER_CROP, ScalingUtils.ScaleType.CENTER_CROP
        )

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        router = Conductor.attachRouter(this, root, savedInstanceState)
        val view = HomeView()
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(view))
        }
    }

    override fun onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        events.onNext(
            LifecycleEvent.Permission(
                requestCode,
                permissions.map { Permissions.fromRawValue(it) },
                grantResults.map { PermissionsResult.fromRawValue(it) }
            ))
    }

    override fun lifecycleComponent(): ActivityComponent =
        component

    private fun createComponent(): ActivityComponent =
        DaggerActivityComponent.create()
}

internal class StoreViewModel : ViewModel() {

    var component: ActivityComponent? = null

    companion object {

        fun factory() = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                if (modelClass.isAssignableFrom(StoreViewModel::class.java)) {
                    StoreViewModel() as T
                } else {
                    throw IllegalArgumentException("this factory can only provide StoreViewModel")
                }
        }
    }
}

internal fun AppCompatActivity.lazyComponent(
    createComponent: () -> ActivityComponent
): Lazy<ActivityComponent> = lazy(LazyThreadSafetyMode.NONE) {
    val store = ViewModelProvider(
        this,
        StoreViewModel.factory()
    ).get(StoreViewModel::class.java)

    if (store.component != null) {
        store.component!!
    } else {
        store.component = createComponent()
        store.component!!
    }
}
