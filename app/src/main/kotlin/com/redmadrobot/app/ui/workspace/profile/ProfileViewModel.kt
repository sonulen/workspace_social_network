package com.redmadrobot.app.ui.workspace.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.redmadrobot.app.ui.base.delegate
import com.redmadrobot.app.ui.base.events.EventError
import com.redmadrobot.app.ui.base.events.EventNavigateTo
import com.redmadrobot.app.ui.base.viewmodel.BaseViewModel
import com.redmadrobot.app.ui.base.viewmodel.ScreenState
import com.redmadrobot.data.network.errors.NetworkException
import com.redmadrobot.domain.repository.AuthRepository
import com.redmadrobot.domain.repository.UserDataRepository
import com.redmadrobot.extensions.lifecycle.mapDistinct
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userDataRepository: UserDataRepository,
) : BaseViewModel() {
    private val liveState = MutableLiveData<ProfileViewState>(ProfileViewState())
    private var state: ProfileViewState by liveState.delegate()

    val screenState = liveState.mapDistinct { it.screenState }
    val userData = liveState.mapDistinct { it.userData }

    init {
        viewModelScope.launch {
            userDataRepository.getUserProfileDataFlow()
                .onStart {
                    state = state.copy(screenState = ScreenState.LOADING)
                }.catch { e ->
                    processError(e)
                }.collect {
                    state = state.copy(
                        screenState = ScreenState.CONTENT,
                        userData = UserDataProfileViewState(
                            nickname = it.nickname,
                            name = it.firstName,
                            surname = it.lastName,
                            birthDay = it.birthDay
                        )
                    )
                }
        }
    }

    private fun processError(e: Throwable) {
        state = state.copy(screenState = ScreenState.ERROR)
        if (e is NetworkException) {
            eventsQueue.offerEvent(EventError(e.message))
        }
    }

    fun onProfileEditClicked() {
        eventsQueue.offerEvent(EventNavigateTo(ProfileFragmentDirections.toProfileEditFragment()))
    }

    fun onLogoutClicked() {
        viewModelScope.launch {
            authRepository.logout()
                .onStart {
                    state = state.copy(screenState = ScreenState.LOADING)
                }.catch { e ->
                    processError(e)
                }.collect {
                    state = state.copy(screenState = ScreenState.CONTENT)
                    eventsQueue.offerEvent(EventNavigateTo(ProfileFragmentDirections.toAuthGraph()))
                }
        }
    }
}
