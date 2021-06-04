package com.redmadrobot.app.di.workspace.profileEdit

import androidx.lifecycle.ViewModel
import com.redmadrobot.app.di.viewmodels.ViewModelFactoryModule
import com.redmadrobot.app.di.viewmodels.ViewModelKey
import com.redmadrobot.app.ui.workspace.profileEdit.ProfileEditViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelFactoryModule::class])
abstract class ProfileEditViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ProfileEditViewModel::class)
    internal abstract fun bindUProfileEditViewModel(viewModel: ProfileEditViewModel): ViewModel
}
