package com.redmadrobot.app.ui.auth.signin

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.annotation.StringRes
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.redmadrobot.app.R
import com.redmadrobot.app.databinding.LoginFragmentBinding
import com.redmadrobot.app.di.auth.login.LoginComponent
import com.redmadrobot.app.ui.LoadingDialogFragment
import com.redmadrobot.app.ui.base.fragment.BaseFragment
import com.redmadrobot.app.ui.base.viewmodel.ScreenState
import com.redmadrobot.app.utils.extension.setTextIfDifferent
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
        observe(viewModel.screenState, ::onScreenStateChange)
        observe(viewModel.email, ::renderEmail)
        observe(viewModel.emailError, ::renderEmailError)
        observe(viewModel.password, ::renderPassword)
        observe(viewModel.passwordError, ::renderPasswordError)
        observe(viewModel.isLoginButtonEnabled, ::renderLoginButton)

        registerButtonClickListeners()
        registerEmailEditTextListener()
        registerPasswordEditTexListener()
    }

    private fun onScreenStateChange(state: ScreenState) {
        when (state) {
            ScreenState.CONTENT,
            ScreenState.ERROR,
            -> {
                renderSpin(isVisible = false)
                binding.buttonLogin.isClickable = true
            }

            ScreenState.LOADING -> {
                renderSpin(isVisible = true)
                binding.buttonLogin.isClickable = false
            }
        }
    }

    private fun renderEmail(string: String) {
        binding.editTextEmail.setTextIfDifferent(string)
    }

    private fun renderEmailError(@StringRes stringId: Int?) {
        if (stringId != null) {
            binding.editTextEmail.error = getString(stringId)
        }
    }

    private fun renderPassword(string: String) {
        binding.editTextPassword.setTextIfDifferent(string)
    }

    private fun renderPasswordError(@StringRes stringId: Int?) {
        if (stringId != null) {
            binding.editTextPassword.error = getString(stringId)
        }
    }

    private fun renderLoginButton(isEnabled: Boolean) {
        binding.buttonLogin.isEnabled = isEnabled
    }

    private fun renderSpin(isVisible: Boolean) {
        if (isVisible) {
            LoadingDialogFragment().apply {
                isCancelable = false
            }.show(childFragmentManager, LoadingDialogFragment.TAG)
        } else {
            val fragment = childFragmentManager.findFragmentByTag(LoadingDialogFragment.TAG) as? DialogFragment
            fragment?.dismiss()
        }
    }

    private fun registerButtonClickListeners() {
        with(binding) {
            toolBar.setNavigationOnClickListener {
                viewModel.onBackClicked()
            }
            buttonGoToRegister.setOnClickListener {
                viewModel.onGoToRegisterClicked()
            }
            buttonLogin.setOnClickListener {
                viewModel.onLoginClicked()
            }
        }
    }

    private fun registerEmailEditTextListener() {
        binding.editTextEmail.doAfterTextChanged {
            it?.let {
                viewModel.onEmailEntered(it.toString())
            }
        }
    }

    private fun registerPasswordEditTexListener() {
        with(binding) {
            editTextPassword.setOnEditorActionListener { textView, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        viewModel.onPasswordEntered(textView.text.toString())
                }
                false
            }
            editTextPassword.doAfterTextChanged {
                it?.let {
                    viewModel.onPasswordEntered(it.toString())
                }
            }
        }
    }
}
