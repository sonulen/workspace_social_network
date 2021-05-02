package com.redmadrobot.app.ui.auth.signup.register

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.redmadrobot.domain.usecases.signup.RegisterUseCase
import com.redmadrobot.domain.util.AuthValidatorImpl

class SignUpFirstFragment : BaseFragment(R.layout.sign_up_first) {
    private lateinit var signUpFirstViewModel: SignUpFirstViewModel
    private lateinit var password: EditText
    private lateinit var email: EditText
    private lateinit var nickname: EditText

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // TODO Решить это все через DI
        val preferences = this.requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
        val authRepository = AuthRepositoryImpl(preferences)
        val registerUseCase = RegisterUseCase(authRepository, AuthValidatorImpl())
        signUpFirstViewModel = SignUpFirstViewModel(registerUseCase)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = inflater.inflate(
            R.layout.sign_up_first,
            container,
            false
        )

        password = view.findViewById<EditText>(R.id.editTextTextPassword)
        email = view.findViewById<EditText>(R.id.editTextTextEmailAddress)
        nickname = view.findViewById<EditText>(R.id.editTextTextNickName)

        observeLiveData(view)
        registerButtonClickListeners(view)
        registerNickNameEditTextListener()
        registerEmailEditTexListener()
        registerPasswordEditTexListener()

        return view
    }

    private fun observeLiveData(view: View) {
        signUpFirstViewModel.signUpFormState.observe(
            viewLifecycleOwner,
            { registerState ->
                // Выставим доступность кнопки согласно валидности данных
                setEnableNextBtn(view, registerState.isDataValid)

                registerState.emailError?.let {
                    email.error = getString(it)
                }
                registerState.passwordError?.let {
                    password.error = getString(it)
                }
                registerState.nicknameError?.let {
                    nickname.error = getString(it)
                }
            }
        )
    }

    private fun registerButtonClickListeners(view: View) {
        val navController = findNavController(this)
        view.findViewById<Button>(R.id.btn_go_to_sign_in).setOnClickListener {
            navController.navigate(R.id.action_signUpFirstFragment_to_signInFragment)
        }

        view.findViewById<Button>(R.id.btn_go_next).setOnClickListener {
            signUpFirstViewModel.onRegisterClicked(email.text.toString(), password.text.toString())
            navController.navigate(R.id.action_signUpFirstFragment_to_signUpSecondFragment)
        }

        view.findViewById<ImageButton>(R.id.btn_back).setOnClickListener {
            navController.navigate(R.id.action_signUpFirstFragment_pop)
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

    private fun registerNickNameEditTextListener() {
        nickname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                onRegisterDataChanged()
            }

            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int,
            ) = Unit

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int,
            ) = Unit
        })
    }

    private fun onRegisterDataChanged() {
        signUpFirstViewModel.onRegisterDataChanged(
            email.text.toString(),
            password.text.toString()
        )
    }

    private fun setEnableNextBtn(view: View, state: Boolean) {
        val goNextBtn = view.findViewById<Button>(R.id.btn_go_next)
        goNextBtn.isEnabled = state
    }
}
