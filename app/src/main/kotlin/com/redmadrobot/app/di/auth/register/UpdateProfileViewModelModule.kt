package com.redmadrobot.app.di.auth.register

import androidx.lifecycle.ViewModel
import com.redmadrobot.app.di.viewmodels.ViewModelFactoryModule
import com.redmadrobot.app.di.viewmodels.ViewModelKey
import com.redmadrobot.app.ui.auth.signup.updateProfile.UpdateProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelFactoryModule::class])
abstract class UpdateProfileViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(UpdateProfileViewModel::class)
    internal abstract fun bindUpdateProfileViewModel(viewModel: UpdateProfileViewModel): ViewModel
}
