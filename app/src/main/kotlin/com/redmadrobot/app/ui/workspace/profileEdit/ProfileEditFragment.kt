package com.redmadrobot.app.ui.workspace.profileEdit

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.datepicker.MaterialDatePicker
import com.redmadrobot.app.R
import com.redmadrobot.app.databinding.ProfileEditFragmentBinding
import com.redmadrobot.app.di.workspace.profileEdit.ProfileEditComponent
import com.redmadrobot.app.ui.LoadingDialogFragment
import com.redmadrobot.app.ui.base.events.EventShowDatePickerDialog
import com.redmadrobot.app.ui.base.fragment.BaseFragment
import com.redmadrobot.app.ui.base.viewmodel.ScreenState
import com.redmadrobot.app.ui.workspace.profile.UserDataProfileViewState
import com.redmadrobot.extensions.lifecycle.Event
import com.redmadrobot.extensions.lifecycle.observe
import com.redmadrobot.extensions.viewbinding.viewBinding
import com.redmadrobot.inputmask.MaskedTextChangedListener
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class ProfileEditFragment : BaseFragment(R.layout.profile_edit_fragment) {
    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: ProfileEditViewModel by viewModels { viewModelFactory }
    private val binding: ProfileEditFragmentBinding by viewBinding()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initDagger()
    }

    private fun initDagger() {
        ProfileEditComponent.init(appComponent).inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(viewModel.eventsQueue, ::onEvent)
        observe(viewModel.screenState, ::onScreenStateChange)
        observe(viewModel.userData, ::renderUserData)
        observe(viewModel.nicknameError, ::renderNicknameError)
        observe(viewModel.nameError, ::renderNameError)
        observe(viewModel.surnameError, ::renderSurnameError)
        observe(viewModel.birthDayError, ::renderBirthDayError)
        observe(viewModel.isSaveButtonEnable, ::renderSaveButton)

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
                binding.buttonSave.isClickable = true
            }

            ScreenState.LOADING -> {
                renderSpin(isVisible = true)
                binding.buttonSave.isClickable = false
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

    private fun renderUserData(userData: UserDataProfileViewState) {
        with(binding) {
            editTextNickname.setText(userData.nickname)
            editTextName.setText(userData.name)
            editTextSurname.setText(userData.surname)
            editTextBirthDay.setText(userData.birthDay)
        }
    }

    private fun renderNicknameError(@StringRes stringId: Int?) {
        if (stringId != null) {
            binding.editTextNickname.error = getString(stringId)
        }
    }

    private fun renderNameError(@StringRes stringId: Int?) {
        if (stringId != null) {
            binding.editTextName.error = getString(stringId)
        }
    }

    private fun renderSurnameError(@StringRes stringId: Int?) {
        if (stringId != null) {
            binding.editTextSurname.error = getString(stringId)
        }
    }

    private fun renderBirthDayError(@StringRes stringId: Int?) {
        if (stringId != null) {
            binding.editTextBirthDay.error = getString(stringId)
        }
    }

    private fun renderSaveButton(isEnabled: Boolean) {
        binding.buttonSave.isEnabled = isEnabled
    }

    private fun registerButtonClickListeners() {
        with(binding) {
            buttonSave.setOnClickListener {
                viewModel.onSaveClicked()
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
        MaskedTextChangedListener.installOn(
            binding.editTextBirthDay,
            getString(R.string.input_mask_date_pattern)
        )

        binding.editTextBirthDay.doAfterTextChanged {
            viewModel.onBirthDayEntered(it?.toString() ?: "")
        }
    }

    private fun registerNicknameEditTextListener() {
        binding.editTextNickname.doAfterTextChanged {
            viewModel.onNicknameEntered(it?.toString() ?: "")
        }
    }

    private fun registerSurnameEditTexListener() {
        binding.editTextSurname.doAfterTextChanged {
            viewModel.onSurnameEntered(it?.toString() ?: "")
        }
    }

    private fun registerNameEditTextListener() {
        binding.editTextName.doAfterTextChanged {
            viewModel.onNameEntered(it?.toString() ?: "")
        }
    }
}
