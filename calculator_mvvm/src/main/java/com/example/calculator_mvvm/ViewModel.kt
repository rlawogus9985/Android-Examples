package com.example.calculator_mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ViewModel: androidx.lifecycle.ViewModel() {

    var model = Model()

    private val _expText = MutableLiveData<String>()
    val expText:LiveData<String>
        get() = _expText

    private val _resText = MutableLiveData<String>()
    val resText:LiveData<String>
        get() = _resText

    fun clickNumber(i: String){

        var splitedExpTexts = model.splitExp(model.expressionTexts)

        if(splitedExpTexts.isEmpty()){
            if (i == "."){
                model.expressionTexts += "0"
            }
        } else {
            if(splitedExpTexts.last() == "%"){
                if(i == "."){
                    model.expressionTexts += "*0"
                } else {
                    model.expressionTexts += "*"
                }
            } else if(splitedExpTexts.last() in "+-*/"){
                if(i == "."){
                    model.expressionTexts += "0"
                }
            }
            if(splitedExpTexts.last().contains(".")){
                if(splitedExpTexts.last().length >= 16){

                } else if (splitedExpTexts.last().split(".").last().length >= 10 ){

                } else if (i  == "."){
                    return
                }
            } else {
                if(splitedExpTexts.last().length >= 15){

                } else if (splitedExpTexts.last() == "0"){
                    if (i != "."){
                        model.expressionTexts = model.expressionTexts.dropLast(1)
                    }
                }
            }
        }
        model.expressionTexts += i
        _expText.value = "${model.expressionTexts}"
        _resText.value = model.calculateExpression(model.expressionTexts)
    }
    fun clickOperator(i: String){

        if( model.expressionTexts.isEmpty()){
            return
        }
        when{
            model.expressionTexts.last() in "+-*/" -> {
                model.expressionTexts = model.expressionTexts.dropLast(1)
            }
            i == "%" -> {
                if(model.expressionTexts.last() == '%'){
                    // 토스트메시지
                    return
                }
            }
        }

        model.expressionTexts += i
        _expText.value = "${model.expressionTexts}"
        if (i=="%"){
            _resText.value = model.calculateExpression(model.expressionTexts)
        } else {
            _resText.value = ""
        }
    }

    fun clickClear(){
        model.expressionTexts = ""
        _expText.value = ""
        _resText.value = ""
    }
    fun clickCancle(){
        model.expressionTexts = model.expressionTexts.dropLast(1)
        _expText.value = "${model.expressionTexts}"

        if(model.expressionTexts.length == 0){
            _resText.value = ""
            return
        }

        if(!(model.expressionTexts.last() in "+-*/")){
            _resText.value = model.calculateExpression(model.expressionTexts)
        } else {
            _resText.value = ""
        }
    }
    fun clickEquality(){
        if (model.expressionTexts.isEmpty() || model.expressionTexts.last() in "+-*/"){
            return
        }

        model.expressionTexts = _resText.value.toString()
        _expText.value = "${model.expressionTexts}"
        _resText.value = ""
    }
}