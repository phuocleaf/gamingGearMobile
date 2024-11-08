package com.phuocleaf.gaminggearmobile.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.phuocleaf.gaminggearmobile.model.PhanLoai
import com.phuocleaf.gaminggearmobile.model.PhanLoaiItem
import com.phuocleaf.gaminggearmobile.model.SanPham
import com.phuocleaf.gaminggearmobile.model.SanPhamItem
import com.phuocleaf.gaminggearmobile.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(): ViewModel() {
    private var phanLoaiLiveData = MutableLiveData<List<PhanLoaiItem>>()
    private var sanPhamLiveData = MutableLiveData<List<SanPhamItem>>()

    val phanLoaiTatCa: PhanLoaiItem = PhanLoaiItem(
        _id = "1",
        created_at = "2024-10-15",
        name = "",
        updated_at = "2024-10-15"
    )




    fun getPhanLoai() {
        RetrofitClient.api.getPhanLoai().enqueue(object : Callback<PhanLoai> {
            override fun onResponse(call: Call<PhanLoai>, response: Response<PhanLoai>) {
//                response.body()?.let { PhanLoai ->
//                    phanLoaiLiveData.postValue(PhanLoai)
//                }
                response.body()?.let { phanLoai ->
                    // Tạo danh sách mới và thêm phần tử "Tất cả sản phẩm" vào đầu
                    val updatedList = mutableListOf(phanLoaiTatCa)
                    updatedList.addAll(phanLoai) // Thêm các phần tử từ API vào sau

                    // Cập nhật giá trị cho phanLoaiLiveData
                    phanLoaiLiveData.postValue(updatedList)
                }
            }

            override fun onFailure(call: Call<PhanLoai>, t: Throwable) {
                Log.e("logg", t.message.toString())
            }
        })
    }

    fun getSanPham() {
        RetrofitClient.api.getSanPham().enqueue(object : Callback<SanPham> {
            override fun onResponse(call: Call<SanPham>, response: Response<SanPham>) {
                response.body()?.let { sanPham ->
                    sanPhamLiveData.postValue(sanPham)
                }
            }

            override fun onFailure(call: Call<SanPham>, t: Throwable) {
                Log.e("logg", t.message.toString())
            }
        })
    }

    fun observerPhanLoaiLiveData(): LiveData<List<PhanLoaiItem>> {
        return phanLoaiLiveData
    }

    fun observerSanPhamLiveData(): LiveData<List<SanPhamItem>> {
        return sanPhamLiveData
    }
}