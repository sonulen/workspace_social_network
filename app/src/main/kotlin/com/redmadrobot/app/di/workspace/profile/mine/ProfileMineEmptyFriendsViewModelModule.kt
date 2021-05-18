package com.redmadrobot.app.di.workspace.profile.mine

import androidx.lifecycle.ViewModel
import com.redmadrobot.app.di.viewmodels.ViewModelFactoryModule
import com.redmadrobot.app.di.viewmodels.ViewModelKey
import com.redmadrobot.app.ui.workspace.profile.mine.ProfileMineEmptyFriendsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelFactoryModule::class])
abstract class ProfileMineEmptyFriendsViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ProfileMineEmptyFriendsViewModel::class)
    internal abstract fun bindProfileMineEmptyFriendsViewModel(viewModel: ProfileMineEmptyFriendsViewModel): ViewModel
}
