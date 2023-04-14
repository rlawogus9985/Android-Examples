package com.example.mvvmtest

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData

class ViewModel {
    // viewInterface에 선언했던 함수들을 변수로 선언

    var toastMessage = MutableLiveData<Int>()
    var checkPasswordMessage = ObservableField<Boolean>(false)

    // presenter의 값들을 다 가져옴
    // controller단에서 model을 가져온 부분을 다시 presenter로 가져옴
    var model = Model()

    // controller단에서 view를 조작하는 함수를 가져옴
    // 그대로 가져왔을때 activity가 없기 때문에 오류나는 부분을 interface로 작성해줘서 뭘 만들지 정의해주고
    // controller에서 그 인터페이스를 상속받게 한 후 구체화시고 Presenter가
    // 그 재정의된 인터페이스를 가져오는 방식을 통해서 구현함.
    fun clickNumber(i: Int){
        // presenter가 activity를 가지고있지 않기 때문에 인터페이스르 통해서 activity로 넘겨준다
        toastMessage.value = i
        model.inputPassword(i)

        if(model.password.size == 4 && model.checkPassword()){
            // 4자리 이상 비밀번호가 savePassword에 정해진 것일때
            checkPasswordMessage.set(true)
        }

    }
    // viewinterface와 presenter파일 ㅈ삭제
}