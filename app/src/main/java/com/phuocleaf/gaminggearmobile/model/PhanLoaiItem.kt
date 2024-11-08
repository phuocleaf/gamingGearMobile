package com.phuocleaf.gaminggearmobile.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhanLoaiItem(
    val _id: String,
    val created_at: String,
    val name: String,
    val updated_at: String
) : Parcelable