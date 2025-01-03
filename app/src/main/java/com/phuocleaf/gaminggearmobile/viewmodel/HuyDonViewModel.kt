package com.phuocleaf.gaminggearmobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.phuocleaf.gaminggearmobile.model.DangKyData
import com.phuocleaf.gaminggearmobile.model.DangKyResponse
import com.phuocleaf.gaminggearmobile.model.HuyDonResponse
import com.phuocleaf.gaminggearmobile.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Response

class HuyDonViewModel(): ViewModel() {

    private var huyDonResponse = MutableLiveData<Boolean>()

    fun HuyDon(id: String) {
        RetrofitClient.api.HuyDon(id).enqueue(object : retrofit2.Callback<HuyDonResponse> {
            override fun onResponse(
                call: Call<HuyDonResponse>,
                response: Response<HuyDonResponse>
            ) {
                response.body()?.let { signUpResponse ->
                    huyDonResponse.value = signUpResponse.success
                }
            }

            override fun onFailure(call: retrofit2.Call<HuyDonResponse>, t: Throwable) {
                huyDonResponse.value = false
            }
        })
    }

    fun observeHuyDonStatus(): LiveData<Boolean> {
        return huyDonResponse
    }

}