package com.phuocleaf.gaminggearmobile.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.phuocleaf.gaminggearmobile.databinding.ItemPhanLoaiBinding
import com.phuocleaf.gaminggearmobile.model.PhanLoaiItem
import com.phuocleaf.gaminggearmobile.onclick.OnclickInterface

class PhanLoaiAdapter(private val onclick: OnclickInterface): RecyclerView.Adapter<PhanLoaiAdapter.MyViewHolder>() {
    private var phanLoaiList = ArrayList<PhanLoaiItem>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemPhanLoaiBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return phanLoaiList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (phanLoaiList[position].name == ""){
            holder.binding.txtPhanLoai.text = "Tất cả sản phẩm"
        } else {
            holder.binding.txtPhanLoai.text = phanLoaiList[position].name
        }


        holder.itemView.setOnClickListener {
            onclick.onClick(position)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDataPhanLoaiList(list: ArrayList<PhanLoaiItem>){
        this.phanLoaiList = list
        notifyDataSetChanged()
    }

    class MyViewHolder(var binding: ItemPhanLoaiBinding): RecyclerView.ViewHolder(binding.root) {

    }

    fun getPhanLoaiItemAt(position: Int): PhanLoaiItem {
        return phanLoaiList[position]
    }

    fun getPhanLoaiList(): ArrayList<PhanLoaiItem>{
        return phanLoaiList
    }
}