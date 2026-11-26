package com.example.calculater_2

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.ToggleButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.coroutines.coroutineContext

class MainActivity : AppCompatActivity() {

    //----------------ประกาศตัวแปร---------------------//
    private lateinit var display : TextView// แสดงผลลัพ//
    private var current = ""//ค่าปัจจุบันที่กำลังพิมพ์
    private var operator = ""//เครื่องหมายที่เลือก
    private var firstNumber = 0.0//ตัวเลขตัวแรก
    private var secondNumber = 0.0//ตัวเลขตัวที่2
    private var result = 0.0
    private var isNewOperation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //เชื่อม display เข้ากับ layout
        display = findViewById(R.id.txtdisplay)

        //ตัวแปรเก็บตัวเลข
        val numberButton = listOf(
            R.id.btn0,
            R.id.btn1,
            R.id.btn2,
            R.id.btn3,
            R.id.btn4,
            R.id.btn5,
            R.id.btn6,
            R.id.btn7,
            R.id.btn8,
            R.id.btn9,
        )
        for (id in numberButton){
            findViewById<Button>(id).setOnClickListener {
                numberClicked((it as Button).text.toString())
            }
        }

        findViewById<Button>(R.id.btnplus).setOnClickListener {operatorClick("+") }
        findViewById<Button>(R.id.btnminus).setOnClickListener {operatorClick("-") }
        findViewById<Button>(R.id.btnMulti).setOnClickListener {operatorClick("*") }
        findViewById<Button>(R.id.btnDiv).setOnClickListener {operatorClick("/") }
        findViewById<Button>(R.id.btnMod).setOnClickListener {operatorClick("%") }

        findViewById<Button>(R.id.btnequal).setOnClickListener {calculateResult() }
        findViewById<Button>(R.id.btnClear).setOnClickListener {clearAll() }
        findViewById<Button>(R.id.btnDot).setOnClickListener {addDot() }
        findViewById<Button>(R.id.btnSign).setOnClickListener {toggleSign() }
        findViewById<Button>(R.id.btnDelete).setOnClickListener {backSpace() }

    }
    private fun numberClicked(value: String){
        if (isNewOperation){
            display.text = ""
            isNewOperation = false
        }
        current += value
        display.text = current
    }

    private fun operatorClick(op: String){
        if (current.isNotEmpty()){
            firstNumber = current.toDouble()
            operator = op
            current = ""

        }
    }
    private fun calculateResult(){
        if(current.isNotEmpty()){
            secondNumber = current.toDouble()
            result = when(operator){
                "+" -> firstNumber + secondNumber
                "_" -> firstNumber - secondNumber
                "*" -> firstNumber * secondNumber
                "/" -> if(secondNumber != 0.0) firstNumber / secondNumber else Double.NaN
                "%" -> if(secondNumber != 0.0) firstNumber % secondNumber else Double.NaN
                else -> 0.0

            }
            display.text = if (result.isNaN()) "Can not devided by zero" else result.toString()
            current = result.toString()
            isNewOperation = true
        }
    }
    private fun clearAll(){
        current = ""
        operator = ""
        firstNumber = 0.0
        secondNumber = 0.0
        result = 0.0
        display.text = "0"
        isNewOperation = true
    }

    private fun addDot(){
        if (!current.contains(".")){
            if(current.isEmpty()) current = "0"
            current += "."
            display.text  =current
        }
    }

    private fun toggleSign(){
        if(current.isNotEmpty() && current != "0"){
            current = if(current.startsWith("-")){
                current.substring(1)
            } else {
                "-$current"
            }
            display.text = current
        }
    }

    private fun backSpace(){
        if(isNewOperation) return

        if(current.isNotEmpty()){
            current = current.dropLast(1)

            if (current == "-" || current.isEmpty()){
                current = ""
                display.text = "0"
            }else {
                display.text = current
            }
        }
    }
}
