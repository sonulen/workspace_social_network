package com.redmadrobot.app.ui.auth

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.redmadrobot.app.R
import com.redmadrobot.app.databinding.DoneFragmentBinding
import com.redmadrobot.app.ui.base.fragment.BaseFragment
import com.redmadrobot.extensions.viewbinding.viewBinding

class DoneFragment : BaseFragment(R.layout.done_fragment) {
    private val binding: DoneFragmentBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerButtonClickListeners()
    }

    private fun registerButtonClickListeners() {
        val navController = findNavController(this)
        binding.buttonGoToFeed.setOnClickListener {
            navController.navigate(R.id.WorkspaceGraph)
        }
    }
}
