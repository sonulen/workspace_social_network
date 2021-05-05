package com.redmadrobot.app.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.redmadrobot.app.R
import com.redmadrobot.app.databinding.DoneFragmentBinding
import com.redmadrobot.app.ui.base.fragment.BaseFragment

class DoneFragment : BaseFragment(R.layout.done_fragment) {
    private var _binding: DoneFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DoneFragmentBinding.inflate(inflater, container, false)
        registerButtonClickListeners()
        return binding.root
    }

    private fun registerButtonClickListeners() {
        val navController = findNavController(this)
        binding.btnGoToFeed.setOnClickListener {
            navController.navigate(R.id.WorkspaceGraph)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
