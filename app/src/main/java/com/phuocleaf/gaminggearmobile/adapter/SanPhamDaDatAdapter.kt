package com.phuocleaf.gaminggearmobile.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.phuocleaf.gaminggearmobile.databinding.ItemSanPhamDaDatBinding
import com.phuocleaf.gaminggearmobile.model.donhang.Cart
import com.phuocleaf.gaminggearmobile.util.Util

class SanPhamDaDatAdapter(): RecyclerView.Adapter<SanPhamDaDatAdapter.OrderClothesViewHolder>() {

    private var detailList = ArrayList<Cart>()

    inner class OrderClothesViewHolder(var binding: ItemSanPhamDaDatBinding): RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SanPhamDaDatAdapter.OrderClothesViewHolder {
        return OrderClothesViewHolder(ItemSanPhamDaDatBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: SanPhamDaDatAdapter.OrderClothesViewHolder,
        position: Int
    ) {
//        holder.binding.txtClothesName.text = detailList[position].name
//        holder.binding.txtClothesPrice.text = "Giá: " + detailList[position].price.toString()
//        holder.binding.txtquantity.text = "Số lượng: "+ detailList[position].quantity.toString()
//        holder.binding.txtClothesSize.text = "Size: "+ detailList[position].Size
//
//        val imageURL = Utils.BASE_URL + "image/" + detailList[position].image
//        Glide.with(holder.itemView).load(imageURL).into(holder.binding.imgClothes)
        holder.binding.txttensanpham.text = detailList[position].sanPham.name
        holder.binding.txtgiasanpham.text = detailList[position].sanPham.salePrice.toString()
        holder.binding.txtSoLuong.text = "Số lượng: " + detailList[position].amount.toString()

        val imageURL = Util.BASE_URL + "hinhanh/" + detailList[position].sanPham.images[0].path
        Glide.with(holder.itemView).load(imageURL).into(holder.binding.imgsanpham)
    }

    override fun getItemCount(): Int {
        return detailList.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setDataDetail(details: ArrayList<Cart>){
        this.detailList = details
        notifyDataSetChanged()
    }
}