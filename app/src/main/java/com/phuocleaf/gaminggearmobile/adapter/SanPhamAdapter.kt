package com.phuocleaf.gaminggearmobile.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.phuocleaf.gaminggearmobile.databinding.ItemSanPhamBinding
import com.phuocleaf.gaminggearmobile.model.PhanLoaiItem
import com.phuocleaf.gaminggearmobile.model.SanPhamItem
import com.phuocleaf.gaminggearmobile.onclick.OnclickInterface
import com.phuocleaf.gaminggearmobile.util.Util

class SanPhamAdapter(private val onclick: OnclickInterface): RecyclerView.Adapter<SanPhamAdapter.MyViewHolder>() {
    private var sanPhamList = ArrayList<SanPhamItem>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemSanPhamBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return sanPhamList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.binding.txttensanpham.text = sanPhamList[position].name
        holder.binding.txtgiasanpham.text = sanPhamList[position].salePrice.toString()
        val imageURL = Util.BASE_URL + "hinhanh/" + sanPhamList[position].images[0].path
        Glide.with(holder.itemView).load(imageURL).into(holder.binding.imgsanpham)

        holder.itemView.setOnClickListener {
            onclick.onClick(position)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDataSanPhamList(list: ArrayList<SanPhamItem>){
        this.sanPhamList = list
        notifyDataSetChanged()
    }

    class MyViewHolder(var binding: ItemSanPhamBinding): RecyclerView.ViewHolder(binding.root) {

    }

    fun getSanPhamItemAt(position: Int): SanPhamItem {
        return sanPhamList[position]
    }

    fun getSanPhamList(): ArrayList<SanPhamItem>{
        return sanPhamList
    }
}