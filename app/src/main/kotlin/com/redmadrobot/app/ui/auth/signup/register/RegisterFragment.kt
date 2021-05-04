package com.redmadrobot.app.ui.auth.signup.register

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
import com.redmadrobot.domain.usecases.signup.RegisterUseCase
import com.redmadrobot.domain.util.AuthValidatorImpl
import com.redmadrobot.extensions.lifecycle.Event
import com.redmadrobot.extensions.lifecycle.observe

class RegisterFragment : BaseFragment(R.layout.register_fragment) {
    private lateinit var viewModel: RegisterViewModel
    private lateinit var password: EditText
    private lateinit var email: EditText
    private lateinit var buttonRegister: Button

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // TODO Решить это все через DI
        val preferences = this.requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
        val authRepository = AuthRepositoryImpl(preferences)
        val registerUseCase = RegisterUseCase(authRepository, AuthValidatorImpl())
        viewModel = RegisterViewModel(registerUseCase)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = inflater.inflate(R.layout.register_fragment, container, false)

        password = view.findViewById(R.id.edit_text_password)
        email = view.findViewById(R.id.edit_text_email)
        buttonRegister = view.findViewById(R.id.button_register)

        observe(viewModel.eventsQueue, ::onEvent)
        registerButtonClickListeners(view)
        registerEmailEditTexListener()
        registerPasswordEditTexListener()

        return view
    }

    private fun onEvent(event: Event) {
        val navController = findNavController(this)

        when (event) {
            is EventRegisterSuccess -> navController.navigate(R.id.toUpdateProfileFragment)

            is EventRegisterFailed -> {
                // no-op //
            }

            is EventRegisterFormStateChanged -> onRegisterFormStateChange()
        }
    }

    private fun onRegisterFormStateChange() {
        val registerFormState = viewModel.registerFormState

        registerFormState.emailError?.let {
            email.error = getString(it)
        }
        registerFormState.passwordError?.let {
            password.error = getString(it)
        }

        // Выставим доступность кнопки согласно валидности данных
        setEnableRegisterButton(registerFormState.isDataValid)
    }

    private fun registerButtonClickListeners(view: View) {
        val navController = findNavController(this)
        view.findViewById<Button>(R.id.button_go_to_register).setOnClickListener {
            navController.navigate(R.id.toLoginFragment)
        }
        view.findViewById<Button>(R.id.button_register).setOnClickListener {
            viewModel.onRegisterClicked(email.text.toString(), password.text.toString())
        }
        view.findViewById<MaterialToolbar>(R.id.tool_bar).setNavigationOnClickListener {
            navController.navigate(R.id.registerFragmentPop)
        }
    }

    private fun registerPasswordEditTexListener() {
        password.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE ->
                    onRegisterDataChanged()
            }
            false
        }
        password.doAfterTextChanged {
            onRegisterDataChanged()
        }
    }

    private fun registerEmailEditTexListener() {
        email.doAfterTextChanged {
            onRegisterDataChanged()
        }
    }

    private fun onRegisterDataChanged() {
        viewModel.onRegisterDataChanged(
            email.text.toString(),
            password.text.toString()
        )
    }

    private fun setEnableRegisterButton(state: Boolean) {
        buttonRegister.isEnabled = state
    }
}
