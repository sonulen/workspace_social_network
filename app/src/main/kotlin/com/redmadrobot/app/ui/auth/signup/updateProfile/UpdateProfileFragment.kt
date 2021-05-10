package com.redmadrobot.app.ui.auth.signup.updateProfile

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.redmadrobot.app.R
import com.redmadrobot.app.databinding.ProfileUpdateFragmentBinding
import com.redmadrobot.app.di.auth.register.UpdateProfileComponent
import com.redmadrobot.app.ui.base.fragment.BaseFragment
import com.redmadrobot.extensions.lifecycle.Event
import com.redmadrobot.extensions.lifecycle.observe
import com.redmadrobot.extensions.viewbinding.viewBinding
import javax.inject.Inject

class UpdateProfileFragment : BaseFragment(R.layout.profile_update_fragment) {
    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: UpdateProfileViewModel by viewModels { viewModelFactory }

    private val binding: ProfileUpdateFragmentBinding by viewBinding()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initDagger()
    }

    private fun initDagger() {
        UpdateProfileComponent.init(appComponent).inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.eventsQueue, ::onEvent)
        registerButtonClickListeners()
        registerNicknameEditTextListener()
        registerNameEditTextListener()
        registerSurnameEditTexListener()
        registerBirthDayEditTexListener()
    }

    override fun onEvent(event: Event) {
        super.onEvent(event)
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
        with(binding) {
            val updateProfileFormState = viewModel.updateProfileFormState

            updateProfileFormState.nicknameError?.let {
                editTextNickname.error = getString(it)
            }

            updateProfileFormState.nameError?.let {
                editTextName.error = getString(it)
            }
            updateProfileFormState.surnameError?.let {
                editTextSurname.error = getString(it)
            }

            updateProfileFormState.birthDayError?.let {
                editTextBirthDay.error = getString(it)
            } ?: run {
                editTextBirthDay.error = null
            }

            // Выставим доступность кнопки согласно валидности данных
            setEnableUpdateProfileButton(updateProfileFormState.isDataValid)
        }
    }

    private fun registerButtonClickListeners() {
        val navController = findNavController(this)
        with(binding) {
            buttonUpdateProfile.setOnClickListener {
                viewModel.onUpdateProfileClicked(
                    nickname = editTextNickname.text.toString(),
                    name = editTextName.text.toString(),
                    surname = editTextSurname.text.toString(),
                    birthDay = editTextBirthDay.text.toString(),
                )
            }
            toolBar.setNavigationOnClickListener {
                navController.navigate(R.id.updateProfileFragmentPop)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun registerBirthDayEditTexListener() {
        with(binding) {
            editTextBirthDay.inputType = InputType.TYPE_NULL

            editTextBirthDay.setOnClickListener {
                val picker =
                    MaterialDatePicker.Builder.datePicker().setTheme(R.style.Widget_Workplaces_DatePicker).build()
                picker.show(parentFragmentManager, picker.toString())

                picker.addOnPositiveButtonClickListener { _ ->
                    editTextBirthDay.setText(picker.headerText)
                    onUpdateProfileDataChanged()
                }
            }
        }
    }

    private fun registerNicknameEditTextListener() {
        binding.editTextNickname.doAfterTextChanged {
            onUpdateProfileDataChanged()
        }
    }

    private fun registerSurnameEditTexListener() {
        binding.editTextSurname.doAfterTextChanged {
            onUpdateProfileDataChanged()
        }
    }

    private fun registerNameEditTextListener() {
        binding.editTextName.doAfterTextChanged {
            onUpdateProfileDataChanged()
        }
    }

    private fun onUpdateProfileDataChanged() {
        with(binding) {
            viewModel.onRegisterDataChanged(
                nickname = editTextNickname.text.toString(),
                name = editTextName.text.toString(),
                surname = editTextSurname.text.toString(),
                birthDay = editTextBirthDay.text.toString(),
            )
        }
    }

    private fun setEnableUpdateProfileButton(state: Boolean) {
        binding.buttonUpdateProfile.isEnabled = state
    }
}
