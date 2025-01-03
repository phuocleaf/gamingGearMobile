package com.phuocleaf.gaminggearmobile.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.phuocleaf.gaminggearmobile.R
import com.phuocleaf.gaminggearmobile.adapter.SanPhamDaDatAdapter
import com.phuocleaf.gaminggearmobile.databinding.ActivityChiTietDonDaDatBinding
import com.phuocleaf.gaminggearmobile.model.HuyDonResponse
import com.phuocleaf.gaminggearmobile.model.donhang.Cart
import com.phuocleaf.gaminggearmobile.model.donhang.DonHangItem
import com.phuocleaf.gaminggearmobile.viewmodel.HuyDonViewModel
import io.paperdb.Paper

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityChiTietDonDaDatBinding
private lateinit var sanPhamDaDatAdapter: SanPhamDaDatAdapter
private lateinit var huyDonViewModel: HuyDonViewModel

class ChiTietDonDaDatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityChiTietDonDaDatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        huyDonViewModel =ViewModelProvider(this)[HuyDonViewModel::class.java]

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        initView()


    }



    @SuppressLint("SetTextI18n")
    private fun initView() {
        Paper.init(this)

        // order: DonHangItem
        val order = Paper.book().read("order",null) as DonHangItem

        binding.textViewName.text = "Tên: " + order.userName
        binding.textViewPhoneNumber.text = "SĐT: " + order.userPhone
        binding.textViewAddress.text = "Địa chỉ: " + order.userAddress.desc

        binding.textViewTotalPrice.text = "Tổng tiền: " + order.total.toString()
        binding.textViewTime.text = "Thời gian đặt: " + order.created_at.toString()
        binding.textViewNote.text = "Ghi chú: " + order.userNote

        sanPhamDaDatAdapter = SanPhamDaDatAdapter()
        sanPhamDaDatAdapter.setDataDetail(order.cartList as ArrayList<Cart>)
        binding.recyclerViewClothes.apply {
            adapter = sanPhamDaDatAdapter
            layoutManager = LinearLayoutManager(this@ChiTietDonDaDatActivity)
        }

        binding.imageViewBack.setOnClickListener {
            val intent = Intent(this, ThongTinDonHangActivity::class.java)
            startActivity(intent)
            finish()
        }

        if (order.status == "Chờ xác nhận") {
            binding.btnCancel.visibility = View.VISIBLE
        }

        binding.btnCancel.setOnClickListener {
            val orderId = order._id

            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.apply {
                setTitle("Xác nhận")
                setMessage("Bạn có chắc chắn muốn hủy đơn hàng này?")
                setPositiveButton("Đồng ý") { dialog, _ ->
                    // Gọi hàm để hủy đơn hàng ở đây
                    huyDonViewModel.HuyDon(orderId)
                    // Theo dõi phản hồi từ việc hủy đơn hàng
                    observerCancelResponse()
                    dialog.dismiss() // Đóng dialog sau khi người dùng xác nhận
                }
                setNegativeButton("Hủy") { dialog, _ ->
                    dialog.dismiss() // Đóng dialog nếu người dùng không muốn hủy đơn hàng
                }
            }

            // Tạo và hiển thị AlertDialog
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
    }
    //overide back button
    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, ThongTinDonHangActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun observerCancelResponse() {
        val cancelOrderObserver = Observer<Boolean> { updateOrderStatusResponse ->
            if (updateOrderStatusResponse) {
                Toast.makeText(this, "Huỷ đơn thành công", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ThongTinDonHangActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show()
            }
        }
        huyDonViewModel.observeHuyDonStatus().observe(this, cancelOrderObserver)
    }
}