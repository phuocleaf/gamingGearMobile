package com.phuocleaf.gaminggearmobile.ui.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.phuocleaf.gaminggearmobile.R
import com.phuocleaf.gaminggearmobile.adapter.CartAdapter
import com.phuocleaf.gaminggearmobile.databinding.FragmentCartBinding
import com.phuocleaf.gaminggearmobile.model.Cart
import com.phuocleaf.gaminggearmobile.onclick.ChangeNumListener
import com.phuocleaf.gaminggearmobile.ui.ThongTinDatHangActivity
import io.paperdb.Paper
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.min


class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var cartList: MutableList<Cart>
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCartBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        Paper.init(requireContext())
        cartList = Paper.book().read("cart", mutableListOf())!!
        var total = 0
        for (i in cartList){
            total += i.sanPham.salePrice.toInt() * i.amount
        }

        var numberFormat = NumberFormat.getInstance(Locale("vi", "VN"))
        var formattedTotal = numberFormat.format(total)
        binding.txtmoney.text = formattedTotal.toString()
        total = 0
        cartAdapter = CartAdapter(object : ChangeNumListener {
            override fun changeNum(position: Int, isAdd: Boolean) {
                if (isAdd){
                    if (cartList[position].amount >= cartList[position].sanPham.quantity){
                        cartList[position].amount >= cartList[position].sanPham.quantity
                        Toast.makeText(context, "Bạn đã thêm số lượng tối đa của mặt hàng này", Toast.LENGTH_SHORT).show()
                    } else {
                        cartList[position].amount++
                    }
                } else {
                    if (cartList[position].amount > 1) {
                        cartList[position].amount--
                    } else {
                        val alertDialogBuilder = AlertDialog.Builder(context)
                        alertDialogBuilder.setTitle("Xoá sản phẩm")
                        alertDialogBuilder.setMessage("Bạn có muốn xoá sản phẩm này khỏi giỏ hàng không?")
                        alertDialogBuilder.setPositiveButton("Xoá") { dialog, which ->
                            cartList.removeAt(position)
                            Paper.book().write("cart",cartList)
                            cartAdapter.setDataCart(cartList as ArrayList<Cart>)

                            for (i in cartList){
                                total += i.sanPham.salePrice.toInt() * i.amount
                            }


                            formattedTotal = numberFormat.format(total)
                            binding.txtmoney.text = formattedTotal.toString()
                            total = 0
                            Toast.makeText(context, "Đã xoá sản phẩm", Toast.LENGTH_SHORT).show()
                        }
                        alertDialogBuilder.setNegativeButton("Hủy") { dialog, which ->

                        }
                        val alertDialog = alertDialogBuilder.create()
                        alertDialog.show()
                    }

                }
                Paper.book().write("cart",cartList)
                cartAdapter.setDataCart(cartList as ArrayList<Cart>)

                for (i in cartList){
                    total += i.sanPham.salePrice.toInt() * i.amount
                }
                formattedTotal = numberFormat.format(total)
                binding.txtmoney.text = formattedTotal.toString()
                total = 0
            }

        })
        cartAdapter.setDataCart(cartList as ArrayList<Cart>)
        binding.rvCart.adapter = cartAdapter
        binding.rvCart.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,false
        )

        binding.btnbuy.setOnClickListener {
            if (cartList.isEmpty()){
                Toast.makeText(context, "Giỏ hàng của bạn đang trống", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(context, ThongTinDatHangActivity::class.java)
                val numberWithoutComma = binding.txtmoney.text.toString().replace(".", "") // Loại bỏ dấu chấm
                val number = numberWithoutComma.toInt() // Chuyển đổi thành số

                intent.putExtra("total", number)
                //write total to paper
                Paper.book().write("total", number)
                startActivity(intent)
            }
        }

    }
}