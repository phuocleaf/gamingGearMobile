package com.phuocleaf.gaminggearmobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.phuocleaf.gaminggearmobile.model.KhachHangData
import com.phuocleaf.gaminggearmobile.retrofit.RetrofitClient

class KhachHangViewModel(): ViewModel() {
    private var userInfo = MutableLiveData<KhachHangData>()

    fun getUserInfo(id: String) {
        // get user info from server
        RetrofitClient.api.getKhachHang(id).enqueue(object : retrofit2.Callback<KhachHangData> {
            override fun onResponse(call: retrofit2.Call<KhachHangData>, response: retrofit2.Response<KhachHangData>) {
                response.body()?.let {
                    userInfo.value = it
                }
            }

            override fun onFailure(call: retrofit2.Call<KhachHangData>, t: Throwable) {
                userInfo.value = KhachHangData(
                     _id = "",
                 active=  false,
                 created_at=  "",
                 dateOfBirth= "",
                 email= "",
                 name= "",
                 password= "",
                 phone= "",
                 sex= "",
                 updated_at= ""
                )
            }
        })
    }

    fun observeUserInfo(): LiveData<KhachHangData> {
        return userInfo
    }
}