package com.example.androidpim.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.androidpim.R
import com.example.androidpim.fragments.OnboardingFragment3
import com.example.androidpim.fragments.OnboardingFragment4


class OnboardingViewPagerAdapter5(
    fragmentActivity: FragmentActivity,
    private val context: Context
) :
    FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OnboardingFragment3()
            1 -> OnboardingFragment4()
            else -> OnboardingFragment3()
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}