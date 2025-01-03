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
import com.phuocleaf.gaminggearmobile.adapter.DonHangAdapter
import com.phuocleaf.gaminggearmobile.databinding.FragmentChoXacNhanBinding
import com.phuocleaf.gaminggearmobile.databinding.FragmentDangVanChuyenBinding
import com.phuocleaf.gaminggearmobile.model.donhang.DonHangItem
import com.phuocleaf.gaminggearmobile.onclick.OnclickInterface
import com.phuocleaf.gaminggearmobile.ui.ChiTietDonDaDatActivity
import com.phuocleaf.gaminggearmobile.viewmodel.ReviewDonHangViewModel
import io.paperdb.Paper


class DangVanChuyenFragment : Fragment() {

    private lateinit var reviewDonHangViewModel: ReviewDonHangViewModel
    private lateinit var binding: FragmentDangVanChuyenBinding
    private lateinit var donHangAdapter: DonHangAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reviewDonHangViewModel = ViewModelProvider(this)[ReviewDonHangViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentDangVanChuyenBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        Paper.init(requireContext())
        val userId = Paper.book().read("user_id", "").toString()
        reviewDonHangViewModel.getReviewOrder(userId,"Đang vận chuyển")
        observerLiveData()
    }

    override fun onResume() {
        super.onResume()
        Paper.init(requireContext())
        val userId = Paper.book().read("user_id", "").toString()
        reviewDonHangViewModel.getReviewOrder(userId,"Đang vận chuyển")
        observerLiveData()
    }

    private fun initView() {
        donHangAdapter = DonHangAdapter(object : OnclickInterface {
            override fun onClick(position: Int) {
                val order = donHangAdapter.getOrderAt(position)
                Paper.init(requireContext())
                Paper.book().write("order", order)
                val intent = Intent(requireContext(), ChiTietDonDaDatActivity::class.java)

                startActivity(intent)
                requireActivity().finish()
            }

        })
        binding.recyclerOrder.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = donHangAdapter
        }
    }

    private fun observerLiveData() {
        reviewDonHangViewModel.observerReviewOrderLiveData().observe(viewLifecycleOwner) {
            donHangAdapter.setDataOrder(it as ArrayList<DonHangItem>)
        }
    }

}