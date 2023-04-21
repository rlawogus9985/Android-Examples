package com.example.calculator_mvvm

import android.app.Application
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.calculator_mvvm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var calViewModel = CalViewModel(Application())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        calViewModel = ViewModelProvider(this).get(CalViewModel::class.java)
        binding.calViewModel = calViewModel
        binding.lifecycleOwner = this
        setObserve()
    }

    fun setObserve(){
        calViewModel.showToastEvent.observe(this) {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
    }

}


