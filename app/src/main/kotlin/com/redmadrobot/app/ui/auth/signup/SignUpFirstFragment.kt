package com.redmadrobot.app.ui.auth.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.redmadrobot.app.R
import com.redmadrobot.app.ui.base.fragment.BaseFragment
import com.redmadrobot.app.utils.validate.isValidEmail
import com.redmadrobot.app.utils.validate.isValidNickname
import com.redmadrobot.app.utils.validate.isValidPassword

class SignUpFirstFragment : BaseFragment(R.layout.sign_up_first) {
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

        registerButtonClickListeners(view)
        registerNickNameEditTextListener(view)
        registerEmailEditTexListener(view)
        registerPasswordEditTexListener(view)

        return view
    }

    private fun registerButtonClickListeners(view: View) {
        val navController = findNavController(this)
        view.findViewById<Button>(R.id.btn_go_to_sign_in).setOnClickListener {
            navController.navigate(R.id.action_signUpFirstFragment_to_signInFragment)
        }

        view.findViewById<Button>(R.id.btn_go_next).setOnClickListener {
            navController.navigate(R.id.action_signUpFirstFragment_to_signUpSecondFragment)
        }

        view.findViewById<ImageButton>(R.id.btn_back).setOnClickListener {
            navController.navigate(R.id.action_signUpFirstFragment_pop)
        }
    }

    private fun registerPasswordEditTexListener(view: View) {
        registerEditTextListener(view.findViewById<EditText>(R.id.editTextTextPassword), view)
    }

    private fun registerEmailEditTexListener(view: View) {
        registerEditTextListener(view.findViewById<EditText>(R.id.editTextTextEmailAddress), view)
    }

    private fun registerNickNameEditTextListener(view: View) {
        registerEditTextListener(view.findViewById<EditText>(R.id.editTextTextNickName), view)
    }

    private fun registerEditTextListener(editText: EditText, view: View) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) = Unit
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
            ) {
                checkAccessibilityGoNextBtn(view)
            }
        })
    }

    private fun checkAccessibilityGoNextBtn(view: View) {
        val nickname = view.findViewById<EditText>(R.id.editTextTextNickName).text.toString()
        val email = view.findViewById<EditText>(R.id.editTextTextEmailAddress).text.toString()
        val password = view.findViewById<EditText>(R.id.editTextTextPassword).text.toString()

        val goNextBtn = view.findViewById<Button>(R.id.btn_go_next)
        goNextBtn.isEnabled = isValidNickname(nickname) && isValidEmail(email) && isValidPassword(password)

        // Костыль. Можно ли тут поменять тему у кнопки?
        if (goNextBtn.isEnabled) {
            goNextBtn.backgroundTintList = ContextCompat.getColorStateList(view.context, R.color.orange)
        } else {
            goNextBtn.backgroundTintList = ContextCompat.getColorStateList(view.context, R.color.light_grey_blue)
        }
    }
}
