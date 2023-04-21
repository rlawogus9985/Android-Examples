package com.example.calculator_mvvm

import java.math.RoundingMode
import java.util.*

class Model {
    var expressionTexts: String = ""
    var previousExpression = mutableListOf<String>()

    fun splitExp(exp: String): List<String> {
        val regex = Regex("[*+\\-/%%()]") // *, +, -, /, % 중 하나에 매칭되는 정규식 패턴
        val tokens = regex.split(exp)
        val operators = regex.findAll(exp).map { it.value }.toList()
        val splitedExp = tokens.zip(operators.plus("")).flatMap { it.toList() }.filter{ it.isNotEmpty()}
        return splitedExp
    }

    fun calculateExpression(_exp: String) : String{

        val exp = when{
            _exp.first() in "*/%" -> return ""
            _exp.first() in "+-" -> "0$_exp"
            else -> _exp
        }

        val splitedExp = splitExp(exp)

        val operandStack = Stack<Double>()
        val operatorStack = Stack<String>()

        for (ch in splitedExp){
            when (ch) {
                "(" -> operatorStack.push(ch)
                ")" -> {
                    while (operatorStack.peek() != "("){
                        val op = operatorStack.pop()
                        val num2 = operandStack.pop()
                        val num1 = operandStack.pop()
                        val result = calculate(num1, num2, op)
                        if (result!!.isInfinite()) return ""
                        operandStack.push(result)
                    }
                    operatorStack.pop()
                }
                in "+-*/" -> {
                    while (operatorStack.isNotEmpty() && isHigherOrEqual(operatorStack.peek(), ch)){
                        val op = operatorStack.pop()
                        val num2 = operandStack.pop()
                        val num1 = operandStack.pop()
                        val result = calculate(num1, num2, op)
                        if (result!!.isInfinite()) return ""
                        operandStack.push(result)
                    }
                    operatorStack.push(ch)
                }
                "%" -> {
                    val num = operandStack.pop()
                    val result = num * 0.01
                    operandStack.push(result)
                }
                else -> operandStack.push(ch.toDouble())
            }
        }
        while (operatorStack.isNotEmpty()) {
            val op = operatorStack.pop()
            val num2 = operandStack.pop()
            val num1 = operandStack.pop()
            val result = calculate(num1, num2, op)
            if (result!!.isInfinite()) return ""
            operandStack.push(result)
        }
        return operandStack.pop().toBigDecimal().setScale(10, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString()
    }


    fun calculate(num1: Double, num2: Double, op: String): Double? {
        return when (op){
            "+" -> num1 + num2
            "-" -> num1 - num2
            "*" -> num1 * num2
            "/" -> num1 / num2
            else -> null
        }
    }

    fun isHigherOrEqual(op1: String, op2: String): Boolean {
        return when (op1) {
            "(" -> false
            "*", "/" -> op2 != "("
            "+", "-" -> op2 == "+" || op2 == "-"
            else -> false
        }
    }


}