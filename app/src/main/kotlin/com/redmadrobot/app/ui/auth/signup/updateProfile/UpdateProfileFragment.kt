package com.redmadrobot.app.ui.auth.signup.updateProfile

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import javax.inject.Inject

class UpdateProfileFragment : BaseFragment(R.layout.profile_update_fragment) {
    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: UpdateProfileViewModel by viewModels { viewModelFactory }

    private var _binding: ProfileUpdateFragmentBinding? = null
    private val binding get() = _binding!!

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
        _binding = ProfileUpdateFragmentBinding.inflate(inflater, container, false)

        observe(viewModel.eventsQueue, ::onEvent)
        registerButtonClickListeners()
        registerNicknameEditTextListener()
        registerNameEditTextListener()
        registerSurnameEditTexListener()
        registerBirthDayEditTexListener()

        return binding.root
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
            binding.editTextNickname.error = getString(it)
        }

        updateProfileFormState.nameError?.let {
            binding.editTextName.error = getString(it)
        }
        updateProfileFormState.surnameError?.let {
            binding.editTextSurname.error = getString(it)
        }

        updateProfileFormState.birthDayError?.let {
            binding.editTextBirthDay.error = getString(it)
        } ?: run {
            binding.editTextBirthDay.error = null
        }

        // Выставим доступность кнопки согласно валидности данных
        setEnableUpdateProfileButton(updateProfileFormState.isDataValid)
    }

    private fun registerButtonClickListeners() {
        val navController = findNavController(this)
        binding.buttonUpdateProfile.setOnClickListener {
            viewModel.onUpdateProfileClicked(
                nickname = binding.editTextNickname.text.toString(),
                name = binding.editTextName.text.toString(),
                surname = binding.editTextSurname.text.toString(),
                birthDay = binding.editTextBirthDay.text.toString(),
            )
        }
        binding.toolBar.setNavigationOnClickListener {
            navController.navigate(R.id.updateProfileFragmentPop)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun registerBirthDayEditTexListener() {
        binding.editTextBirthDay.inputType = InputType.TYPE_NULL

        binding.editTextBirthDay.setOnClickListener {
            val picker = MaterialDatePicker.Builder.datePicker().setTheme(R.style.Widget_Workplaces_DatePicker).build()
            picker.show(parentFragmentManager, picker.toString())

            picker.addOnPositiveButtonClickListener { _ ->
                binding.editTextBirthDay.setText(picker.headerText)
                onUpdateProfileDataChanged()
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
        viewModel.onRegisterDataChanged(
            nickname = binding.editTextNickname.text.toString(),
            name = binding.editTextName.text.toString(),
            surname = binding.editTextSurname.text.toString(),
            birthDay = binding.editTextBirthDay.text.toString(),
        )
    }

    private fun setEnableUpdateProfileButton(state: Boolean) {
        binding.buttonUpdateProfile.isEnabled = state
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
