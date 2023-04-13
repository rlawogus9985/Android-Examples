package com.example.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.room.Room
import com.example.calculator.databinding.ActivityMainBinding
import com.example.calculator.model.History
import java.math.RoundingMode
import java.util.*

// eval()함수를 사용하려면 스크립트 엔진을 사용해서 구현할 수 있다.
// 여기선 stack을 사용하여 처리

class MainActivity : AppCompatActivity() {

    // 뷰 바인딩을 사용할 때, setContentView를 호출하기 전에 binding 객체를 초기화해야한다.
    // binding 객체를 클래스 레벨에서 선언하는 것은 좋은 방법이 아니다.
    // binding 객체는 onCreate() 메서드에서 초기화해야 한다.
    private lateinit var binding: ActivityMainBinding

    // binding을 쓰기 위해..
    val expressionTextView: TextView by lazy {
        binding.expressionTextView
    }
    val resultTextView: TextView by lazy {
        binding.resultTextView
    }

    val historyLayout: View by lazy {
        binding.historyLayout
    }

    val historyLinearLayout: LinearLayout by lazy {
        binding.historyLinearLayout
    }

    lateinit var db: AppDatabase

    // 지금 숫자를 넣고있는지 기호를 넣고있는지 구분하는 변수
    private var isOperator = false

