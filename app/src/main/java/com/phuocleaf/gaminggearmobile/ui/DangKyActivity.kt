package com.phuocleaf.gaminggearmobile.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.phuocleaf.gaminggearmobile.R
import com.phuocleaf.gaminggearmobile.databinding.ActivityDangKyBinding
import android.app.DatePickerDialog
import android.content.Intent
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.phuocleaf.gaminggearmobile.model.DangKyData
import com.phuocleaf.gaminggearmobile.viewmodel.DangKyViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityDangKyBinding
private lateinit var viewModel: DangKyViewModel

class DangKyActivity : AppCompatActivity() {
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDangKyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this)[DangKyViewModel::class.java]

        binding.etdateOfBirth.setOnClickListener {
            // Tạo MaterialDatePicker với kiểu trượt dọc
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds()) // Lấy ngày hôm nay ở UTC
                .build()

            datePicker.addOnPositiveButtonClickListener { selection ->
                // Chuyển đổi giá trị chọn thành Date
                val date = Date(selection)

                // Cập nhật lại múi giờ của hệ thống (lấy múi giờ mặc định của người dùng)
                val calendar = Calendar.getInstance()
                calendar.time = date
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

                // Định dạng lại ngày và hiển thị lên EditText
                binding.etdateOfBirth.setText(dateFormat.format(calendar.time))
            }

            datePicker.show(supportFragmentManager, datePicker.toString())
        }

        binding.textButtonSignIn.setOnClickListener {
            val intent = Intent(this, DangNhapActivity::class.java)
            startActivity(intent)
        }

        binding.btnRegister.setOnClickListener {

            val name = binding.etname.text.toString().trim()
            val email = binding.etemail.text.toString().trim()
            val password = binding.etpassword.text.toString().trim()
            val phone = binding.etphone.text.toString().trim()
            val dateOfBirth = binding.etdateOfBirth.text.toString().trim()
            val sex = if (binding.rbMale.isChecked) "male" else "female"

            val dangKyData = DangKyData(
                name = name,
                email = email,
                password = password,
                phone = phone,
                dateOfBirth = dateOfBirth,
                sex = sex
            )

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty() || dateOfBirth.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.signUp(dangKyData)
                observeSignUpStatus()
            }
        }

    }

    private fun observeSignUpStatus(){
        val signUpObserver = Observer<Boolean> { isSignup ->
            if(isSignup){
                Toast.makeText(this,"Đăng kí thành công", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, DangNhapActivity::class.java)
                startActivity(intent)
            } else{
                Toast.makeText(this, "Email đã tồn tại", Toast.LENGTH_SHORT).show()
            }

        }
        viewModel.observeSignUpStatus().observe(this, signUpObserver)
    }
}