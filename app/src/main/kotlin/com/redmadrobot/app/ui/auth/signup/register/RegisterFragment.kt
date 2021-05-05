package com.redmadrobot.app.ui.auth.signup.register

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
import com.redmadrobot.app.databinding.RegisterFragmentBinding
import com.redmadrobot.app.di.auth.register.RegisterComponent
import com.redmadrobot.app.ui.base.fragment.BaseFragment
import com.redmadrobot.extensions.lifecycle.Event
import com.redmadrobot.extensions.lifecycle.observe
import javax.inject.Inject

class RegisterFragment : BaseFragment(R.layout.register_fragment) {
    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: RegisterViewModel by viewModels { viewModelFactory }

    private var _binding: RegisterFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initDagger()
    }

    private fun initDagger() {
        RegisterComponent.init(appComponent).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = RegisterFragmentBinding.inflate(inflater, container, false)

        observe(viewModel.eventsQueue, ::onEvent)
        registerButtonClickListeners()
        registerEmailEditTexListener()
        registerPasswordEditTexListener()

        return binding.root
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
            binding.editTextEmail.error = getString(it)
        }
        registerFormState.passwordError?.let {
            binding.editTextPassword.error = getString(it)
        }

        // Выставим доступность кнопки согласно валидности данных
        setEnableRegisterButton(registerFormState.isDataValid)
    }

    private fun registerButtonClickListeners() {
        val navController = findNavController(this)
        binding.buttonGoToLogin.setOnClickListener {
            navController.navigate(R.id.toLoginFragment)
        }
        binding.buttonRegister.setOnClickListener {
            viewModel.onRegisterClicked(binding.editTextEmail.text.toString(), binding.editTextPassword.text.toString())
        }
        binding.toolBar.setNavigationOnClickListener {
            navController.navigate(R.id.registerFragmentPop)
        }
    }

    private fun registerPasswordEditTexListener() {
        binding.editTextPassword.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE ->
                    onRegisterDataChanged()
            }
            false
        }
        binding.editTextPassword.doAfterTextChanged {
            onRegisterDataChanged()
        }
    }

    private fun registerEmailEditTexListener() {
        binding.editTextEmail.doAfterTextChanged {
            onRegisterDataChanged()
        }
    }

    private fun onRegisterDataChanged() {
        viewModel.onRegisterDataChanged(
            binding.editTextEmail.text.toString(),
            binding.editTextPassword.text.toString()
        )
    }

    private fun setEnableRegisterButton(state: Boolean) {
        binding.buttonRegister.isEnabled = state
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
