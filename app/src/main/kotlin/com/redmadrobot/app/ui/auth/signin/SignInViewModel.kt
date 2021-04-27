package com.redmadrobot.app.ui.auth.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.redmadrobot.app.ui.base.viewmodel.BaseViewModel

class SignInViewModel : BaseViewModel() {
    private val _loginForm = MutableLiveData<SignInFormState>()
    val loginFormState: LiveData<SignInFormState> = _loginForm

    private val _loginResult = MutableLiveData<SignInResult>()
    val loginResult: LiveData<SignInResult> = _loginResult
}
