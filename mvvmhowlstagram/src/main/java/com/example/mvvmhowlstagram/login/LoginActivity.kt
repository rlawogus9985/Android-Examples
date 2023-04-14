package com.example.mvvmhowlstagram.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mvvmhowlstagram.R
import com.example.mvvmhowlstagram.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

//    lateinit var loginViewModel: LoginViewModel
    val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        // binding과 loginviewModel을 MainActivity의 lifeCycle을 따라가게 만들기기
//        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        binding.viewModel = loginViewModel
        binding.activity = this
        binding.lifecycleOwner = this
        setObserve()
    }
    fun setObserve(){
        loginViewModel.showInputNumberActivity.observe(this){
            if(it){
                finish()
                startActivity(Intent(this, InputNumberActivity::class.java))
            }
        }
        loginViewModel.showFindIdActivity.observe(this){
            if(it){
                startActivity(Intent(this, FindIdActivity::class.java))
            }
        }
    }

    fun loginEmail(){
        println("Email")
        loginViewModel.showInputNumberActivity.value = true
    }
    fun findId() {
        println("findId")
        loginViewModel.showFindIdActivity.value = true
    }
}