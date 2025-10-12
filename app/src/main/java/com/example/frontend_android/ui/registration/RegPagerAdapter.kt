package com.example.frontend_android.ui.registration

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class RegPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OneRegFragment()
            1 -> TwoRegFragment()
            2 -> ThreeRegFragment()
            else -> throw IllegalArgumentException("Kan inte gå dit")
        }
    }

    override fun getItemCount(): Int = 3
}