package com.redmadrobot.app.ui.auth.done

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.redmadrobot.app.R
import com.redmadrobot.app.databinding.DoneFragmentBinding
import com.redmadrobot.app.di.auth.done.DoneComponent
import com.redmadrobot.app.ui.base.fragment.BaseFragment
import com.redmadrobot.extensions.lifecycle.observe
import com.redmadrobot.extensions.viewbinding.viewBinding
import javax.inject.Inject

class DoneFragment : BaseFragment(R.layout.done_fragment) {
    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: DoneViewModel by viewModels { viewModelFactory }

    private val binding: DoneFragmentBinding by viewBinding()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initDagger()
    }

    private fun initDagger() {
        DoneComponent.init().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.eventsQueue, ::onEvent)
        registerButtonClickListeners()
    }

    private fun registerButtonClickListeners() {
        binding.buttonGoToFeed.setOnClickListener {
            viewModel.onGoToFeedClicked()
        }
    }
}
