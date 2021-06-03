package com.redmadrobot.app.ui.auth.signup.updateProfile

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.datepicker.MaterialDatePicker
import com.redmadrobot.app.R
import com.redmadrobot.app.databinding.ProfileUpdateFragmentBinding
import com.redmadrobot.app.di.auth.register.UpdateProfileComponent
import com.redmadrobot.app.ui.LoadingDialogFragment
import com.redmadrobot.app.ui.base.events.EventShowDatePickerDialog
import com.redmadrobot.app.ui.base.fragment.BaseFragment
import com.redmadrobot.app.ui.base.viewmodel.ScreenState
import com.redmadrobot.extensions.lifecycle.Event
import com.redmadrobot.extensions.lifecycle.observe
import com.redmadrobot.extensions.viewbinding.viewBinding
import com.redmadrobot.inputmask.MaskedTextChangedListener.Companion.installOn
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class UpdateProfileFragment : BaseFragment(R.layout.profile_update_fragment) {
    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: UpdateProfileViewModel by viewModels { viewModelFactory }

    private val binding: ProfileUpdateFragmentBinding by viewBinding()
    private val args: UpdateProfileFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initDagger()
    }

    private fun initDagger() {
        UpdateProfileComponent.init(appComponent).inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.onEmailAndPasswordReceived(args.email, args.password)

        observe(viewModel.eventsQueue, ::onEvent)
        observe(viewModel.screenState, ::onScreenStateChange)
        observe(viewModel.nickname, ::renderNickname)
        observe(viewModel.nicknameError, ::renderNicknameError)
        observe(viewModel.name, ::renderName)
        observe(viewModel.nameError, ::renderNameError)
        observe(viewModel.surname, ::renderSurname)
        observe(viewModel.surnameError, ::renderSurnameError)
        observe(viewModel.birthDay, ::renderBirthDay)
        observe(viewModel.birthDayError, ::renderBirthDayError)
        observe(viewModel.isRegisterButtonEnabled, ::renderRegisterButton)

        registerButtonClickListeners()
        registerNicknameEditTextListener()
        registerNameEditTextListener()
        registerSurnameEditTexListener()
        registerBirthDayEditTexListener()
    }

    override fun onEvent(event: Event) {
        super.onEvent(event)
        when (event) {
            is EventShowDatePickerDialog -> showDatePicker()
        }
    }

    private fun showDatePicker() {
        val picker = MaterialDatePicker.Builder.datePicker()
            .build()

        picker.show(parentFragmentManager, picker.toString())

        picker.addOnPositiveButtonClickListener { dateTimeStampInMillis ->
            val dateTime =
                LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(dateTimeStampInMillis),
                    ZoneId.systemDefault()
                )
            val dateAsFormattedText: String = dateTime.format(
                DateTimeFormatter.ofPattern(getString(R.string.data_time_formatter_pattern))
            )
            binding.editTextBirthDay.setText(dateAsFormattedText)
        }
    }

    private fun onScreenStateChange(state: ScreenState) {
        when (state) {
            ScreenState.CONTENT,
            ScreenState.ERROR,
            -> {
                renderSpin(isVisible = false)
                binding.buttonRegister.isClickable = true
            }

            ScreenState.LOADING -> {
                renderSpin(isVisible = true)
                binding.buttonRegister.isClickable = false
            }
        }
    }

    private fun renderSpin(isVisible: Boolean) {
        if (isVisible) {
            LoadingDialogFragment().apply {
                isCancelable = false
            }.show(childFragmentManager, LoadingDialogFragment.TAG)
        } else {
            val fragment = childFragmentManager.findFragmentByTag(LoadingDialogFragment.TAG) as? DialogFragment
            fragment?.dismiss()
        }
    }

    private fun renderNickname(value: String) {
        binding.editTextNickname.setText(value)
    }

    private fun renderNicknameError(@StringRes stringId: Int?) {
        if (stringId != null) {
            binding.editTextNickname.error = getString(stringId)
        }
    }

    private fun renderName(value: String) {
        binding.editTextName.setText(value)
    }

    private fun renderNameError(@StringRes stringId: Int?) {
        if (stringId != null) {
            binding.editTextName.error = getString(stringId)
        }
    }

    private fun renderSurname(value: String) {
        binding.editTextSurname.setText(value)
    }

    private fun renderSurnameError(@StringRes stringId: Int?) {
        if (stringId != null) {
            binding.editTextSurname.error = getString(stringId)
        }
    }

    private fun renderBirthDay(value: String) {
        binding.editTextBirthDay.setText(value)
    }

    private fun renderBirthDayError(@StringRes stringId: Int?) {
        if (stringId != null) {
            binding.editTextBirthDay.error = getString(stringId)
        }
    }

    private fun renderRegisterButton(isEnabled: Boolean) {
        binding.buttonRegister.isEnabled = isEnabled
    }

    private fun registerButtonClickListeners() {
        with(binding) {
            buttonRegister.setOnClickListener {
                viewModel.onRegisterClicked(
                    nickname = editTextNickname.text.toString(),
                    name = editTextName.text.toString(),
                    surname = editTextSurname.text.toString(),
                    birthDay = editTextBirthDay.text.toString(),
                )
            }
            toolBar.setNavigationOnClickListener {
                viewModel.onBackClicked()
            }
            buttonShowDatepicker.setOnClickListener {
                viewModel.onShowDatePickerClicked()
            }
        }
    }

    private fun registerBirthDayEditTexListener() {
        installOn(
            binding.editTextBirthDay,
            getString(R.string.input_mask_date_pattern)
        )

        binding.editTextBirthDay.doAfterTextChanged {
            it?.let {
                viewModel.onBirthDayEntered(it.toString())
            }
        }
    }

    private fun registerNicknameEditTextListener() {
        binding.editTextNickname.doAfterTextChanged {
            it?.let {
                viewModel.onNicknameEntered(it.toString())
            }
        }
    }

    private fun registerSurnameEditTexListener() {
        binding.editTextSurname.doAfterTextChanged {
            it?.let {
                viewModel.onSurnameEntered(it.toString())
            }
        }
    }

    private fun registerNameEditTextListener() {
        binding.editTextName.doAfterTextChanged {
            it?.let {
                viewModel.onNameEntered(it.toString())
            }
        }
    }
}
