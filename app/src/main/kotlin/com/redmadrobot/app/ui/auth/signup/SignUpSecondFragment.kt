package com.redmadrobot.app.ui.auth.signup

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.text.Editable
import android.text.InputType
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

class SignUpSecondFragment : BaseFragment(R.layout.sign_up_second) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = inflater.inflate(
            R.layout.sign_up_second,
            container,
            false
        )

        registerButtonClickListeners(view)
        registerNameEditTextListener(view)
        registerSurnameEditTexListener(view)
        registerBirthDayEditTexListener(view)

        return view
    }

    private fun registerButtonClickListeners(view: View) {
        val navController = findNavController(this)
        view.findViewById<Button>(R.id.btn_register).setOnClickListener {
            navController.navigate(R.id.action_signUpSecondFragment_to_doneFragment)
        }

        view.findViewById<ImageButton>(R.id.btn_back).setOnClickListener {
            navController.navigate(R.id.action_signUpSecondFragment_pop)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun registerBirthDayEditTexListener(view: View) {
        val eText = view.findViewById<EditText>(R.id.editTextTextBirthDay)

        eText.inputType = InputType.TYPE_NULL

        eText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
            val month = calendar.get(Calendar.MONTH)
            val year: Int = calendar.get(Calendar.YEAR)

            val picker = DatePickerDialog(
                view.context,
                { v, yearIn, monthOfYear, dayOfMonth ->
                    eText.setText(dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + yearIn)
                    checkAccessibilityGoNextBtn(view)
                },
                year,
                month,
                day
            )

            picker.show()
        }
    }

    private fun registerSurnameEditTexListener(view: View) {
        registerEditTextListener(view.findViewById<EditText>(R.id.editTextTextSurname), view)
    }

    private fun registerNameEditTextListener(view: View) {
        registerEditTextListener(view.findViewById<EditText>(R.id.editTextTextName), view)
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
        val name = view.findViewById<EditText>(R.id.editTextTextName).text.toString()
        val surname = view.findViewById<EditText>(R.id.editTextTextSurname).text.toString()
        val date = view.findViewById<EditText>(R.id.editTextTextBirthDay).text.toString()

        val goNextBtn = view.findViewById<Button>(R.id.btn_register)
        goNextBtn.isEnabled = name.isNotEmpty() && surname.isNotEmpty() && date.isNotEmpty()

        // Костыль. Можно ли тут поменять тему у кнопки?
        if (goNextBtn.isEnabled) {
            goNextBtn.backgroundTintList = ContextCompat.getColorStateList(view.context, R.color.orange)
        } else {
            goNextBtn.backgroundTintList = ContextCompat.getColorStateList(view.context, R.color.light_grey_blue)
        }
    }
}
