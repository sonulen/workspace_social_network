package com.redmadrobot.app.ui.feed

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.redmadrobot.app.R
import com.redmadrobot.app.databinding.FeedFragmentBinding
import com.redmadrobot.app.ui.base.fragment.BaseFragment
import com.redmadrobot.extensions.viewbinding.viewBinding

class FeedFragment : BaseFragment(R.layout.feed_fragment) {
    private val binding: FeedFragmentBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerButtonClickListeners()
    }

    private fun registerButtonClickListeners() {
        val navController = NavHostFragment.findNavController(this)

        with(binding) {
            btnFindFriends.setOnClickListener {
                Toast.makeText(context, "Извини, но все разошлись!", Toast.LENGTH_SHORT).show()
            }
            btnDeauth.setOnClickListener {
                navController.navigate(R.id.AuthGraph)
            }
        }
    }
}
