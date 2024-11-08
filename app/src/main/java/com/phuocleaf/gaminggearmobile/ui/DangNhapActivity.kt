package com.phuocleaf.gaminggearmobile.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.phuocleaf.gaminggearmobile.R
import com.phuocleaf.gaminggearmobile.databinding.ActivityDangNhapBinding
import com.phuocleaf.gaminggearmobile.model.DangNhapData
import com.phuocleaf.gaminggearmobile.model.DangNhapResponse
import com.phuocleaf.gaminggearmobile.viewmodel.DangNhapViewModel
import io.paperdb.Paper

private lateinit var binding: ActivityDangNhapBinding
private lateinit var viewModel: DangNhapViewModel

class DangNhapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDangNhapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this)[DangNhapViewModel::class.java]

        Paper.init(this)

        binding.btnsignin.setOnClickListener{
            val email = binding.etemail.text.toString().trim()
            val password = binding.etpassword.text.toString().trim()

            val dangNhapData = DangNhapData(
                email = email,
                password = password
            )

            viewModel.signIn(dangNhapData)
            observeSignInStatus()
        }

        binding.textButtonSignUp.setOnClickListener{
            val intent = Intent(this, DangKyActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observeSignInStatus(){
        val signInObserver = Observer<DangNhapResponse> { signInResponse ->
            if(signInResponse.dangnhap){
                Paper.init(this)
                Paper.book().write("user_id", signInResponse.id)
                Paper.book().write("user_name", signInResponse.name)
                Toast.makeText(this,"Đăng nhập thành công", Toast.LENGTH_LONG).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else{
                Toast.makeText(this, "có lỗi xảy ra", Toast.LENGTH_LONG).show()
            }
        }
        viewModel.observerSignInResponseData().observe(this, signInObserver)
    }
}