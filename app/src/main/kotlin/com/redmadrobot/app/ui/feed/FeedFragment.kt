package com.redmadrobot.app.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.redmadrobot.app.R
import com.redmadrobot.app.ui.base.fragment.BaseFragment

class FeedFragment : BaseFragment(R.layout.feed_fragment) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = inflater.inflate(
            R.layout.feed_fragment,
            container,
            false
        )

        registerButtonClickListeners(view)

        return view
    }

    private fun registerButtonClickListeners(view: View) {
        view.findViewById<Button>(R.id.btn_find_friends).setOnClickListener {
            Toast.makeText(view.context, "Извини, но все разошлись!", Toast.LENGTH_SHORT).show()
        }
    }
}
