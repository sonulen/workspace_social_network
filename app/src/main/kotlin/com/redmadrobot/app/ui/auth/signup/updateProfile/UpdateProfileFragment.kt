package com.redmadrobot.app.ui.auth.signup.updateProfile

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.datepicker.MaterialDatePicker
import com.redmadrobot.app.R
import com.redmadrobot.app.di.auth.register.UpdateProfileComponent
import com.redmadrobot.app.ui.base.fragment.BaseFragment
import com.redmadrobot.extensions.lifecycle.Event
import com.redmadrobot.extensions.lifecycle.observe
import javax.inject.Inject

class UpdateProfileFragment : BaseFragment(R.layout.profile_update_fragment) {
    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: UpdateProfileViewModel by viewModels { viewModelFactory }

    private lateinit var nickname: EditText
    private lateinit var name: EditText
    private lateinit var surname: EditText
    private lateinit var birthDay: EditText
    private lateinit var updateProfileButton: Button

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initDagger()
    }

    private fun initDagger() {
        UpdateProfileComponent.init(appComponent).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = inflater.inflate(R.layout.profile_update_fragment, container, false)

        nickname = view.findViewById(R.id.edit_text_nickname)
        name = view.findViewById(R.id.edit_text_name)
        surname = view.findViewById(R.id.edit_text_surname)
        birthDay = view.findViewById(R.id.edit_text_birth_day)
        updateProfileButton = view.findViewById(R.id.button_update_profile)

        observe(viewModel.eventsQueue, ::onEvent)
        registerButtonClickListeners(view)
        registerNicknameEditTextListener()
        registerNameEditTextListener()
        registerSurnameEditTexListener()
        registerBirthDayEditTexListener()

        return view
    }

    private fun onEvent(event: Event) {
        val navController = findNavController(this)

        when (event) {
            is EventUpdateProfileSuccess -> navController.navigate(R.id.toDoneFragment)

            is EventUpdateProfileFailed -> {
                // no-op //
            }

            is EventUpdateProfileFormStateChanged -> onLoginFormStateChange()
        }
    }

    private fun onLoginFormStateChange() {
        val updateProfileFormState = viewModel.updateProfileFormState

        updateProfileFormState.nicknameError?.let {
            nickname.error = getString(it)
        }

        updateProfileFormState.nameError?.let {
            name.error = getString(it)
        }
        updateProfileFormState.surnameError?.let {
            surname.error = getString(it)
        }

        updateProfileFormState.birthDayError?.let {
            birthDay.error = getString(it)
        } ?: run {
            birthDay.error = null
        }

        // Выставим доступность кнопки согласно валидности данных
        setEnableUpdateProfileButton(updateProfileFormState.isDataValid)
    }

    private fun registerButtonClickListeners(view: View) {
        val navController = findNavController(this)
        updateProfileButton.setOnClickListener {
            viewModel.onUpdateProfileClicked(
                nickname = nickname.text.toString(),
                name = name.text.toString(),
                surname = surname.text.toString(),
                birthDay = birthDay.text.toString(),
            )
        }
        view.findViewById<MaterialToolbar>(R.id.tool_bar).setNavigationOnClickListener {
            navController.navigate(R.id.updateProfileFragmentPop)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun registerBirthDayEditTexListener() {
        birthDay.inputType = InputType.TYPE_NULL

        birthDay.setOnClickListener {
            val picker = MaterialDatePicker.Builder.datePicker().build()
            picker.show(parentFragmentManager, picker.toString())

            picker.addOnPositiveButtonClickListener { _ ->
                birthDay.setText(picker.headerText)
                onUpdateProfileDataChanged()
            }
        }
    }

    private fun registerNicknameEditTextListener() {
        nickname.doAfterTextChanged {
            onUpdateProfileDataChanged()
        }
    }

    private fun registerSurnameEditTexListener() {
        surname.doAfterTextChanged {
            onUpdateProfileDataChanged()
        }
    }

    private fun registerNameEditTextListener() {
        name.doAfterTextChanged {
            onUpdateProfileDataChanged()
        }
    }

    private fun onUpdateProfileDataChanged() {
        viewModel.onRegisterDataChanged(
            nickname = nickname.text.toString(),
            name = name.text.toString(),
            surname = surname.text.toString(),
            birthDay = birthDay.text.toString(),
        )
    }

    private fun setEnableUpdateProfileButton(state: Boolean) {
        updateProfileButton.isEnabled = state
    }
}
