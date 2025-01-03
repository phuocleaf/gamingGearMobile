package com.phuocleaf.gaminggearmobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.phuocleaf.gaminggearmobile.model.DiaChiData
import com.phuocleaf.gaminggearmobile.model.KhachHangData
import com.phuocleaf.gaminggearmobile.retrofit.RetrofitClient

class DiaChiViewModel(): ViewModel() {
    private var userInfo = MutableLiveData<DiaChiData>()

    fun getDiaChi(id: String) {
        // get user info from server
        RetrofitClient.api.getDiaChi(id).enqueue(object : retrofit2.Callback<DiaChiData> {
            override fun onResponse(call: retrofit2.Call<DiaChiData>, response: retrofit2.Response<DiaChiData>) {
                response.body()?.let {
                    userInfo.value = it
                }
            }

            override fun onFailure(call: retrofit2.Call<DiaChiData>, t: Throwable) {
                userInfo.value = DiaChiData()
            }
        })
    }

    fun observeDiaChiInfo(): LiveData<DiaChiData> {
        return userInfo
    }
}