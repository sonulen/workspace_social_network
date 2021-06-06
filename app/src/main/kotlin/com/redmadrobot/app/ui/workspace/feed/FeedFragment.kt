package com.redmadrobot.app.ui.workspace.feed

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.redmadrobot.app.R
import com.redmadrobot.app.databinding.FeedFragmentBinding
import com.redmadrobot.app.di.workspace.feed.FeedComponent
import com.redmadrobot.app.ui.base.fragment.BaseFragment
import com.redmadrobot.extensions.lifecycle.observe
import com.redmadrobot.extensions.viewbinding.viewBinding
import javax.inject.Inject

class FeedFragment : BaseFragment(R.layout.feed_fragment) {
    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: FeedViewModel by viewModels { viewModelFactory }

    private val binding: FeedFragmentBinding by viewBinding()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initDagger()
    }

    private fun initDagger() {
        FeedComponent.init(appComponent).inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.eventsQueue, ::onEvent)
        initEpoxyRecyclerView()
    }

    private fun initEpoxyRecyclerView() {
        with(binding) {
            recyclerView.setController(viewModel.getEpoxyController())
        }
    }
}
