package com.worldsnas.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.bluelinelabs.conductor.Controller
import com.google.android.material.snackbar.Snackbar
import com.worldsnas.core.coreComponent
import com.worldsnas.core.di.CoreComponent
import com.worldsnas.core.visible
import com.worldsnas.mvi.MviIntent
import com.worldsnas.mvi.MviPresenter
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

abstract class BaseView<S : BaseViewState, I : MviIntent, P : MviPresenter<I, S>>
    : Controller() {

    @Suppress("MemberVisibilityCanBePrivate")
    val disposables = CompositeDisposable()

    lateinit var presenter: P

    var loadingView: LoadingView? = null
    var errorSnack: Snackbar? = null

    // private val inject by lazy {
    //     injectDependencies()
    // }

    override fun onContextAvailable(context: Context) {
        super.onContextAvailable(context)
        // inject
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View =
        inflater.inflate(getLayoutId(), container, false)
            .also {
                injectDependencies(it.coreComponent())
            }

    override fun onAttach(view: View) {
        super.onAttach(view)
        // bind()
        createLoading()
        createErrorSnack()
    }

    override fun onDetach(view: View) {
        super.onDetach(view)
        disposables.clear()
        loadingView = null
        errorSnack = null
    }

    private fun bind() {
        presenter.processIntents(intents())
        presenter.states().subscribeBy { render(it) }.addTo(disposables)
    }

    protected fun renderError(baseState: BaseState) {
        view?.run {
            if (baseState.showLoading) {
                val error = baseState.error.getErrorString(context)
                if (errorSnack == null) {
                    createErrorSnack()
                }
                errorSnack?.setText(error)
                if (errorSnack?.isShown != true) {
                    errorSnack?.show()
                }else{
                    //empty
                }
            } else {
                errorSnack?.dismiss()
            }
        }
    }

    protected fun renderLoading(baseState: BaseState) {
        if (baseState.showLoading) {
            if (loadingView == null) {
                createLoading()
            }
            loadingView?.visible(true)
        } else {
            loadingView?.visible(false)
        }
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
                loadingView = LoadingView(parent.context)
                parent.addView(loadingView)
                loadingView!! visible false
            }
        }
    }

    private fun createErrorSnack() {
        view?.run {
            errorSnack = Snackbar.make(this, "", Snackbar.LENGTH_LONG)
        }
    }

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected abstract fun injectDependencies(core : CoreComponent)

    protected abstract fun render(state: S)

    protected abstract fun intents(): Observable<I>
}