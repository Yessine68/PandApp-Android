package com.example.androidpim.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.androidpim.R
import com.example.androidpim.fragments.OnboardingFragment
import com.example.androidpim.fragments.OnboardingFragment2


class OnboardingViewPagerAdapter4(
    fragmentActivity: FragmentActivity,
    private val context: Context
) :
    FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OnboardingFragment()
            1 -> OnboardingFragment2()
            else -> OnboardingFragment()
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}