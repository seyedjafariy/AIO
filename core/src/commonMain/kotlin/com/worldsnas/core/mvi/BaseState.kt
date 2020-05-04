package com.worldsnas.core.mvi

import com.worldsnas.core.ErrorHolder

data class BaseState(
    val stable: Boolean = true,
    val error: ErrorState = ErrorState.Disabled,
    val showLoading: Boolean = false,
    val showKeyboard: Boolean = false,
    val confirm: Boolean = false
) {

    sealed class ErrorState(val showSnackBar: Boolean) {
        data class String(val snackBarMessageString: kotlin.String) : ErrorState(showSnackBar = true)
        data class Res(val snackBarMessage: Int) : ErrorState(showSnackBar = true)
        data class UnAuthorized(val errorMessage: kotlin.String) : ErrorState(showSnackBar = false)
        object Disabled : ErrorState(showSnackBar = false)
    }

    companion object {
        fun stable() = BaseState()

        fun loading() = BaseState(
            stable = false,
            showLoading = true,
            showKeyboard = false
        )

        fun showKeyboard() =
            BaseState(stable = false, showKeyboard = true)

        fun showError(message: Int) =
            BaseState(
                stable = false,
                error = ErrorState.Res(message),
                showKeyboard = false
            )

        fun showError(message: String) =
            BaseState(
                stable = false,
                error = ErrorState.String(
                    message
                ),
                showKeyboard = false
            )

        fun withError(error: ErrorState) =
            BaseState(
                stable = false,
                error = error,
                showKeyboard = false
            )

        fun confirm() = BaseState(
            stable = false,
            confirm = true,
            showKeyboard = false
        )

        fun unAuthorized(errorMessage: String) =
            BaseState(
                stable = false,
                error = ErrorState.UnAuthorized(
                    errorMessage
                ),
                showKeyboard = false
            )
    }
}

fun ErrorHolder.toErrorState(): BaseState.ErrorState =
    when(this){
        is ErrorHolder.Message ->
            BaseState.ErrorState.String(message)
        is ErrorHolder.Res ->
            BaseState.ErrorState.Res(message)
        is ErrorHolder.UnAuthorized ->
            BaseState.ErrorState.UnAuthorized(message)
    }


