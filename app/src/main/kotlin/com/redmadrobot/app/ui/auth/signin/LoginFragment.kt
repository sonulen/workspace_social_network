package com.redmadrobot.app.ui.auth.signin

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.redmadrobot.app.R
import com.redmadrobot.app.ui.base.fragment.BaseFragment
import com.redmadrobot.data.repository.AuthRepositoryImpl
import com.redmadrobot.domain.usecases.login.LoginUseCase

class LoginFragment : BaseFragment(R.layout.sign_in_fragment) {
    private lateinit var viewModel: LoginViewModel
    private lateinit var password: EditText
    private lateinit var email: EditText

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
        val view = inflater.inflate(
            R.layout.sign_in_fragment,
            container,
            false
        )
        password = view.findViewById<EditText>(R.id.editTextTextPassword)
        email = view.findViewById<EditText>(R.id.editTextTextEmailAddress)

        observeLiveData(view)
        registerButtonClickListeners(view)
        registerEmailEditTextListener()
        registerPasswordEditTexListener()

        return view
    }

    private fun observeLiveData(view: View) {
        viewModel.loginFormState.observe(
            viewLifecycleOwner,
            { loginState ->
                // Выставим доступность кнопки согласно валидности данных
                setEnableNextBtn(view, loginState.isDataValid)

                loginState.emailError?.let {
                    email.error = getString(it)
                }

                loginState.passwordError?.let {
                    password.error = getString(it)
                }
            }
        )
    }

    private fun registerButtonClickListeners(view: View) {
        val navController = findNavController(this)
        view.findViewById<Button>(R.id.btn_go_to_register).setOnClickListener {
            navController.navigate(R.id.action_signInFragment_to_signUpFirstFragment)
        }

        view.findViewById<Button>(R.id.btn_go_next).setOnClickListener {
            // TODO: login должен быть через observer на signInViewModel.loginResult
            viewModel.onLoginClicked(email.text.toString(), password.text.toString())
            navController.navigate(R.id.action_signInFragment_to_doneFragment)
        }

        view.findViewById<ImageButton>(R.id.btn_back).setOnClickListener {
            navController.navigate(R.id.action_signInFragment_pop)
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
                    viewModel.onLoginClicked(
                        email.text.toString(),
                        password.text.toString()
                    )
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

    private fun setEnableNextBtn(view: View, state: Boolean) {
        val goNextBtn = view.findViewById<Button>(R.id.btn_go_next)
        goNextBtn.isEnabled = state
    }
}
