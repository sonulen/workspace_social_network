package com.redmadrobot.app.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.redmadrobot.app.R
import com.redmadrobot.app.databinding.ProfileFragmentBinding
import com.redmadrobot.app.ui.base.fragment.BaseFragment
import com.redmadrobot.extensions.viewbinding.inflateViewBinding

class ProfileFragment : BaseFragment(R.layout.feed_fragment) {
    private lateinit var binding: ProfileFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = inflater.inflateViewBinding(container, attachToRoot = false)
        return binding.root
    }
}
