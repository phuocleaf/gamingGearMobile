package com.phuocleaf.gaminggearmobile.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.phuocleaf.gaminggearmobile.R
import com.phuocleaf.gaminggearmobile.adapter.PhanLoaiAdapter
import com.phuocleaf.gaminggearmobile.adapter.SanPhamAdapter
import com.phuocleaf.gaminggearmobile.databinding.FragmentHomeBinding
import com.phuocleaf.gaminggearmobile.model.PhanLoaiItem
import com.phuocleaf.gaminggearmobile.model.SanPhamItem
import com.phuocleaf.gaminggearmobile.onclick.OnclickInterface
import com.phuocleaf.gaminggearmobile.ui.ChiTietSanPhamActivity
import com.phuocleaf.gaminggearmobile.viewmodel.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var phanLoaiAdapter: PhanLoaiAdapter
    private lateinit var sanPhamAdapter: SanPhamAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        homeViewModel.getPhanLoai()
        homeViewModel.getSanPham()

        observerLiveData()


    }

    private fun initView() {

        phanLoaiAdapter = PhanLoaiAdapter(object: OnclickInterface{
            override fun onClick(position: Int) {
                if (phanLoaiAdapter.getPhanLoaiItemAt(position).name == "") {
                    binding.txtPhanLoaiHienTai.text = "Tất cả sản phẩm"

                    homeViewModel.observerSanPhamLiveData().observe(viewLifecycleOwner) { SanPham ->
                        sanPhamAdapter.setDataSanPhamList(SanPham as ArrayList<SanPhamItem>)
                    }

                } else {
                    binding.txtPhanLoaiHienTai.text = phanLoaiAdapter.getPhanLoaiItemAt(position).name

                    homeViewModel.observerSanPhamLiveData().observe(viewLifecycleOwner) { SanPham ->
                        val sanPhamFilter = SanPham.filter {sanPhamItem ->
                            sanPhamItem.phanloai.any { it.name.contains(binding.txtPhanLoaiHienTai.text, ignoreCase = true) }
                        }
                        sanPhamAdapter.setDataSanPhamList(sanPhamFilter as ArrayList<SanPhamItem>)
                    }
                }
            }

        } )

        binding.recyclerPhanLoai.apply {
            adapter = phanLoaiAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        sanPhamAdapter = SanPhamAdapter(object: OnclickInterface{
            override fun onClick(position: Int) {
                val sanPham = sanPhamAdapter.getSanPhamItemAt(position)
                val intent = Intent(context, ChiTietSanPhamActivity::class.java)
                intent.putExtra("sanpham", sanPham)

                startActivity(intent)
            }

        } )

        binding.recyclerSanPham.apply {
            adapter = sanPhamAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun observerLiveData() {
        homeViewModel.observerPhanLoaiLiveData().observe(viewLifecycleOwner) { PhanLoai ->
            phanLoaiAdapter.setDataPhanLoaiList(PhanLoai as ArrayList<PhanLoaiItem>)
        }

        homeViewModel.observerSanPhamLiveData().observe(viewLifecycleOwner) { SanPham ->
            sanPhamAdapter.setDataSanPhamList(SanPham as ArrayList<SanPhamItem>)
        }
    }
}