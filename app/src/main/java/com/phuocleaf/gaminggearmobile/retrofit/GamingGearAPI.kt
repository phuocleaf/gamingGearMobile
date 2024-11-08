package com.phuocleaf.gaminggearmobile.retrofit

import com.phuocleaf.gaminggearmobile.model.DangKyData
import com.phuocleaf.gaminggearmobile.model.DangKyResponse
import com.phuocleaf.gaminggearmobile.model.DangNhapData
import com.phuocleaf.gaminggearmobile.model.DangNhapResponse
import com.phuocleaf.gaminggearmobile.model.PhanLoai
import com.phuocleaf.gaminggearmobile.model.SanPham
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface GamingGearAPI {
    @GET("phanloai")
    fun getPhanLoai(): Call<PhanLoai>

    @GET("sanpham/laysanpham/laysanphamvahinhanh")
    fun getSanPham(): Call<SanPham>

    @POST("khachhang/dangky")
    fun DangKy(
        @Body dangKyData: DangKyData
    ):Call<DangKyResponse>

    @POST("khachhang/dangnhap")
    fun DangNhap(
        @Body dangNhapData: DangNhapData
    ):Call<DangNhapResponse>
}