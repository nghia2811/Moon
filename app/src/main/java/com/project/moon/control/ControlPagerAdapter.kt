package com.project.moon.control

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.project.moon.control.home.HomeFragment
import com.project.moon.control.info.InfoFragment

class ControlPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return HomeFragment()
            1 -> return InfoFragment()
            else -> return HomeFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "Home"
            1 -> return "Info"
        }
        return super.getPageTitle(position)
    }

}
