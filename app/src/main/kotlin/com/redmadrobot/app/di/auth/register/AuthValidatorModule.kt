package com.redmadrobot.app.di.auth.register

import com.redmadrobot.domain.util.AuthValidator
import com.redmadrobot.domain.util.AuthValidatorImpl
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object AuthValidatorModule {
    @Provides
    @Reusable
    fun provideAuthApi(): AuthValidator = AuthValidatorImpl()
}
