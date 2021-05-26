package com.swtug.anticovid.view.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(private val fragments: List<Pair<Fragment, String>>, fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun createFragment(position: Int): Fragment {
        return fragments[position].first
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    fun getItemNameAtPosition(position: Int): String {
        return fragments[position].second
    }
}