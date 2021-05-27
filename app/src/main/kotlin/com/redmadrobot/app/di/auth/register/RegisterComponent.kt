package com.redmadrobot.app.di.auth.register

import com.redmadrobot.app.di.validate.AuthValidatorModule
import com.redmadrobot.app.ui.auth.signup.register.RegisterFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RegisterViewModelModule::class,
        AuthValidatorModule::class
    ]
)
interface RegisterComponent {
    fun inject(obj: RegisterFragment)

    companion object {
        fun init(): RegisterComponent {
            return DaggerRegisterComponent.builder()
                .build()
        }
    }
}
