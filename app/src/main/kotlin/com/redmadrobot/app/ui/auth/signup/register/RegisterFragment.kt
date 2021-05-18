package com.redmadrobot.app.ui.auth.signup.register

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.annotation.StringRes
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.redmadrobot.app.R
import com.redmadrobot.app.databinding.RegisterFragmentBinding
import com.redmadrobot.app.di.auth.register.RegisterComponent
import com.redmadrobot.app.ui.base.fragment.BaseFragment
import com.redmadrobot.app.ui.base.viewmodel.ScreenState
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
        observe(viewModel.screenState, ::onScreenStateChange)
        observe(viewModel.emailError, ::renderEmailError)
        observe(viewModel.passwordError, ::renderPasswordError)
        observe(viewModel.isGoNextButtonEnabled, ::renderGoNextButton)

        registerButtonClickListeners()
        registerEmailEditTextListener()
        registerPasswordEditTexListener()
    }

    private fun onScreenStateChange(state: ScreenState) {
        when (state) {
            ScreenState.CONTENT,
            ScreenState.ERROR,
            -> binding.buttonGoNext.isClickable = true
            ScreenState.LOADING -> binding.buttonGoNext.isClickable = false
        }
    }

    private fun renderEmailError(@StringRes stringId: Int?) {
        if (stringId != null) {
            binding.editTextEmail.error = getString(stringId)
        }
    }

    private fun renderPasswordError(@StringRes stringId: Int?) {
        if (stringId != null) {
            binding.editTextPassword.error = getString(stringId)
        }
    }

    private fun renderGoNextButton(isEnabled: Boolean) {
        binding.buttonGoNext.isEnabled = isEnabled
    }

    private fun registerButtonClickListeners() {
        with(binding) {
            buttonGoToLogin.setOnClickListener {
                viewModel.onGoToLoginClicked()
            }
            buttonGoNext.setOnClickListener {
                viewModel.onGoNextClicked(
                    editTextEmail.text.toString(),
                    editTextPassword.text.toString()
                )
            }
            toolBar.setNavigationOnClickListener {
                viewModel.onBackClicked()
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

    private fun registerEmailEditTextListener() {
        binding.editTextEmail.doAfterTextChanged {
            it?.let {
                viewModel.onEmailEntered(it.toString())
            }
        }
    }
}
