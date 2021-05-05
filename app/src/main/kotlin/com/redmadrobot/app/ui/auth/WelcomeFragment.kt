package com.redmadrobot.app.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.redmadrobot.app.R
import com.redmadrobot.app.databinding.WelcomeFragmentBinding
import com.redmadrobot.app.ui.base.fragment.BaseFragment

class WelcomeFragment : BaseFragment(R.layout.welcome_fragment) {
    private var _binding: WelcomeFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = WelcomeFragmentBinding.inflate(inflater, container, false)
        registerButtonClickListeners()
        return binding.root
    }

    private fun registerButtonClickListeners() {
        val navController = findNavController(this)
        binding.buttonLogin.setOnClickListener {
            navController.navigate(R.id.toLoginFragment)
        }

        binding.btnRegisterByMail.setOnClickListener {
            navController.navigate(R.id.toRegisterFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
