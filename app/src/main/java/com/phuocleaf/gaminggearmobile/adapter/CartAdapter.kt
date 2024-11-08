package com.phuocleaf.gaminggearmobile.adapter

import android.R
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.phuocleaf.gaminggearmobile.databinding.ItemCartBinding
import com.phuocleaf.gaminggearmobile.model.Cart
import com.phuocleaf.gaminggearmobile.onclick.ChangeNumListener
import com.phuocleaf.gaminggearmobile.util.Util

class CartAdapter(private val changeNumListener: ChangeNumListener,
): RecyclerView.Adapter<CartAdapter.MyViewHolder>() {

    private var cartList = ArrayList<Cart>()

    class MyViewHolder(var binding: ItemCartBinding): RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemCartBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.binding.txtTenSanPham.text = cartList[position].sanPham.name
        holder.binding.txtGiaSanPham.text = cartList[position].sanPham.salePrice.toString()
        holder.binding.txtAmount.text = cartList[position].amount.toString()
        val imageURL = Util.BASE_URL + "hinhanh/" + cartList[position].sanPham.images[0].path
        Glide.with(holder.itemView).load(imageURL).into(holder.binding.imgSanPham)

        holder.binding.imgadd.setOnClickListener {
            changeNumListener.changeNum(position,true)
        }
        holder.binding.imgsub.setOnClickListener {
            changeNumListener.changeNum(position,false)
        }


    }

    fun getCartAt(position: Int): Cart {
        return cartList[position]
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDataCart(carts: ArrayList<Cart>){
        this.cartList = carts
        notifyDataSetChanged()
    }
}