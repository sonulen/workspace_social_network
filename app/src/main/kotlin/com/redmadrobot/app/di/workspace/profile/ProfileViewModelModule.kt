package com.redmadrobot.app.di.workspace.profile

import androidx.lifecycle.ViewModel
import com.redmadrobot.app.di.viewmodels.ViewModelFactoryModule
import com.redmadrobot.app.di.viewmodels.ViewModelKey
import com.redmadrobot.app.ui.workspace.profile.ProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelFactoryModule::class])
abstract class ProfileViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    internal abstract fun bindProfileViewModel(viewModel: ProfileViewModel): ViewModel
}
