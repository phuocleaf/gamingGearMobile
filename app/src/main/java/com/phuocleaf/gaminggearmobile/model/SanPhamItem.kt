package com.phuocleaf.gaminggearmobile.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SanPhamItem(
    val _id: String,
    val costPrice: String,
    val created_at: String,
    val description: String,
    val images: List<Image>,
    val name: String,
    val phanloai: List<PhanLoaiItem>,
    val phanloaiId: String,
    val quantity: Int,
    val salePrice: String,
    val updated_at: String
) : Parcelable