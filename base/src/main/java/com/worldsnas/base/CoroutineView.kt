package com.worldsnas.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.worldsnas.daggercore.CoreComponent
import com.worldsnas.daggercore.coreComponent
import com.worldsnas.mvi.MviIntent
import com.worldsnas.mvi.MviPresenter
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.rx2.asFlow
import timber.log.Timber
import javax.inject.Inject

@Suppress("UNUSED_PARAMETER")
abstract class CoroutineView<
        V : ViewBinding,
        S : BaseViewState,
        I : MviIntent
        > @JvmOverloads constructor(
    bundle: Bundle? = null
) : ViewBindingController<V>(bundle), CoroutineScope by MainScope(){

    lateinit var coreComponent: CoreComponent
    @Inject
    lateinit var presenter: MviPresenter<I, S>

    var loadingView: View? = null
    var errorSnack: Snackbar? = null

    private val inject by lazy {
        prepareDependencies()
    }

    override fun onContextAvailable(context: Context) {
        super.onContextAvailable(context)
        inject
    }

    @CallSuper
    override fun onViewBound(binding: V) {
        bind()
        createLoading()
        createErrorSnack()
    }

    @CallSuper
    override fun unBindView() {
        coroutineContext.cancelChildren()
        loadingView = null
        errorSnack = null
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }

    private fun prepareDependencies() {
        if (!::coreComponent.isInitialized) {
            coreComponent = applicationContext!!.coreComponent()
        }
        injectDependencies(coreComponent)
    }

    private fun bind() {
        intents()
            .newIntents()

        presenter
            .states()
            .asFlow()
            .catch {
                Timber.e("presenter state flow exception caught")
            }
            .onEach {
                render(it)
            }
            .launchIn(this)
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected fun Flow<I>.newIntents() =
        catch {
            Timber.e("view intents flow exception caught")
        }.onEach {
            presenter.processIntents(it)
        }.launchIn(this@CoroutineView)

    protected fun renderError(baseState: BaseState) {
//        view?.run {
//            if (baseState.error.showSnackBar) {
//                val error = baseState.error.getErrorString(context)
//                if (errorSnack == null) {
//                    createErrorSnack()
//                }
//                errorSnack?.setText(error)
//                if (errorSnack?.isShown != true) {
//                    errorSnack?.show()
//                } else {
//                    empty
//                }
//            } else {
//                errorSnack?.dismiss()
//            }
//        }
    }

    protected fun renderLoading(baseState: BaseState) {
//        if (baseState.showLoading) {
//            if (loadingView == null) {
//                createLoading()
//            }
//            loadingView?.visible(true)
//        } else {
//            loadingView?.visible(false)
//        }
    }

    private fun createLoading() {
        view?.also {
            val parent: ViewGroup? =
                if (it is ViewGroup) {
                    it
                } else {
                    if (it.parent is ViewGroup) {
                        it.parent as ViewGroup
                    } else {
                        null
                    }
                }

            if (parent != null) {
                loadingView = LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.view_loading, parent, false)
                parent.addView(loadingView)
                loadingView!! visible false
            }
        }
    }

    private fun createErrorSnack() {
//        view?.run {
//            errorSnack = Snackbar.make(this, "", Snackbar.LENGTH_LONG)
//        }
    }

    protected abstract fun injectDependencies(core: CoreComponent)

    protected abstract fun render(state: S)

    protected abstract fun intents(): Flow<I>
}