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

    @Component.Factory
    interface Factory {
        fun create(): DoneComponent
    }

    companion object {
        fun init(): DoneComponent {
            return DaggerDoneComponent.factory().create()
        }
    }
}
