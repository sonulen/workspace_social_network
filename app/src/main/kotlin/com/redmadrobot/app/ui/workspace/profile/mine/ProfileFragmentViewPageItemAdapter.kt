package com.redmadrobot.app.ui.workspace.profile.mine

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ProfileFragmentViewPageItemAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return ProfileMineEmptyFriendsFragment()
    }
}
