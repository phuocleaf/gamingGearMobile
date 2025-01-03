package com.phuocleaf.gaminggearmobile.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.phuocleaf.gaminggearmobile.ui.fragment.ChoXacNhanFragment
import com.phuocleaf.gaminggearmobile.ui.fragment.DaGiaoFragment
import com.phuocleaf.gaminggearmobile.ui.fragment.DaHuyFragment
import com.phuocleaf.gaminggearmobile.ui.fragment.DangVanChuyenFragment
import com.phuocleaf.gaminggearmobile.ui.fragment.DangXuLyFragment

class StatusFragmentAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
) : FragmentStateAdapter(fragmentManager, lifecycle
) {
    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
               ChoXacNhanFragment()
            }

            1 -> {
                DangXuLyFragment()
            }

            2 -> {
                DangVanChuyenFragment()
            }

            3 -> {
                DaGiaoFragment()
            }

            4 -> {
                DaHuyFragment()
            }

            else -> {
                ChoXacNhanFragment()
            }
        }
    }
}