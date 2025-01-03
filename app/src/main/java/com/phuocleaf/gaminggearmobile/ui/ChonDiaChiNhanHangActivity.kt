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
import com.phuocleaf.gaminggearmobile.databinding.ActivityChonDiaChiNhanHangBinding
import com.phuocleaf.gaminggearmobile.model.DiaChiDataItem
import com.phuocleaf.gaminggearmobile.onclick.OnclickInterface
import com.phuocleaf.gaminggearmobile.viewmodel.DiaChiViewModel
import io.paperdb.Paper

private lateinit var diaChiAdapter: ChonDiaChiAdapter
@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityChonDiaChiNhanHangBinding
private lateinit var viewModel: DiaChiViewModel
class ChonDiaChiNhanHangActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityChonDiaChiNhanHangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Paper.init(this)
        val id = Paper.book().read<String>("user_id")

        initView()
        viewModel = ViewModelProvider(this)[DiaChiViewModel::class.java]
        if (id != null) {
            viewModel.getDiaChi(id)
        }

        observerLiveData()

    }

   private fun initView() {
       diaChiAdapter = ChonDiaChiAdapter(object: OnclickInterface {
            override fun onClick(position: Int) {
                // Toast value
                val diaChiDataItem = diaChiAdapter.getDiaChiList()[position]
                Paper.init(this@ChonDiaChiNhanHangActivity)
                Paper.book().write("diaChi", diaChiDataItem)

                Toast.makeText(this@ChonDiaChiNhanHangActivity,"Đã chọn địa chỉ", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@ChonDiaChiNhanHangActivity, ThongTinDatHangActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
        binding.recyclerDiaChiNhanHang.apply {
            adapter = diaChiAdapter
            layoutManager = LinearLayoutManager(this@ChonDiaChiNhanHangActivity)
        }
   }

    private fun observerLiveData() {
        viewModel.observeDiaChiInfo().observe(this) {diachiData ->
            diaChiAdapter.setDataDiaChiList(diachiData as ArrayList<DiaChiDataItem>)
        }
    }
}