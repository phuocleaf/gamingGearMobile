package com.phuocleaf.gaminggearmobile.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.phuocleaf.gaminggearmobile.databinding.ItemHinhAnhBinding
import com.phuocleaf.gaminggearmobile.databinding.ItemSanPhamBinding
import com.phuocleaf.gaminggearmobile.model.Image
import com.phuocleaf.gaminggearmobile.model.SanPhamItem
import com.phuocleaf.gaminggearmobile.onclick.OnclickInterface
import com.phuocleaf.gaminggearmobile.util.Util

class HinhAnhAdapter(private val onclick: OnclickInterface): RecyclerView.Adapter<HinhAnhAdapter.MyViewHolder>() {
    private var hinhAnhList = ArrayList<Image>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemHinhAnhBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return hinhAnhList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        val imageURL = Util.BASE_URL + "hinhanh/" + hinhAnhList[position].path
        Glide.with(holder.itemView).load(imageURL).into(holder.binding.imgHinhAnh)

        holder.itemView.setOnClickListener {
            onclick.onClick(position)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDataHinhAnhList(list: ArrayList<Image>){
        this.hinhAnhList = list
        notifyDataSetChanged()
    }

    class MyViewHolder(var binding: ItemHinhAnhBinding): RecyclerView.ViewHolder(binding.root) {

    }

    fun getHinhAnhItemAt(position: Int): Image {
        return hinhAnhList[position]
    }

    fun getHinhAnhList(): ArrayList<Image>{
        return hinhAnhList
    }
}