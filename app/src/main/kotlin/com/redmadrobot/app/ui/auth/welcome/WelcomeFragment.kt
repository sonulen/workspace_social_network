package com.redmadrobot.app.ui.auth.welcome

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.redmadrobot.app.R
import com.redmadrobot.app.databinding.WelcomeFragmentBinding
import com.redmadrobot.app.di.auth.welcome.WelcomeComponent
import com.redmadrobot.app.ui.base.fragment.BaseFragment
import com.redmadrobot.extensions.lifecycle.observe
import com.redmadrobot.extensions.viewbinding.viewBinding
import javax.inject.Inject

class WelcomeFragment : BaseFragment(R.layout.welcome_fragment) {
    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: WelcomeViewModel by viewModels { viewModelFactory }

    private val binding: WelcomeFragmentBinding by viewBinding()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initDagger()
    }

    private fun initDagger() {
        WelcomeComponent.init().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.eventsQueue, ::onEvent)
        registerButtonClickListeners()
    }

    private fun registerButtonClickListeners() {
        with(binding) {
            buttonLogin.setOnClickListener {
                viewModel.onGoToLoginClicked()
            }
            buttonRegisterByMail.setOnClickListener {
                viewModel.onGoToRegisterClicked()
            }
        }
    }
}
