package com.redmadrobot.app.ui.auth.signin

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.redmadrobot.app.R
import com.redmadrobot.app.databinding.LoginFragmentBinding
import com.redmadrobot.app.di.auth.login.LoginComponent
import com.redmadrobot.app.ui.base.fragment.BaseFragment
import com.redmadrobot.extensions.lifecycle.Event
import com.redmadrobot.extensions.lifecycle.observe
import com.redmadrobot.extensions.viewbinding.viewBinding
import javax.inject.Inject

class LoginFragment : BaseFragment(R.layout.login_fragment) {
    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: LoginViewModel by viewModels { viewModelFactory }

    private val binding: LoginFragmentBinding by viewBinding()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initDagger()
    }

    private fun initDagger() {
        LoginComponent.init(appComponent).inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.eventsQueue, ::onEvent)
        registerButtonClickListeners()
        registerEmailEditTextListener()
        registerPasswordEditTexListener()
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
        with(binding) {
            val loginState = viewModel.loginFormState
            loginState.emailError?.let {
                editTextEmail.error = getString(it)
            }
            loginState.passwordError?.let {
                editTextPassword.error = getString(it)
            }
            // Выставим доступность кнопки согласно валидности данных
            setEnableLoginButton(loginState.isDataValid)
        }
    }

    private fun registerButtonClickListeners() {
        val navController = findNavController(this)
        with(binding) {
            toolBar.setNavigationOnClickListener {
                navController.navigate(R.id.loginFragmentPop)
            }
            buttonGoToRegister.setOnClickListener {
                navController.navigate(R.id.toRegisterFragment)
            }
            buttonLogin.setOnClickListener {
                viewModel.onLoginClicked(
                    editTextEmail.text.toString(),
                    editTextPassword.text.toString()
                )
            }
        }
    }

    private fun registerEmailEditTextListener() {
        binding.editTextEmail.doAfterTextChanged {
            onLoginDataChanged()
        }
    }

    private fun registerPasswordEditTexListener() {
        with(binding) {
            editTextPassword.setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        onLoginDataChanged()
                }
                false
            }
            editTextPassword.doAfterTextChanged {
                onLoginDataChanged()
            }
        }
    }

    private fun onLoginDataChanged() {
        with(binding) {
            viewModel.onLoginDataChanged(
                editTextEmail.text.toString(),
                editTextPassword.text.toString(),
            )
        }
    }

    private fun setEnableLoginButton(state: Boolean) {
        binding.buttonLogin.isEnabled = state
    }
}
