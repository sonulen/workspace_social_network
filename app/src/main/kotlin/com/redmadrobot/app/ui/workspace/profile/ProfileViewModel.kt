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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
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
        userDataRepository.initProfileData()
            .onStart {
                state = state.copy(screenState = ScreenState.LOADING)
            }
            .catch { e ->
                processError(e)
            }
            .onEach { /* No-op */ }
            .launchIn(viewModelScope)

        userDataRepository.getUserProfileDataFlow()
            .onEach {
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
            .launchIn(viewModelScope)
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
        authRepository.logout()
            .onStart {
                state = state.copy(screenState = ScreenState.LOADING)
            }
            .catch { e ->
                processError(e)
            }
            .onEach {
                state = state.copy(screenState = ScreenState.CONTENT)
                eventsQueue.offerEvent(EventNavigateTo(ProfileFragmentDirections.toAuthGraph()))
            }
            .launchIn(viewModelScope)
    }
}
