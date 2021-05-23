package com.redmadrobot.app.ui.workspace.profileEdit

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.redmadrobot.app.R
import com.redmadrobot.app.databinding.ProfileEditFragmentBinding
import com.redmadrobot.app.di.workspace.profileEdit.ProfileEditComponent
import com.redmadrobot.app.ui.base.fragment.BaseFragment
import com.redmadrobot.extensions.lifecycle.observe
import com.redmadrobot.extensions.viewbinding.viewBinding
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
        registerButtonClickListeners()
    }

    private fun renderSaveButton(isEnabled: Boolean) {
        binding.buttonSave.isEnabled = isEnabled
    }

    private fun registerButtonClickListeners() {
        binding.buttonSave.setOnClickListener {
            viewModel.onSaveClicked()
        }
        binding.toolBar.setNavigationOnClickListener {
            viewModel.onBackClicked()
        }
    }
}
