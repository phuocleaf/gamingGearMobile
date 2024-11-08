package com.phuocleaf.gaminggearmobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.phuocleaf.gaminggearmobile.model.DangNhapData
import com.phuocleaf.gaminggearmobile.model.DangNhapResponse
import com.phuocleaf.gaminggearmobile.retrofit.RetrofitClient

class DangNhapViewModel(): ViewModel() {

    private var signInResponse = MutableLiveData<DangNhapResponse>()

    fun signIn(dangNhapData: DangNhapData) {

        RetrofitClient.api.DangNhap(dangNhapData).enqueue(object : retrofit2.Callback<DangNhapResponse> {
            override fun onResponse(call: retrofit2.Call<DangNhapResponse>, response: retrofit2.Response<DangNhapResponse>) {
                response.body()?.let {
                    signInResponse.value = it
                }
            }

            override fun onFailure(call: retrofit2.Call<DangNhapResponse>, t: Throwable) {
                signInResponse.value = DangNhapResponse(
                    active = false,
                    dangnhap = false,
                    id = "",
                    name = ""
                )
            }
        })
    }

    fun observerSignInResponseData(): LiveData<DangNhapResponse> {
        return signInResponse
    }
}