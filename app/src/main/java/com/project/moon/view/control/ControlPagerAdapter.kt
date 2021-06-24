package com.project.moon.view.control

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.project.moon.view.control.commands.CommandsFragment
import com.project.moon.view.control.home.HomeFragment
import com.project.moon.view.control.info.InfoFragment

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
            0 -> return "Online mode"
            1 -> return "Offline mode"
            2 -> return "Info"
        }
        return super.getPageTitle(position)
    }

}
