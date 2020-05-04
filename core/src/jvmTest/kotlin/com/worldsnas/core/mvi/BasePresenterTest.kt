package com.worldsnas.core.mvi

import com.worldsnas.core.ofType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

val initialState = BasePresenterTest.State(false, false)

class BasePresenterTest {


    sealed class Intent : MviIntent {
        object First : Intent()
        object Second : Intent()

    }

    sealed class MVIResult : MviResult {
        object First : MVIResult()
        object Second : MVIResult()
    }

    data class State(
        val first: Boolean,
        val second: Boolean
    ) : MviViewState

    class FakeProcessor : BaseProcessor<Intent, MVIResult>() {
        override fun Flow<Intent>.transformers(): List<Flow<MVIResult>> = listOf(
            ofType<Intent.First>()
                .map {
                    MVIResult.First
                },
            ofType<Intent.Second>()
                .map {
                    MVIResult.Second
                }
        )
    }

    lateinit var presenter: FakePresenter

    class FakePresenter :
        BasePresenter<Intent, State, MVIResult>(FakeProcessor(), initialState) {
        override fun reduce(preState: State, result: MVIResult): State =
            when (result) {
                MVIResult.First -> preState.copy(first = true, second = false)
                MVIResult.Second -> preState.copy(second = true, first = false)
            }

    }

    private val mainThreadSurrogate = TestCoroutineDispatcher()

    @Before
    fun setup() {

        Dispatchers.setMain(mainThreadSurrogate)
        presenter = FakePresenter()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `first states comes through`(): Unit = runBlocking(mainThreadSurrogate) {

        val stateFlow = presenter.states()

        presenter.processIntents(Intent.First)
        presenter.processIntents(Intent.Second)

        val states = stateFlow.take(3).toList()

        presenter.close()

        assertThat(states).isEqualTo(
            listOf(
                initialState,
                initialState.copy(first = true),
                initialState.copy(second = true)
            )
        )
        Unit
    }

    @Test
    fun `second collector only gets the last state`() = runBlocking(mainThreadSurrogate) {
        val stateFlow = presenter.states()

        presenter.processIntents(Intent.First)
        presenter.processIntents(Intent.Second)

        stateFlow.take(2).toList()

        val states = presenter.states().take(1).toList()

        assertThat(states).isEqualTo(
            listOf(
                initialState.copy(second = true)
            )
        )

        Unit
    }

    @Test
    fun `closing presenter closes all channels and flows`() = runBlocking(mainThreadSurrogate) {
        presenter.processIntents(Intent.First)
        presenter.processIntents(Intent.Second)

        val stateFlow = presenter.states()
        stateFlow.take(1).toList()

        launch {
            presenter.states().collect()
        }

        presenter.close()

//        assertThat(presenter.holder.channelList).isEmpty()

//        assertThat(presenter.intents.isClosedForReceive).isTrue()
//        assertThat(presenter.intents.isClosedForSend).isTrue()

        Unit
    }

}