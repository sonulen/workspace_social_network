package com.redmadrobot.app.di.auth.done

import androidx.lifecycle.ViewModel
import com.redmadrobot.app.di.viewmodels.ViewModelFactoryModule
import com.redmadrobot.app.di.viewmodels.ViewModelKey
import com.redmadrobot.app.ui.auth.done.DoneViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelFactoryModule::class])
abstract class DoneViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(DoneViewModel::class)
    internal abstract fun bindDoneViewModel(viewModel: DoneViewModel): ViewModel
}
