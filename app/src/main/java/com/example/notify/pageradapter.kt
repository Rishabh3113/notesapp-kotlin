package com.example.notify

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


class PagerAdapter(fm: FragmentManager, private val context: Int) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> notesFragment()
            1 -> profileFragment()

            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }



    override fun getCount(): Int {
        return 2
    }
}