package com.phuocleaf.gaminggearmobile.retrofit

import com.phuocleaf.gaminggearmobile.model.DangKyData
import com.phuocleaf.gaminggearmobile.model.DangKyResponse
import com.phuocleaf.gaminggearmobile.model.DangNhapData
import com.phuocleaf.gaminggearmobile.model.DangNhapResponse
import com.phuocleaf.gaminggearmobile.model.DatHangData
import com.phuocleaf.gaminggearmobile.model.DiaChiData
import com.phuocleaf.gaminggearmobile.model.DonHangResponse
import com.phuocleaf.gaminggearmobile.model.HuyDonResponse
import com.phuocleaf.gaminggearmobile.model.KhachHangData
import com.phuocleaf.gaminggearmobile.model.PhanLoai
import com.phuocleaf.gaminggearmobile.model.SanPham
import com.phuocleaf.gaminggearmobile.model.donhang.DonHang
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

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

    @GET("khachhang/{id}")
    fun getKhachHang(
        @Path ("id") id:String
    ):Call<KhachHangData>

    @GET("diachi/{id}")
    fun getDiaChi(
        @Path ("id") id:String
    ):Call<DiaChiData>

    @POST("donhang")
    fun createOrder(
        @Body datHangData: DatHangData
    ):Call<DonHangResponse>

    @GET("donhang/user/{id}/{status}")
    fun getOrderList(
        @Path("id") id:String,
        @Path("status") status: String
    ):Call<DonHang>


    @PUT("donhang/update/{id}/Đã hủy")
    fun HuyDon(
        @Path("id") id:String,
    ):Call<HuyDonResponse>
}