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

    companion object {
        fun init(): ProfileMineEmptyFriendsComponent {
            return DaggerProfileMineEmptyFriendsComponent.builder().build()
        }
    }
}
