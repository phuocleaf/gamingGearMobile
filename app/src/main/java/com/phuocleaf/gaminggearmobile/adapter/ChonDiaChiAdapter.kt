package com.phuocleaf.gaminggearmobile.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.phuocleaf.gaminggearmobile.databinding.ItemDiaChiBinding
import com.phuocleaf.gaminggearmobile.databinding.ItemPhanLoaiBinding
import com.phuocleaf.gaminggearmobile.model.DiaChiDataItem
import com.phuocleaf.gaminggearmobile.model.PhanLoaiItem
import com.phuocleaf.gaminggearmobile.onclick.OnclickInterface

class ChonDiaChiAdapter(private val onclick: OnclickInterface): RecyclerView.Adapter<ChonDiaChiAdapter.MyViewHolder>() {
    private var diaChiList = ArrayList<DiaChiDataItem>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemDiaChiBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return diaChiList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.txtDiaChi.text = diaChiList[position].desc

        holder.itemView.setOnClickListener {
            onclick.onClick(position)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDataDiaChiList(list: ArrayList<DiaChiDataItem>){
        this.diaChiList = list
        notifyDataSetChanged()
    }

    class MyViewHolder(var binding: ItemDiaChiBinding): RecyclerView.ViewHolder(binding.root) {

    }

    fun getDiaChiItemAt(position: Int): DiaChiDataItem {
        return diaChiList[position]
    }

    fun getDiaChiList(): ArrayList<DiaChiDataItem>{
        return diaChiList
    }
}