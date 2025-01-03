package com.phuocleaf.gaminggearmobile.model

class DatHangData(
    val userId : String,
    val userAddress: DiaChiDataItem,
    val userName: String,
    val userPhone: String,//
    val cartList: List<Cart>,//
    val total: Int,//
    val userNote: String//
)