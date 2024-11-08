package com.phuocleaf.gaminggearmobile.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.phuocleaf.gaminggearmobile.R
import com.phuocleaf.gaminggearmobile.adapter.HinhAnhAdapter
import com.phuocleaf.gaminggearmobile.databinding.ActivityChiTietSanPhamBinding
import com.phuocleaf.gaminggearmobile.databinding.ItemSanPhamBinding
import com.phuocleaf.gaminggearmobile.model.Cart
import com.phuocleaf.gaminggearmobile.model.Image
import com.phuocleaf.gaminggearmobile.model.SanPhamItem
import com.phuocleaf.gaminggearmobile.onclick.OnclickInterface
import com.phuocleaf.gaminggearmobile.util.Util
import io.paperdb.Paper

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityChiTietSanPhamBinding
private lateinit var hinhAnhAdapter: HinhAnhAdapter


class ChiTietSanPhamActivity : AppCompatActivity() {

    var amount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Paper.init(this)
        enableEdgeToEdge()

        binding = ActivityChiTietSanPhamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sanPham = intent.getParcelableExtra<SanPhamItem>("sanpham")
        sanPham?.let {
            binding.txtTenSanPham.text = it.name
            binding.txtGiaSanPham.text = it.salePrice.toString()
            binding.txtMoTaDetail.text = it.description

            binding.txtConLai.text = "Còn lại: ${it.quantity}"

            val imageURL = Util.BASE_URL + "hinhanh/" + sanPham.images[0].path
            Glide.with(binding.imgSanPham.context).load(imageURL).into(binding.imgSanPham)

            hinhAnhAdapter = HinhAnhAdapter(object: OnclickInterface {
                override fun onClick(position: Int) {
                    val image = hinhAnhAdapter.getHinhAnhItemAt(position)
                    val imageURL = Util.BASE_URL + "hinhanh/" + image.path
                    Glide.with(binding.imgSanPham.context).load(imageURL).into(binding.imgSanPham)
                }
            })

            binding.recyclerAnhSanPham.apply {
                adapter = hinhAnhAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }

            hinhAnhAdapter.setDataHinhAnhList(sanPham.images as ArrayList<Image>)

            binding.ivBack.setOnClickListener {
                finish()
            }

            binding.imgadd.setOnClickListener {
                if (amount < sanPham.quantity) {
                    amount++
                    binding.txtamount.text = amount.toString()
                } else {
                    showAlertDialog(this, amount)
                }
            }

            binding.imgsub.setOnClickListener{
                if(amount > 1){
                    amount--
                    binding.txtamount.text = amount.toString()
                }
            }

            binding.btnaddtocart.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)

                var cart = Cart(
                    sanPham,
                    amount
                )

                var isExist = false
                var cartList = mutableListOf<Cart>()
                cartList = Paper.book().read("cart", mutableListOf())!!

                if(cartList.size > 0){
                    for(i in 0 until cartList.size){
                        if(cartList[i].sanPham._id == cart.sanPham._id){
                            isExist = true
                            cartList[i].amount += cart.amount
                            Paper.book().write("cart",cartList)
                        }
                    }
                    if(!isExist){
                        cartList.add(cart)
                        Paper.book().write("cart",cartList)
                    }
                } else{
                    cartList.add(cart)
                    Paper.book().write("cart",cartList)
                }
                Toast.makeText(this,"Đã thêm vào giỏ", Toast.LENGTH_LONG).show()
                startActivity(intent)
                finish()
            }

        }
    }

    private fun showAlertDialog(context: Context, amount: Int) {
        // Tạo đối tượng AlertDialog.Builder
        val builder = AlertDialog.Builder(context)

        // Thiết lập các thuộc tính của hộp thoại
        builder.setTitle("Cảnh báo")
            .setMessage("Bạn chỉ có thể thêm tối đa ${amount} sản phẩm vào giỏ hàng")
            .setPositiveButton("OK") { dialog, _ ->
                // Đóng hộp thoại khi người dùng nhấn nút "OK"
                dialog.dismiss()
            }

        // Hiển thị hộp thoại
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }
}