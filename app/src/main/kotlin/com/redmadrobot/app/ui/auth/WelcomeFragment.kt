package com.redmadrobot.app.ui.auth

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.redmadrobot.app.R
import com.redmadrobot.app.databinding.WelcomeFragmentBinding
import com.redmadrobot.app.ui.base.fragment.BaseFragment
import com.redmadrobot.extensions.viewbinding.viewBinding

class WelcomeFragment : BaseFragment(R.layout.welcome_fragment) {
    private val binding: WelcomeFragmentBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerButtonClickListeners()
    }

    private fun registerButtonClickListeners() {
        val navController = findNavController(this)
        with(binding) {
            buttonLogin.setOnClickListener {
                navController.navigate(R.id.toLoginFragment)
            }

            buttonRegisterByMail.setOnClickListener {
                navController.navigate(R.id.toRegisterFragment)
            }
        }
    }
}
