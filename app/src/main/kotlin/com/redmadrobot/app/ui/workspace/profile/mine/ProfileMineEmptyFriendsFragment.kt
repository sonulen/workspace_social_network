package com.redmadrobot.app.ui.workspace.profile.mine

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.redmadrobot.app.R
import com.redmadrobot.app.databinding.ProfileMinePageItemEmptyFriendsBinding
import com.redmadrobot.app.di.workspace.profile.mine.ProfileMineEmptyFriendsComponent
import com.redmadrobot.app.ui.base.fragment.BaseFragment
import com.redmadrobot.extensions.lifecycle.observe
import com.redmadrobot.extensions.viewbinding.viewBinding
import javax.inject.Inject

class ProfileMineEmptyFriendsFragment : BaseFragment(R.layout.profile_mine_page_item_empty_friends) {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: ProfileMineEmptyFriendsViewModel by viewModels { viewModelFactory }

    private val binding: ProfileMinePageItemEmptyFriendsBinding by viewBinding()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initDagger()
    }

    private fun initDagger() {
        ProfileMineEmptyFriendsComponent.init().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.eventsQueue, ::onEvent)
        registerButtonClickListeners()
    }

    private fun registerButtonClickListeners() {
        with(binding) {
            buttonFindFriends.setOnClickListener {
                viewModel.onFindFriendsClicked()
            }
        }
    }
}
