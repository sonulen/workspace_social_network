package com.redmadrobot.app.ui

import com.redmadrobot.app.ui.base.viewmodel.BaseViewModel
import com.redmadrobot.domain.repository.SessionRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(private val sessionRepository: SessionRepository) : BaseViewModel() {

    fun requestDirections() {
        // TODO Заэмитить ивент по какому графу идти
    }
}
