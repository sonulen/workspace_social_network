package com.redmadrobot.app.ui.auth.signin

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.google.android.material.appbar.MaterialToolbar
import com.redmadrobot.app.R
import com.redmadrobot.app.ui.base.fragment.BaseFragment
import com.redmadrobot.data.repository.AuthRepositoryImpl
import com.redmadrobot.domain.usecases.login.LoginUseCase
import com.redmadrobot.extensions.lifecycle.Event
import com.redmadrobot.extensions.lifecycle.observe

class LoginFragment : BaseFragment(R.layout.login_fragment) {
    private lateinit var viewModel: LoginViewModel
    private lateinit var password: EditText
    private lateinit var email: EditText
    private lateinit var loginButton: Button

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // TODO Решить это все через DI
        val preferences = this.requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
        val authRepository = AuthRepositoryImpl(preferences)
        val loginUseCase = LoginUseCase(authRepository)
        viewModel = LoginViewModel(loginUseCase)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = inflater.inflate(R.layout.login_fragment, container, false)

        password = view.findViewById(R.id.editTextTextPassword)
        email = view.findViewById(R.id.editTextTextEmailAddress)
        loginButton = view.findViewById(R.id.button_login)

        observe(viewModel.eventsQueue, ::onEvent)

        registerButtonClickListeners(view)
        registerEmailEditTextListener()
        registerPasswordEditTexListener()

        return view
    }

    private fun onEvent(event: Event) {
        val navController = findNavController(this)

        when (event) {
            is EventLoginSuccess -> navController.navigate(R.id.toDoneFragment)

            is EventLoginFailed -> {
                // no-op //
            }

            is EventLoginFormStateChanged -> onLoginFormStateChange()
        }
    }

    private fun onLoginFormStateChange() {
        val loginState = viewModel.loginFormState
        loginState.emailError?.let {
            email.error = getString(it)
        }
        loginState.passwordError?.let {
            password.error = getString(it)
        }
        // Выставим доступность кнопки согласно валидности данных
        setEnableLoginButton(loginState.isDataValid)
    }

    private fun registerButtonClickListeners(view: View) {
        val navController = findNavController(this)

        view.findViewById<MaterialToolbar>(R.id.tool_bar).setNavigationOnClickListener {
            navController.navigate(R.id.loginFragmentPop)
        }

        view.findViewById<Button>(R.id.button_go_to_register).setOnClickListener {
            navController.navigate(R.id.toRegisterFragment)
        }

        loginButton.setOnClickListener {
            viewModel.onLoginClicked(email.text.toString(), password.text.toString())
        }
    }

    private fun registerEmailEditTextListener() {
        email.doAfterTextChanged {
            onLoginDataChanged()
        }
    }

    private fun registerPasswordEditTexListener() {
        password.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE ->
                    onLoginDataChanged()
            }
            false
        }

        password.doAfterTextChanged {
            onLoginDataChanged()
        }
    }

    private fun onLoginDataChanged() {
        viewModel.onLoginDataChanged(
            email.text.toString(),
            password.text.toString()
        )
    }

    private fun setEnableLoginButton(state: Boolean) {
        loginButton.isEnabled = state
    }
}
