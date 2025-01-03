package com.phuocleaf.gaminggearmobile.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.phuocleaf.gaminggearmobile.model.donhang.DonHang
import com.phuocleaf.gaminggearmobile.model.donhang.DonHangItem
import com.phuocleaf.gaminggearmobile.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewDonHangViewModel(): ViewModel() {
    private var reviewOrderLiveData = MutableLiveData<List<DonHangItem>>()

    fun getReviewOrder(id: String, status: String) {
        RetrofitClient.api.getOrderList(id, status).enqueue(object : Callback<DonHang> {
            override fun onResponse(call: Call<DonHang>, response: Response<DonHang>) {
                response.body()?.let { reviewOrder ->
                    reviewOrderLiveData.postValue(reviewOrder)
                }
            }

            override fun onFailure(call: Call<DonHang>, t: Throwable) {
                Log.e("logg", t.message.toString())
            }
        })
    }

    fun observerReviewOrderLiveData(): LiveData<List<DonHangItem>> {
        return reviewOrderLiveData
    }
}