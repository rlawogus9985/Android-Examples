package com.example.calculator_mvvm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.calculator_mvvm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    var viewModel = ViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this)[ViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        setObserve()
    }

    fun setObserve(){
        viewModel.expText.observe(this, Observer {
//            Toast.makeText(this, "$it 번을 클릭했습니다.", Toast.LENGTH_SHORT).show()
//            binding.expressionTextView.text = it
        })
    }
}