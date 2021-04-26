package com.redmadrobot.app.ui.auth.signin

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
import com.redmadrobot.app.utils.validate.isValidPassword

class SignInFragment : BaseFragment(R.layout.sign_in_fragment) {
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

        registerButtonClickListeners(view)
        registerEmailEditTextListener(view)
        registerPasswordEditTexListener(view)

        return view
    }

    private fun registerButtonClickListeners(view: View) {
        val navController = findNavController(this)
        view.findViewById<Button>(R.id.btn_go_to_register).setOnClickListener {
            navController.navigate(R.id.action_signInFragment_to_signUpFirstFragment)
        }

        view.findViewById<Button>(R.id.btn_go_next).setOnClickListener {
            navController.navigate(R.id.action_signInFragment_to_doneFragment)
        }

        view.findViewById<ImageButton>(R.id.btn_back).setOnClickListener {
            navController.navigate(R.id.action_signInFragment_pop)
        }
    }

    private fun registerEmailEditTextListener(view: View) {
        registerEditTextListener(view.findViewById<EditText>(R.id.editTextTextEmailAddress), view)
    }

    private fun registerPasswordEditTexListener(view: View) {
        registerEditTextListener(view.findViewById<EditText>(R.id.editTextTextPassword), view)
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
        val email = view.findViewById<EditText>(R.id.editTextTextEmailAddress).text.toString()
        val password = view.findViewById<EditText>(R.id.editTextTextPassword).text.toString()

        val goNextBtn = view.findViewById<Button>(R.id.btn_go_next)
        goNextBtn.isEnabled = isValidEmail(email) && isValidPassword(password)

        // Костыль. Можно ли тут поменять тему у кнопки?
        if (goNextBtn.isEnabled) {
            goNextBtn.backgroundTintList = ContextCompat.getColorStateList(view.context, R.color.orange)
        } else {
            goNextBtn.backgroundTintList = ContextCompat.getColorStateList(view.context, R.color.light_grey_blue)
        }
    }
}
