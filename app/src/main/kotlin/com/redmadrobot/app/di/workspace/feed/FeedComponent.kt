package com.redmadrobot.app.di.workspace.feed

import com.redmadrobot.app.ui.workspace.feed.FeedFragment
import dagger.Component

@Component(
    modules = [
        FeedViewModelModule::class,
    ]
)
interface FeedComponent {
    fun inject(obj: FeedFragment)

    @Component.Factory
    interface Factory {
        fun create(): FeedComponent
    }

    companion object {
        fun init(): FeedComponent {
            return DaggerFeedComponent.factory().create()
        }
    }
}
