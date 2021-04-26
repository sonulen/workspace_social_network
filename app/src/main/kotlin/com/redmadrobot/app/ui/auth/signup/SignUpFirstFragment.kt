package com.redmadrobot.app.ui.auth.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.redmadrobot.app.R
import com.redmadrobot.app.ui.base.fragment.BaseFragment

class SignUpFirstFragment : BaseFragment(R.layout.sign_up_first) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = inflater.inflate(
            R.layout.sign_up_first,
            container,
            false
        )

        registerButtonClickListeners(view)

        return view
    }

    private fun registerButtonClickListeners(view: View) {
        val navController = findNavController(this)
        view.findViewById<Button>(R.id.btn_go_to_sign_in).setOnClickListener {
            navController.navigate(R.id.action_signUpFirstFragment_to_signInFragment)
        }

        view.findViewById<Button>(R.id.btn_go_next).setOnClickListener {
            navController.navigate(R.id.action_signUpFirstFragment_to_signUpSecondFragment)
        }

        view.findViewById<ImageButton>(R.id.btn_back).setOnClickListener {
            navController.navigate(R.id.action_signUpFirstFragment_pop)
        }
    }
}
