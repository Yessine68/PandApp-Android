package com.example.androidpim.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class ViewPagerAdapter(fm: FragmentManager?) :
    FragmentPagerAdapter(fm!!) {
    // Declare Variables
    private val fragmentList: MutableList<Fragment> = ArrayList()
    private val fragmentListTitles: MutableList<String> = ArrayList()
    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentListTitles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentListTitles[position]
    }

    fun addFragment(fragment: Fragment, title: String) {
        // add fragment to the list
        fragmentList.add(fragment)
        fragmentListTitles.add(title)
    }
}
