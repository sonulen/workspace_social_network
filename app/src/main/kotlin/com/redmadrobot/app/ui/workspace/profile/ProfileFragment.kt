package com.redmadrobot.app.ui.workspace.profile

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.redmadrobot.app.R
import com.redmadrobot.app.databinding.ProfileFragmentBinding
import com.redmadrobot.app.di.workspace.profile.ProfileComponent
import com.redmadrobot.app.ui.LoadingDialogFragment
import com.redmadrobot.app.ui.base.fragment.BaseFragment
import com.redmadrobot.app.ui.base.viewmodel.ScreenState
import com.redmadrobot.app.ui.workspace.profile.mine.ProfileFragmentViewPageItemAdapter
import com.redmadrobot.extensions.lifecycle.observe
import com.redmadrobot.extensions.viewbinding.viewBinding
import javax.inject.Inject

class ProfileFragment : BaseFragment(R.layout.profile_fragment) {
    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: ProfileViewModel by viewModels { viewModelFactory }
    private val binding: ProfileFragmentBinding by viewBinding()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initDagger()
    }

    private fun initDagger() {
        ProfileComponent.init(appComponent).inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPagerWithTabLayout()

        observe(viewModel.eventsQueue, ::onEvent)
        observe(viewModel.screenState, ::onScreenStateChange)
        observe(viewModel.userData, ::renderUserData)

        registerButtonClickListeners()
    }

    private fun renderUserData(userData: UserDataProfileViewState) {
        with(binding) {
            toolbarTitle.text = userData.nickname
            nameTextView.text = userData.name + " " + userData.surname
            ageTextView.text = userData.birthDay
        }
    }

    private fun onScreenStateChange(state: ScreenState) {
        when (state) {
            ScreenState.CONTENT,
            ScreenState.ERROR,
            -> renderSpin(isVisible = false)
            ScreenState.LOADING -> renderSpin(isVisible = true)
        }
    }

    private fun renderSpin(isVisible: Boolean) {
        if (isVisible) {
            LoadingDialogFragment().apply {
                isCancelable = false
            }.show(childFragmentManager, LoadingDialogFragment.TAG)
        } else {
//            val fragment = childFragmentManager.findFragmentByTag(LoadingDialogFragment.TAG) as? DialogFragment
//            fragment?.dismiss()
        }
    }

    private fun initViewPagerWithTabLayout() {
        binding.pager.adapter = ProfileFragmentViewPageItemAdapter(this)

        TabLayoutMediator(
            binding.tabLayout,
            binding.pager
        ) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = getString(R.string.profile_tab_name_posts)
                }

                1 -> {
                    tab.text = getString(R.string.profile_tab_name_likes)
                }

                2 -> {
                    tab.text = getString(R.string.profile_tab_name_friends)
                }
            }
        }.attach()
    }

    private fun registerButtonClickListeners() {
        with(binding) {
            buttonEditProfile.setOnClickListener {
                viewModel.onProfileEditClicked()
            }
            buttonLogout.setOnClickListener {
                viewModel.onLogoutClicked()
            }
        }
    }
}
