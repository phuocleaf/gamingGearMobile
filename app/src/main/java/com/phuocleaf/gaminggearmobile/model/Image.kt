package com.phuocleaf.gaminggearmobile.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(
    val _id: String,
    val created_at: String,
    val idSanPham: String,
    val path: String,
    val updated_at: String
) : Parcelable