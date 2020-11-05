package com.project.moon.control

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.project.moon.control.commands.CommandsFragment
import com.project.moon.control.home.HomeFragment
import com.project.moon.control.info.InfoFragment

class ControlPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> CommandsFragment()
            2 -> InfoFragment()
            else -> HomeFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "Home"
            1 -> return "Commands"
            2 -> return "Info"
        }
        return super.getPageTitle(position)
    }

}
