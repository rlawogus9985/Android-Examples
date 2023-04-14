package com.example.mvvmtest

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.lifecycle.Observer
import com.example.mvvmtest.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    var viewModel = ViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.viewModel = viewModel

        viewModel.toastMessage.observe(this, Observer {
            Toast.makeText(this, "$it 번을 클릭했습니다.", Toast.LENGTH_SHORT).show()
        })
        viewModel.checkPasswordMessage.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if(viewModel.checkPasswordMessage.get() == true){
                    binding.messageSuccess.visibility = View.VISIBLE
                } else {
                    binding.messageSuccess.visibility= View.GONE
                }
            }
        })
    }

    // 클릭동작을 ViewModel에 정의
//    fun clickNumber(i: Int){
//        presenter.clickNumber(i)
//    }

//    override fun toastMessage(i: Int) {
//
//    }
//
//    override fun checkPasswordMessage() {
//        binding.messageSuccess.visibility = View.VISIBLE
//    }


}