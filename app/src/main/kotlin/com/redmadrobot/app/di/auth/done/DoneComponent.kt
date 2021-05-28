package com.redmadrobot.app.di.auth.done

import com.redmadrobot.app.ui.auth.done.DoneFragment
import dagger.Component

@Component(
    modules = [
        DoneViewModelModule::class,
    ]
)
interface DoneComponent {
    fun inject(obj: DoneFragment)

    companion object {
        fun init(): DoneComponent {
            return DaggerDoneComponent.builder().build()
        }
    }
}