    private var isKeepEquation = false
    private var previousExpression = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 화면 출력
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "historyDB"
        ).build()

        // 그냥 버튼 클릭했을 때 이벤트 걸기
        val buttonIds = arrayOf(binding.button0, binding.button1, binding.button2, binding.button3,
        binding.button4, binding.button5, binding.button6, binding.button7, binding.button8, binding.button9,
        binding.dotButton, binding.plusButton, binding.minusButton, binding.multipyButton,
        binding.divisionbutton, binding.percentageButton)

        for (buttonId in buttonIds) {
            buttonId.setOnClickListener{
                when(buttonId) {
                    binding.button0 -> numberButtonClicked("0")
                    binding.button1 -> numberButtonClicked("1")
                    binding.button2 -> numberButtonClicked("2")
                    binding.button3 -> numberButtonClicked("3")
                    binding.button4 -> numberButtonClicked("4")
                    binding.button5 -> numberButtonClicked("5")
                    binding.button6 -> numberButtonClicked("6")
                    binding.button7 -> numberButtonClicked("7")
                    binding.button8 -> numberButtonClicked("8")
                    binding.button9 -> numberButtonClicked("9")
                    binding.dotButton -> numberButtonClicked(".")
                    binding.plusButton -> operatorButtonClicked("+")
                    binding.minusButton -> operatorButtonClicked("-")
                    binding.multipyButton -> operatorButtonClicked("*")
                    binding.percentageButton -> operatorButtonClicked("%")
                    binding.divisionbutton -> operatorButtonClicked("/")
                }
            }
        }

        // equationButton(부등호) 클릭. 람다식
        binding.equationButton.setOnClickListener {

            if (isKeepEquation){
                when {
                    previousExpression[1] == "%" -> {
                        expressionTextView.append(" * 0.01")
                        expressionTextView.text = calculateExpression()
                    }
                    else -> {
                        expressionTextView.append(" ${previousExpression[0]} ${previousExpression[1]}")
                        expressionTextView.text = calculateExpression()
                    }
                }
                return@setOnClickListener
            }

            val expressionTexts = expressionTextView.text.split(" ")

            // 비어있거나 숫자만 들어오는 경우 아무 동작 x
            if (expressionTexts.size <= 1){
                return@setOnClickListener
            }

            // 연산 기호로 끝나면 오류
            if ( expressionTexts.last() in "+-*/" ) {
                Toast.makeText(this,"완성되지 않은 수식입니다", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            previousExpression.addAll(expressionTexts.takeLast(2).joinToString(" ").split(" "))

            // 실질적 계산
            val expressionText = expressionTextView.text.toString()
            val resultText = calculateExpression()

            // DB는 새로운 쓰레드에서 해줘야한다.
            Thread(Runnable {
                db.historyDao().insertHistory(History(null,expressionText,resultText))
            }).start()


            resultTextView.text = ""
            expressionTextView.text = resultText

            isOperator = false
            isKeepEquation = true
        }

        // clearButton 클릭
        binding.clearButton.setOnClickListener{
            binding.expressionTextView.text = ""
            binding.resultTextView.text = ""
            isOperator = false
            isKeepEquation = false
            previousExpression.clear()
        }

        // cancleButton 클릭
        binding.cancleButton.setOnClickListener{
            isOperator = false
            isKeepEquation = false
            previousExpression.clear()

            val expressionTexts = expressionTextView.text.split(" ")

            if(expressionTexts.size == 0){
                return@setOnClickListener
            }

            expressionTextView.text = expressionTexts.dropLast(1).joinToString(" ")
            resultTextView.text = calculateExpression()
        }

        // history버튼은 이후 추가
        binding.historyButton.setOnClickListener{
            historyLayout.isVisible = true

            historyLinearLayout.removeAllViews()
            // DB에서 모든 기록 가져오기
            Thread(Runnable {
                db.historyDao().getAll().reversed().forEach{
                    // 뷰에 모든 기록 할당하기
                    runOnUiThread{
                        val historyView = LayoutInflater.from(this).inflate(R.layout.history_row, null, false)
                        historyView.findViewById<TextView>(R.id.expressionTextView).text = it.expression
                        historyView.findViewById<TextView>(R.id.resultTextView).text = "= ${it.result}"

                        historyLinearLayout.addView(historyView)
                    }
                }
            }).start()
        }
        binding.historyClearButton.setOnClickListener {
            historyLinearLayout.removeAllViews()

            Thread(Runnable {
                db.historyDao().deleteAll()
            }).start()
        }
        binding.historyCloseButton.setOnClickListener {
            historyLayout.isVisible = false
        }
    }

    // 일반 숫자를 눌렀을 때
    fun numberButtonClicked(number: String) {

        isKeepEquation = false
        previousExpression.clear()

        // 기호를 넣다가 왔으면 띄어쓰기를 통해 구분
        if (expressionTextView.text.split(" ").lastOrNull() in listOf("-","*","/","+")) {
            expressionTextView.append(" ")
        }
        isOperator = false

        // 띄어쓰기로 구분
        val expressionText = expressionTextView.text.split(" ")

        // %를 썼을 경우 숫자를 누르면 _x_를 앞에 붙이고 이후 숫자를 넣는다.
        if(expressionText.last() == "%"){
            if (number == ".") {
                expressionTextView.append(" * 0.")
                return
            } else {
                expressionTextView.append(" * ")
            }

        }

        // 소수점이 있을때와 없을 때를 구분해서 처리
        // 소수점을 맨 처음 입력했을 때
        if (expressionText.last().isEmpty() && number == "."){
            expressionTextView.append("0.")
            return
        }

        if (expressionText.last().contains(".")){ // 소수점을 포함할 때
            if(expressionText.last().length >= 16) { // 소수점은 포함 안해야 하니까 16자리
                Toast.makeText(this,"15자리 까지만 사용할 수 있습니다.", Toast.LENGTH_SHORT).show()
                return
            } else if (expressionText.last().split(".").last().length >= 10) { // 소수점 이하 10자리까지만 적게하기
                Toast.makeText(this,"소수점 이하 10자리까지 입력할 수 있습니다.", Toast.LENGTH_SHORT).show()
                return
            } else if (number ==".") { // .가 있는데 .를 다시 입력하는 경우 무시
                return
            }
        } else { // 소수점을 포함하지 않을 때
            if (expressionText.isNotEmpty() && expressionText.last().length >= 15){ // 소수점 없이 15자리를 넘어가면 오류
                Toast.makeText(this,"15자리 까지만 사용할 수 있습니다.", Toast.LENGTH_SHORT).show()
                return
            } else if (expressionText.last() == "0"){ // 맨처음 있는 숫자가 0이라면 새로 입력한 숫자로 바꾸고? 지우고? 리턴
                // 그런데 만약 입력한게 소수점이라면 그냥 .을 붙여준다.
                if (number == "."){expressionTextView.append(number)}
                else {
                    val text = expressionTextView.text.toString()
                    // 맨 끝 한자리 삭제하고 새로운 연산자로 추가
                    expressionTextView.text = text.dropLast(1) + number
                }
                return
            }
        }

        expressionTextView.append(number)

        // resultTextView에 실시간으로 계산 결과를 넣어야 하는 기능
        resultTextView.text = calculateExpression()
    }

    // 연산자 기호를 눌렀을 때
    fun operatorButtonClicked(operator: String) {

        isKeepEquation = false
        previousExpression.clear()

        // 처음부터 연산자를 클릭했을때 무시해야한다
        if (expressionTextView.text.isEmpty()){
            return
        }
        // 연산자를 바꾸기 위해서 취소 버튼을 누르거나 다른 연산자 버튼을 눌렀을때를 허용
        when {
            expressionTextView.text.split(" ").lastOrNull() in listOf("-","*","/","+") -> {
              val text = expressionTextView.text.toString()
                // 맨 끝 한자리 삭제하고 새로운 연산자로 추가
                // %일 경우에는 완성되지 않은 수식입니다. 오류 반환
//                if (operator == "%"){
//                    Toast.makeText(this,"완성되지 않은 수식입니다.", Toast.LENGTH_SHORT).show()
//                    return
//                }
              expressionTextView.text = text.dropLast(1) + operator
            }
            operator == "%" -> { // 연산자가 %는 두번 연속으로 나올 수 없고 %를 쓴 시점에 식을 계산해준다.
                // %의 isOperator는 항상 false다.
                if (expressionTextView.text.split(" ").last() == "%"){
                    Toast.makeText(this,"완성되지 않은 수식입니다.", Toast.LENGTH_SHORT).show()
                    return
                } else {
                    expressionTextView.append(" $operator")
                    resultTextView.text = calculateExpression()
                }
            }
            else -> {
                expressionTextView.append(" $operator")
            }
        }

        // %가 붙은 연산은 따로 x 0.01로 따로 처리 해주기 때문에 그 뒤에 바로 연산이 와야한다.
        // %를 붙이면 다른 기호를 누른다고 해서 바뀌면 안되기 대문에 위의 isOperator에서 벗어나기 위해서 true로 바꿔주지 않는다.
        if (operator != "%") {
            isOperator = true
        }
    }

    // expressionTextView에서 문자열을 그대로 가져와서 연산을 하기 위해 나누고 연산하기
    private fun calculateExpression(): String {
        val expressionTexts = expressionTextView.text.split(" ")

        // 연산 도중에는 ""를 출력해야한다.

        // 아무것도 입력하지 않았을때는 계산 반응이 없어야 하고 연산자로 끝나면 ""반환
        if (expressionTexts.size == 0 || expressionTexts.last() in "+-*/") {
            return ""
        }

        // 스택에 넣어서 처리
        val operandStack = Stack<Double>() // 피연산자 스택, 숫자
        val operatorStack = Stack<String>() // 연산자 스택

        for (ch in expressionTexts) {
            when (ch) {
                "(" -> operatorStack.push(ch) // 왼쪽 괄호면 연산자 스택에 넣기
                ")" -> { // 오른쪽 괄호면
                    while (operatorStack.peek() != "(") { // 왼쪽 괄호가 나올때까지
                        val op = operatorStack.pop() // 연산자를 꺼내고
                        val num2 = operandStack.pop() // 피연산자 두 개를 꺼내서
                        val num1 = operandStack.pop()
                        val result = calculate(num1, num2, op) // 계산하고
                        if (result!!.isInfinite()) return ""
                        operandStack.push(result) // 결과를 피연산자 스택에 넣기
                    }
                    operatorStack.pop() // 왼쪽 괄호 제거
                }
                in "+-*/" -> { // 일반 연산자면
                    while (operatorStack.isNotEmpty() && isHigherOrEqual(operatorStack.peek(), ch)) {
                        // 자신보다 우선순위가 높거나 같은 연산자들을 모두 꺼내고 연산
                        val op = operatorStack.pop() // 연산자를 꺼내고
                        val num2 = operandStack.pop() // 피연산자 두 개를 꺼내서
                        val num1 = operandStack.pop()
                        val result = calculate(num1, num2, op) // 계산하고
                        if (result!!.isInfinite()) return ""
                        operandStack.push(result) // 결과를 피연산자 스택에 넣기
                    }
                    operatorStack.push(ch) // 앞에 들어있는게 자신보다 우선순위가 낮으면 자신을 연산자 스택에 넣기
                }
                "%" -> { // 퍼센트 연산자면 그냥 앞의 자리에 연산하고 값을 저장. %는 스택에 저장 x
                    val num = operandStack.pop()
                    val result = num * 0.01
                    operandStack.push(result)
                }
                else -> operandStack.push(ch.toDouble())
            }
        }

        while (operatorStack.isNotEmpty()) { // 문자열을 모두 읽은 후에는 연산자 스택이 빌 때까지 반복
            val op = operatorStack.pop() // 연산자를 모두 꺼내고
            val num2 = operandStack.pop() // 피연산자 두 개를 꺼내서
            val num1 = operandStack.pop()
            val result = calculate(num1, num2, op) // 계산하고
            if (result!!.isInfinite()) return ""
            operandStack.push(result)
        }

        // 마지막 결과값
        // stripTrailingZeros() : 소수점 뒤의 0제거
        // toPlainString(): 숫자를 문자열로 변환. toString은 지수 형식으로 변환하지만 toPlainString은 십진수 형식으로 출력된다.
        // operandStack.pop()에서 double자료형으로 나온것을 두 함수를 사용하기위해 자료형 변경..
        // 소수점 10번재 자리까지만 출력
        return operandStack.pop().toBigDecimal().setScale(10, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString()

    }

}

// 두 수와 연산자를 받아서 계산하는 함수
fun calculate(num1: Double, num2: Double, op: String): Double? {
    return when (op){
        "+" -> num1 + num2
        "-" -> num1 - num2
        "*" -> num1 * num2
        "/" -> num1 / num2
        else -> null
    }
}

// 두 연산자의 우선순위를 비교하는 함수. op1이 op2보다 우선순위가 높거나 같으면 true 반환
fun isHigherOrEqual(op1: String, op2: String): Boolean {
    return when (op1) { // )는 만나면 비교하는 연산자이므로 따로 비교에 넣지 않아도 된다.
        "(" -> false // 왼쪽 괄호는 가장 낮은 우선순위이므로 false
        "*", "/" -> op2 != "(" // 곱셈과 나눗셈은 왼쪽 괄호보다만 낮으면 true 반환.
        "+", "-" -> op2 == "+" || op2 == "-" // 덧셈과 뺄셈은 덧셈과 뺄셈보다만 높으면 true 반환.
        else -> false // 그 외의 경우는 false 반환.
    }
}
