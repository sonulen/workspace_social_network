package com.redmadrobot.app.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.redmadrobot.app.R
import com.redmadrobot.app.databinding.ActivityMainBinding
import com.redmadrobot.app.di.AppComponent
import com.redmadrobot.app.ui.base.activity.BaseActivity
import com.redmadrobot.app.utils.extension.dispatchApplyWindowInsetsToChild

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (appComponent as AppComponent).inject(this)
        val navController = findNavController(R.id.nav_host_fragment)
        binding.menuNavigation.setupWithNavController(navController)
        setupBottomNavigationBarVisibility(navController)

        binding.activityStartContainerScreens.dispatchApplyWindowInsetsToChild()
        // TODO Тут нужна ViewModel которая будет решать по какому графу идти. Auth или Workspace
    }

    private fun setupBottomNavigationBarVisibility(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.welcomeFragment,
                R.id.doneFragment,
                R.id.loginFragment,
                R.id.registerFragment,
                R.id.updateProfileFragment,
                -> {
                    binding.menuNavigation.visibility = View.GONE
                }

                else -> binding.menuNavigation.visibility = View.VISIBLE
            }
        }
    }
}
