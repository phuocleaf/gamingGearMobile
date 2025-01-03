package com.phuocleaf.gaminggearmobile.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.phuocleaf.gaminggearmobile.databinding.ItemOrderReviewBinding
import com.phuocleaf.gaminggearmobile.model.donhang.DonHangItem
import com.phuocleaf.gaminggearmobile.onclick.OnclickInterface

class DonHangAdapter(private val onClick: OnclickInterface): RecyclerView.Adapter<DonHangAdapter.MyViewHolder>() {

    private var orderList = ArrayList<DonHangItem>()

    inner class MyViewHolder( var binding: ItemOrderReviewBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemOrderReviewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.tvorderid.text = orderList[position]._id.toString()
        val dateTime: String = orderList[position].created_at
        holder.binding.tvordertime.text = dateTime
        holder.binding.tvordertotal.text = orderList[position].total.toString()

        holder.itemView.setOnClickListener {
            onClick.onClick(position)
        }
    }

    fun getOrderAt(position: Int): DonHangItem {
        return orderList[position]
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setDataOrder(orders: ArrayList<DonHangItem>){
        this.orderList = orders
        notifyDataSetChanged()
    }
}