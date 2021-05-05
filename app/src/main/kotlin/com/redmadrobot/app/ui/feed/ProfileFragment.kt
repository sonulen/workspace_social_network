package com.redmadrobot.app.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.redmadrobot.app.R
import com.redmadrobot.app.databinding.ProfileFragmentBinding
import com.redmadrobot.app.ui.base.fragment.BaseFragment

class ProfileFragment : BaseFragment(R.layout.feed_fragment) {
    private var _binding: ProfileFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = ProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
