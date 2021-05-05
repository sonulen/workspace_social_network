package com.redmadrobot.app.ui

import com.redmadrobot.app.ui.base.viewmodel.BaseViewModel
import com.redmadrobot.domain.repository.AuthRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(private val authRepository: AuthRepository) : BaseViewModel() {

    fun requestDirections() {
        // TODO Заэмитить ивент по какому графу идти
    }
}
