package com.project.moon.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.project.moon.device.DeviceFragment
import com.project.moon.chat.ChatFragment

class MainPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return DeviceFragment()
            1 -> return ChatFragment()
            else -> return DeviceFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "Device"
            1 -> return "Chat"
        }
        return super.getPageTitle(position)
    }

}
