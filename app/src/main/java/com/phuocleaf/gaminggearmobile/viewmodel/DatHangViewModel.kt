package com.phuocleaf.gaminggearmobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.phuocleaf.gaminggearmobile.model.DatHangData
import com.phuocleaf.gaminggearmobile.model.DiaChiDataItem
import com.phuocleaf.gaminggearmobile.model.DonHangResponse
import com.phuocleaf.gaminggearmobile.retrofit.RetrofitClient

class DatHangViewModel(): ViewModel() {
    private var orderData = DatHangData(
        userId = "",
        userAddress = DiaChiDataItem("", "", "", "", ""),
        userName = "",
        userPhone = "",
        cartList = emptyList(),
        total = 0,
        userNote = ""
    )

    private var orderDataResponse = MutableLiveData<DonHangResponse>()

    fun createOrder(orderData: DatHangData) {
        RetrofitClient.api.createOrder(orderData).enqueue(object : retrofit2.Callback<DonHangResponse> {
            override fun onResponse(call: retrofit2.Call<DonHangResponse>, response: retrofit2.Response<DonHangResponse>) {
                response.body()?.let {
                    orderDataResponse.value = it
                }
            }

            override fun onFailure(call: retrofit2.Call<DonHangResponse>, t: Throwable) {
                orderDataResponse.value = DonHangResponse(
                    success = false,
                )
            }
        })
    }
    fun setOrderData(orderData: DatHangData) {
        this.orderData = orderData
    }

    fun observeOrderDataResponse(): LiveData<DonHangResponse> {
        return orderDataResponse
    }
}