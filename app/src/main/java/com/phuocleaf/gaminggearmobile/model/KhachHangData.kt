package com.phuocleaf.gaminggearmobile.model

data class KhachHangData(
    val _id: String,
    val active: Boolean,
    val created_at: String,
    val dateOfBirth: String,
    val email: String,
    val name: String,
    val password: String,
    val phone: String,
    val sex: String,
    val updated_at: String
)