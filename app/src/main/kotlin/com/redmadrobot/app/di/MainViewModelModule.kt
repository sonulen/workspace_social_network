package com.redmadrobot.app.di

import androidx.lifecycle.ViewModel
import com.redmadrobot.app.di.viewmodels.ViewModelFactoryModule
import com.redmadrobot.app.di.viewmodels.ViewModelKey
import com.redmadrobot.app.ui.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelFactoryModule::class])
abstract class MainViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel
}
