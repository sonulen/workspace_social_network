package com.redmadrobot.app.ui.auth.signup

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
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.redmadrobot.app.R
import com.redmadrobot.app.ui.base.fragment.BaseFragment

class SignUpFirstFragment : BaseFragment(R.layout.sign_up_first) {
    private val signUpViewModel = SignUpFirstViewModel()
    private lateinit var password: EditText
    private lateinit var email: EditText
    private lateinit var nickname: EditText

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
        signUpViewModel.registerFormState.observe(
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
            signUpViewModel.update(nickname.text.toString(), email.text.toString(), password.text.toString())
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
                    signUpViewModel.update(
                        nickname.text.toString(),
                        email.text.toString(),
                        password.text.toString()
                    )
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
        signUpViewModel.onRegisterDataChanged(
            nickname.text.toString(),
            email.text.toString(),
            password.text.toString()
        )
    }

    private fun setEnableNextBtn(view: View, state: Boolean) {
        val goNextBtn = view.findViewById<Button>(R.id.btn_go_next)
        goNextBtn.isEnabled = state

        // Костыль. Можно ли тут поменять тему у кнопки?
        if (goNextBtn.isEnabled) {
            goNextBtn.backgroundTintList = ContextCompat.getColorStateList(view.context, R.color.orange)
        } else {
            goNextBtn.backgroundTintList = ContextCompat.getColorStateList(view.context, R.color.light_grey_blue)
        }
    }
}
