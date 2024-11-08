package com.phuocleaf.gaminggearmobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.phuocleaf.gaminggearmobile.model.DangKyData
import com.phuocleaf.gaminggearmobile.model.DangKyResponse
import com.phuocleaf.gaminggearmobile.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Response

class DangKyViewModel(): ViewModel() {

    private var isSignUp = MutableLiveData<Boolean>()

    fun signUp(dangKyData: DangKyData) {
        RetrofitClient.api.DangKy(dangKyData).enqueue(object : retrofit2.Callback<DangKyResponse> {
            override fun onResponse(
                call: Call<DangKyResponse>,
                response: Response<DangKyResponse>
            ) {
                response.body()?.let { signUpResponse ->
                    isSignUp.value = signUpResponse.dangky
                }
            }

            override fun onFailure(call: retrofit2.Call<DangKyResponse>, t: Throwable) {
                isSignUp.value = false
            }
        })
    }

    fun observeSignUpStatus(): LiveData<Boolean> {
        return isSignUp
    }

}