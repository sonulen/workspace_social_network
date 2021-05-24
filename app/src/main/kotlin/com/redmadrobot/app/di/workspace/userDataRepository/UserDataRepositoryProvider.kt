package com.redmadrobot.app.di.workspace.userDataRepository

import com.redmadrobot.domain.repository.UserDataRepository

interface UserDataRepositoryProvider {
    fun userDataRepository(): UserDataRepository
}
