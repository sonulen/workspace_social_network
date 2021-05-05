package com.redmadrobot.app.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.redmadrobot.app.R
import com.redmadrobot.app.databinding.FeedFragmentBinding
import com.redmadrobot.app.ui.base.fragment.BaseFragment

class FeedFragment : BaseFragment(R.layout.feed_fragment) {
    private var _binding: FeedFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FeedFragmentBinding.inflate(inflater, container, false)

        registerButtonClickListeners()

        return binding.root
    }

    private fun registerButtonClickListeners() {
        binding.btnFindFriends.setOnClickListener {
            Toast.makeText(context, "Извини, но все разошлись!", Toast.LENGTH_SHORT).show()
        }

        val navController = NavHostFragment.findNavController(this)
        binding.btnDeauth.setOnClickListener {
            navController.navigate(R.id.toWelcomeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
