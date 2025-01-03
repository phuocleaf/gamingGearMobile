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
import com.phuocleaf.gaminggearmobile.R
import com.phuocleaf.gaminggearmobile.databinding.ActivityDangNhapBinding
import com.phuocleaf.gaminggearmobile.databinding.ActivityThongTinDatHangBinding
import com.phuocleaf.gaminggearmobile.model.Cart
import com.phuocleaf.gaminggearmobile.model.DatHangData
import com.phuocleaf.gaminggearmobile.model.DiaChiDataItem
import com.phuocleaf.gaminggearmobile.viewmodel.DatHangViewModel
import com.phuocleaf.gaminggearmobile.viewmodel.KhachHangViewModel
import io.paperdb.Paper
import java.text.NumberFormat
import java.util.Locale

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityThongTinDatHangBinding
private lateinit var khachHangViewModel: KhachHangViewModel
private lateinit var diaChi: DiaChiDataItem
private lateinit var datHangViewModel: DatHangViewModel

class ThongTinDatHangActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityThongTinDatHangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        khachHangViewModel = ViewModelProvider(this)[KhachHangViewModel::class.java]
        Paper.init(this)
        val user_id = Paper.book().read<String>("user_id")
        val total = Paper.book().read<Int>("total") ?: 0
        val cartList = Paper.book().read("cart", mutableListOf<Cart>().toList())

        user_id?.let { khachHangViewModel.getUserInfo(it) }

        khachHangViewModel.observeUserInfo().observe(
            this,
        ) {
            binding.etname.setText(it.name)
            binding.etphone.setText(it.phone)
        }

        val numberFormat = NumberFormat.getInstance(Locale("vi", "VN"))
        val formattedTotal = numberFormat.format(total)

        binding.tvtotal.text = "Tổng tiền: $formattedTotal"

        binding.imageViewBack.setOnClickListener {
            finish()
        }

        binding.tvchangeaddress.setOnClickListener {
            //intent
            val intent = Intent(this, ChonDiaChiNhanHangActivity::class.java)
            startActivity(intent)
        }

        diaChi = Paper.book().read<DiaChiDataItem>("diaChi")!!
        binding.etaddress.text = "Địa chỉ: ${diaChi?.desc}"

        binding.btnOrder.setOnClickListener {
            val name = binding.etname.text.toString().trim()
            val phone = binding.etphone.text.toString().trim()

            val note = binding.etnote.text.toString().trim()

            datHangViewModel = ViewModelProvider(this)[DatHangViewModel::class.java]

            val datHangData = DatHangData(
                userId = user_id!!,
                userAddress = diaChi,
                userName = name,
                userPhone = phone,
                cartList = cartList!!,
                total = total,
                userNote = note
            )

            datHangViewModel.createOrder(datHangData)

            datHangViewModel.observeOrderDataResponse().observe(
                this,
            ) {
                if (it.success) {
                    Toast.makeText(this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show()
                    Paper.book().delete("cart")
                    val intent = Intent(this, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Lỗi", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        diaChi = Paper.book().read<DiaChiDataItem>("diaChi")!!

        binding.etaddress.text = diaChi.desc
    }
}