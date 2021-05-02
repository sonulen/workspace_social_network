package com.redmadrobot.app.ui.auth.signup.updateProfile

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
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
import com.redmadrobot.data.repository.AuthRepositoryImpl
import com.redmadrobot.domain.usecases.signup.ProfileUpdateUseCase
import com.redmadrobot.domain.util.AuthValidatorImpl

class UpdateProfileFragment : BaseFragment(R.layout.sign_up_second) {
    private lateinit var updateProfileViewModel: UpdateProfileViewModel
    private lateinit var name: EditText
    private lateinit var surname: EditText
    private lateinit var birthDay: EditText

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // TODO Решить это все через DI
        val preferences = this.requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
        val authRepository = AuthRepositoryImpl(preferences)
        val profileUpdateUseCase = ProfileUpdateUseCase(authRepository, AuthValidatorImpl())
        updateProfileViewModel = UpdateProfileViewModel(profileUpdateUseCase)
    }

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
        updateProfileViewModel.signUpFormState.observe(
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
            updateProfileViewModel.onUpdateProfileClicked(
                "nickname",
                name.text.toString(),
                surname.text.toString(),
                birthDay.text.toString()
            )
            navController.navigate(R.id.toDoneFragment)
        }

        view.findViewById<ImageButton>(R.id.btn_back).setOnClickListener {
            navController.navigate(R.id.updateProfileFragmentPop)
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
        updateProfileViewModel.registerDataChanged(
            "nickname",
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
