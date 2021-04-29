package com.redmadrobot.app.ui.auth.signup

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.redmadrobot.app.R
import com.redmadrobot.app.ui.base.fragment.BaseFragment

class SignUpSecondFragment : BaseFragment(R.layout.sign_up_second) {
    private val signUpViewModel = SignUpSecondViewModel()
    private lateinit var name: EditText
    private lateinit var surname: EditText
    private lateinit var birthDay: EditText

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

        name = view.findViewById<EditText>(R.id.editTextTextName)
        surname = view.findViewById<EditText>(R.id.editTextTextSurname)
        birthDay = view.findViewById<EditText>(R.id.editTextTextBirthDay)

        observeLiveData(view)
        registerButtonClickListeners(view)
        registerNameEditTextListener()
        registerSurnameEditTexListener()
        registerBirthDayEditTexListener(view)

        return view
    }

    private fun observeLiveData(view: View) {
        signUpViewModel.signUpFormState.observe(
            viewLifecycleOwner,
            { registerState ->
                // Выставим доступность кнопки согласно валидности данных
                setEnableNextBtn(view, registerState.isDataValid)
                registerState.nameError?.let {
                    name.error = getString(it)
                }
                registerState.surnameError?.let {
                    surname.error = getString(it)
                }
            }
        )
    }

    private fun registerButtonClickListeners(view: View) {
        val navController = findNavController(this)
        view.findViewById<Button>(R.id.btn_register).setOnClickListener {
            signUpViewModel.login(name.text.toString(), surname.text.toString(), birthDay.text.toString())
            navController.navigate(R.id.action_signUpSecondFragment_to_doneFragment)
        }

        view.findViewById<ImageButton>(R.id.btn_back).setOnClickListener {
            navController.navigate(R.id.action_signUpSecondFragment_pop)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun registerBirthDayEditTexListener(view: View) {
        birthDay.inputType = InputType.TYPE_NULL

        birthDay.setOnClickListener {
            val calendar = Calendar.getInstance()
            val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
            val month = calendar.get(Calendar.MONTH)
            val year: Int = calendar.get(Calendar.YEAR)

            val picker = DatePickerDialog(
                view.context,
                { _, yearIn, monthOfYear, dayOfMonth ->
                    birthDay.setText(dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + yearIn)
                    onRegisterDataChanged()
                },
                year,
                month,
                day
            )

            picker.show()
        }
    }

    private fun registerSurnameEditTexListener() {
        surname.doAfterTextChanged {
            onRegisterDataChanged()
        }
    }

    private fun registerNameEditTextListener() {
        name.doAfterTextChanged {
            onRegisterDataChanged()
        }
    }

    private fun onRegisterDataChanged() {
        signUpViewModel.registerDataChanged(
            name.text.toString(),
            surname.text.toString(),
            birthDay.text.toString()
        )
    }

    private fun setEnableNextBtn(view: View, state: Boolean) {
        val goNextBtn = view.findViewById<Button>(R.id.btn_register)
        goNextBtn.isEnabled = state

        // Костыль. Можно ли тут поменять тему у кнопки?
        if (goNextBtn.isEnabled) {
            goNextBtn.backgroundTintList = ContextCompat.getColorStateList(view.context, R.color.orange)
        } else {
            goNextBtn.backgroundTintList = ContextCompat.getColorStateList(view.context, R.color.light_grey_blue)
        }
    }
}
