package com.phuocleaf.gaminggearmobile.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.phuocleaf.gaminggearmobile.R
import com.phuocleaf.gaminggearmobile.adapter.ChonDiaChiAdapter
import com.phuocleaf.gaminggearmobile.databinding.ActivityThongTinCaNhanBinding
import com.phuocleaf.gaminggearmobile.model.DiaChiDataItem
import com.phuocleaf.gaminggearmobile.onclick.OnclickInterface
import com.phuocleaf.gaminggearmobile.viewmodel.DiaChiViewModel
import com.phuocleaf.gaminggearmobile.viewmodel.KhachHangViewModel
import io.paperdb.Paper

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityThongTinCaNhanBinding
private lateinit var khachHangViewModel: KhachHangViewModel
private lateinit var diaChiAdapter: ChonDiaChiAdapter
private lateinit var diaChiViewModel: DiaChiViewModel

class ThongTinCaNhanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityThongTinCaNhanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Paper.init(this)
        val id = Paper.book().read<String>("user_id")

        initView()
        diaChiViewModel = ViewModelProvider(this)[DiaChiViewModel::class.java]
        khachHangViewModel = ViewModelProvider(this)[KhachHangViewModel::class.java]

        if (id != null) {
            diaChiViewModel.getDiaChi(id)
            khachHangViewModel.getUserInfo(id)
        }
        khachHangViewModel.observeUserInfo().observe(
            this,
        ) {
            binding.tvname.text = "Tên: "+ it.name
            binding.tvemail.text = "Email: "+ it.email
            binding.tvphone.text = "Số điện thoại: "+ it.phone
            binding.tvgender.text = "giới tính: "+ it.sex
            binding.tvbirthday.text = "Ngày sinh: "+ it.dateOfBirth
        }
        observerLiveData()

    }
    private fun initView() {
        diaChiAdapter = ChonDiaChiAdapter(object: OnclickInterface {
            override fun onClick(position: Int) {

            }
        })
        binding.rvAddresses.apply {
            adapter = diaChiAdapter
            layoutManager = LinearLayoutManager(this@ThongTinCaNhanActivity)
        }
    }

    private fun observerLiveData() {
        diaChiViewModel.observeDiaChiInfo().observe(this) {diachiData ->
            diaChiAdapter.setDataDiaChiList(diachiData as ArrayList<DiaChiDataItem>)
        }
    }
}