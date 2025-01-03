package com.phuocleaf.gaminggearmobile.model.donhang

data class DonHangItem(
    val _id: String,
    val cartList: List<Cart>,
    val created_at: String,
    val status: String,
    val total: Int,
    val updated_at: String,
    val userAddress: UserAddress,
    val userId: String,
    val userName: String,
    val userNote: String,
    val userPhone: String,
    val nguoiGiaoHangId: String,
    val nguoiGiaoHangName: String,

)