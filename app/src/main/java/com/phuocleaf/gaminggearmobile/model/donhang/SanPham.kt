package com.phuocleaf.gaminggearmobile.model.donhang

data class SanPham(
    val _id: String,
    val costPrice: String,
    val created_at: String,
    val description: String,
    val images: List<Image>,
    val name: String,
    val phanloai: List<Phanloai>,
    val phanloaiId: String,
    val quantity: Int,
    val salePrice: String,
    val updated_at: String
)