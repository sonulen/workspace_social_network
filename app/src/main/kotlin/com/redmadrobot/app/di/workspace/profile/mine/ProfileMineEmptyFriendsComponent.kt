package com.redmadrobot.app.di.workspace.profile.mine

import com.redmadrobot.app.ui.workspace.profile.mine.ProfileMineEmptyFriendsFragment
import dagger.Component

@Component(
    modules = [
        ProfileMineEmptyFriendsViewModelModule::class,
    ]
)
interface ProfileMineEmptyFriendsComponent {
    fun inject(obj: ProfileMineEmptyFriendsFragment)

    @Component.Factory
    interface Factory {
        fun create(): ProfileMineEmptyFriendsComponent
    }

    companion object {
        fun init(): ProfileMineEmptyFriendsComponent {
            return DaggerProfileMineEmptyFriendsComponent.factory().create()
        }
    }
}
