package com.worldsnas.kotlintesthelpers.rule

import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.functions.Function
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.util.concurrent.Callable

class RxTrampolineSchedulerRule : TestRule {

    companion object {
        @JvmStatic
        val TRAMPOLINE_SCHEDULER_INSTANCE = Schedulers.trampoline()
    }

    private val schedulerFunction = Function<Scheduler, Scheduler> { TRAMPOLINE_SCHEDULER_INSTANCE }

    private val schedulerFunctionLazy = Function<Callable<Scheduler>, Scheduler> { TRAMPOLINE_SCHEDULER_INSTANCE }

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                RxAndroidPlugins.reset()
                RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerFunctionLazy)

                RxJavaPlugins.reset()
                RxJavaPlugins.setIoSchedulerHandler(schedulerFunction)
                RxJavaPlugins.setNewThreadSchedulerHandler(schedulerFunction)
                RxJavaPlugins.setComputationSchedulerHandler(schedulerFunction)

                try {
                    base.evaluate()
                } finally {
                    RxAndroidPlugins.reset()
                    RxJavaPlugins.reset()
                }

            }
        }
    }
}
