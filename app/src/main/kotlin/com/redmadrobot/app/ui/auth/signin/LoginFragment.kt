package com.redmadrobot.app.ui.auth.signin

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import javax.inject.Inject

class LoginFragment : BaseFragment(R.layout.login_fragment) {
    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: LoginViewModel by viewModels { viewModelFactory }

    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initDagger()
    }

    private fun initDagger() {
        LoginComponent.init(appComponent).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = LoginFragmentBinding.inflate(inflater, container, false)

        observe(viewModel.eventsQueue, ::onEvent)

        registerButtonClickListeners()
        registerEmailEditTextListener()
        registerPasswordEditTexListener()

        return binding.root
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
            binding.editTextEmail.error = getString(it)
        }
        loginState.passwordError?.let {
            binding.editTextPassword.error = getString(it)
        }
        // Выставим доступность кнопки согласно валидности данных
        setEnableLoginButton(loginState.isDataValid)
    }

    private fun registerButtonClickListeners() {
        val navController = findNavController(this)

        binding.toolBar.setNavigationOnClickListener {
            navController.navigate(R.id.loginFragmentPop)
        }

        binding.buttonGoToRegister.setOnClickListener {
            navController.navigate(R.id.toRegisterFragment)
        }

        binding.buttonLogin.setOnClickListener {
            viewModel.onLoginClicked(binding.editTextEmail.text.toString(), binding.editTextPassword.text.toString())
        }
    }

    private fun registerEmailEditTextListener() {
        binding.editTextEmail.doAfterTextChanged {
            onLoginDataChanged()
        }
    }

    private fun registerPasswordEditTexListener() {
        binding.editTextPassword.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE ->
                    onLoginDataChanged()
            }
            false
        }

        binding.editTextPassword.doAfterTextChanged {
            onLoginDataChanged()
        }
    }

    private fun onLoginDataChanged() {
        viewModel.onLoginDataChanged(
            binding.editTextEmail.text.toString(),
            binding.editTextPassword.text.toString()
        )
    }

    private fun setEnableLoginButton(state: Boolean) {
        binding.buttonLogin.isEnabled = state
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
