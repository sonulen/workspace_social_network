package com.redmadrobot.app.di.validate

import com.redmadrobot.domain.util.AuthValidator
import com.redmadrobot.domain.util.AuthValidatorImpl
import dagger.Module
import dagger.Provides

@Module
object AuthValidatorModule {
    @Provides
    fun provideAuthApi(): AuthValidator = AuthValidatorImpl()
}
