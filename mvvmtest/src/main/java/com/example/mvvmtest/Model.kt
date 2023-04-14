package com.example.mvvmtest

class Model {
    var password: MutableList<Int> = mutableListOf()

    fun inputPassword(i: Int){
        if(password.size <= 4){
            password.add(i)
        }
    }
    fun checkPassword(): Boolean {
        var trueCount = 0
        var savePassword = mutableListOf(1,2,3,4)

        for(i in 0 until savePassword.size){
            if(savePassword.get(i) == password.get(i)){
                trueCount++
            }
        }

        return trueCount == 4
    }
}