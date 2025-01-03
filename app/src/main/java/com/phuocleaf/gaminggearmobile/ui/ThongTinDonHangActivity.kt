package com.phuocleaf.gaminggearmobile.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.phuocleaf.gaminggearmobile.R
import com.phuocleaf.gaminggearmobile.adapter.StatusFragmentAdapter
import com.phuocleaf.gaminggearmobile.databinding.ActivityThongTinDonHangBinding

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityThongTinDonHangBinding
private lateinit var adapter: StatusFragmentAdapter

class ThongTinDonHangActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityThongTinDonHangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tabLayout = binding.tabLayout
        val viewPager2 = binding.viewPager

        adapter = StatusFragmentAdapter(supportFragmentManager, lifecycle)

        tabLayout.addTab(tabLayout.newTab().setText("Chờ xác nhận"))
        tabLayout.addTab(tabLayout.newTab().setText("Đang xử lý"))
        tabLayout.addTab(tabLayout.newTab().setText("Đang vận chuyển"))
        tabLayout.addTab(tabLayout.newTab().setText("Đã giao"))
        tabLayout.addTab(tabLayout.newTab().setText("Đã hủy"))

        viewPager2.adapter = adapter

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager2.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })

        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}