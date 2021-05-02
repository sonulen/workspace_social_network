package com.redmadrobot.app.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.redmadrobot.app.R
import com.redmadrobot.app.ui.base.fragment.BaseFragment

class WelcomeFragment : BaseFragment(R.layout.welcome_fragment) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = inflater.inflate(R.layout.welcome_fragment, container, false)
        registerButtonClickListeners(view)
        return view
    }

    private fun registerButtonClickListeners(view: View) {
        val navController = findNavController(this)
        view.findViewById<Button>(R.id.btn_login).setOnClickListener {
            navController.navigate(R.id.toLoginFragment)
        }

        view.findViewById<Button>(R.id.btn_register_by_mail).setOnClickListener {
            navController.navigate(R.id.toRegisterFragment)
        }
    }
}
