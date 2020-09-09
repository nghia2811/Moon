package com.project.moon.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.project.moon.main.chat.ChatFragment
import com.project.moon.main.home.HomeFragment
import com.project.moon.main.info.InfoFragment

class CustomPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return HomeFragment()
            1 -> return ChatFragment()
            2 -> return InfoFragment()
            else -> return HomeFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            0 -> return "Home"
            1 -> return "Chat"
            2 -> return "Info"
        }
        return super.getPageTitle(position)
    }
}
