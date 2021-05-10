package com.redmadrobot.app.ui.auth.signup.register

import android.content.Context
import android.os.Bundle
import android.view.View
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
import com.redmadrobot.extensions.viewbinding.viewBinding
import javax.inject.Inject

class RegisterFragment : BaseFragment(R.layout.register_fragment) {
    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: RegisterViewModel by viewModels { viewModelFactory }

    private val binding: RegisterFragmentBinding by viewBinding()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initDagger()
    }

    private fun initDagger() {
        RegisterComponent.init(appComponent).inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.eventsQueue, ::onEvent)
        registerButtonClickListeners()
        registerEmailEditTexListener()
        registerPasswordEditTexListener()
    }

    override fun onEvent(event: Event) {
        super.onEvent(event)
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
        with(binding) {
            val registerFormState = viewModel.registerFormState

            registerFormState.emailError?.let {
                editTextEmail.error = getString(it)
            }
            registerFormState.passwordError?.let {
                editTextPassword.error = getString(it)
            }
            // Выставим доступность кнопки согласно валидности данных
            setEnableRegisterButton(registerFormState.isDataValid)
        }
    }

    private fun registerButtonClickListeners() {
        val navController = findNavController(this)
        with(binding) {
            buttonGoToLogin.setOnClickListener {
                navController.navigate(R.id.toLoginFragment)
            }
            buttonRegister.setOnClickListener {
                viewModel.onRegisterClicked(editTextEmail.text.toString(), editTextPassword.text.toString())
            }
            toolBar.setNavigationOnClickListener {
                navController.navigate(R.id.registerFragmentPop)
            }
        }
    }

    private fun registerPasswordEditTexListener() {
        with(binding) {
            editTextPassword.setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        onRegisterDataChanged()
                }
                false
            }
            editTextPassword.doAfterTextChanged {
                onRegisterDataChanged()
            }
        }
    }

    private fun registerEmailEditTexListener() {
        binding.editTextEmail.doAfterTextChanged {
            onRegisterDataChanged()
        }
    }

    private fun onRegisterDataChanged() {
        with(binding) {
            viewModel.onRegisterDataChanged(
                editTextEmail.text.toString(),
                editTextPassword.text.toString()
            )
        }
    }

    private fun setEnableRegisterButton(state: Boolean) {
        binding.buttonRegister.isEnabled = state
    }
}
