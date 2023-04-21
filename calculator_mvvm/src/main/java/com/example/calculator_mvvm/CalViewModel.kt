package com.example.calculator_mvvm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class CalViewModel(app : Application) : AndroidViewModel(app) {

    var model = Model()

    private val _expText = MutableLiveData<String>()
    val expText:LiveData<String>
        get() = _expText

    private val _resText = MutableLiveData<String>()
    val resText:LiveData<String>
        get() = _resText

    private val _showToastEvent = MutableLiveData<String>()
    val showToastEvent: LiveData<String>
        get() = _showToastEvent

    private var isKeepEquation = false

    fun clickNumber(i: String){

        model.previousExpression.clear()
        if(isKeepEquation){
            model.expressionTexts = _expText.value.toString()
        }
        isKeepEquation = false

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
                    _showToastEvent.value = "15자리까지 입력할 수 있어요."
                    return
                } else if (splitedExpTexts.last().split(".").last().length >= 10 ){
                    _showToastEvent.value = "소수점 이하 10자리까지 입력할 수 있어요."
                    return
                } else if (i  == "."){
                    return
                }
            } else {
                if(splitedExpTexts.last().length >= 15){
                    _showToastEvent.value = "15자리까지 입력할 수 있어요."
                    return
                } else if (splitedExpTexts.last() == "0"){
                    if (i != "."){
                        model.expressionTexts = model.expressionTexts.dropLast(1)
                    }
                }
            }
        }
        model.expressionTexts += i
        _expText.value = model.expressionTexts
        _resText.value = model.calculateExpression(model.expressionTexts)
    }
    fun clickOperator(i: String){

        model.previousExpression.clear()
        if(isKeepEquation){
            model.expressionTexts = _expText.value.toString()
        }
        isKeepEquation = false

        if( model.expressionTexts.isEmpty()){
            _showToastEvent.value = "완성되지 않은 수식입니다."
            return
        }
        if(model.expressionTexts.last() in "+-*/"){
            model.expressionTexts = model.expressionTexts.dropLast(1)
        }
        if (model.expressionTexts.isEmpty() || model.expressionTexts.last() == '%' && i == "%") {
            _showToastEvent.value = "완성되지 않은 수식입니다."
            return
        }

        model.expressionTexts += i
        _expText.value = model.expressionTexts
        if (i=="%"){
            _resText.value = model.calculateExpression(model.expressionTexts)
        } else {
            _resText.value = ""
        }
    }

    fun clickClear(){
        isKeepEquation = false
        model.previousExpression.clear()
        model.expressionTexts = ""
        _expText.value = ""
        _resText.value = ""
    }
    fun clickCancle(){

        isKeepEquation = false
        model.previousExpression.clear()

        model.expressionTexts = model.expressionTexts.dropLast(1)
        _expText.value = model.expressionTexts

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
    fun clickEquality() {
        if (model.expressionTexts.isEmpty() || model.expressionTexts.last() in "+-*/"){
            return
        }

        if (isKeepEquation){
            when{
                model.previousExpression[1] == "%" -> {
                    model.expressionTexts += "*0.01"
                    _expText.value = model.calculateExpression(model.expressionTexts)
                }
                else -> {
                    model.expressionTexts += model.previousExpression[0] + model.previousExpression[1]
                    _expText.value = model.calculateExpression(model.expressionTexts)
                }
            }
            return
        }

        model.previousExpression.addAll(model.splitExp(model.expressionTexts).takeLast(2))

        model.expressionTexts = _resText.value.toString()
        _expText.value = model.expressionTexts
        _resText.value = ""
        isKeepEquation = true
    }

}