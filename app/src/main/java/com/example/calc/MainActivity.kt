package com.example.calc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.calc.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //check last character is operator or not
        fun isLastCharOperator(input: String): Boolean {
            return when (val lastChar = input.lastOrNull()) {
                '+', '-', '*', '/' -> true
                else -> false
            }
        }

        // Set up click listeners for buttons
        val buttons = arrayOf(
            binding.btn0,
            binding.btn1,
            binding.btn2,
            binding.btn3,
            binding.btn4,
            binding.btn5,
            binding.btn6,
            binding.btn7,
            binding.btn8,
            binding.btn9,
            binding.btnAdd,
            binding.btnSubtract,
            binding.btnMultiply,
            binding.btnDivide,
            binding.btnMod
        )

        buttons.forEach { button ->
            button.setOnClickListener {
                val alreadyEnteredValue =
                    binding.inputNumbers.text.toString(); //extracting value of input values

                //check two operators should not come one after another.
                if (isLastCharOperator(button.text.toString()) && isLastCharOperator(alreadyEnteredValue)) {
                    Toast.makeText(this, "Enter a number.", Toast.LENGTH_SHORT).show()
                } else {
                    binding.inputNumbers.append(button.text.toString())
                }
            }
        }

        // Set up click listeners for clear buttons
        binding.btnClear.setOnClickListener {
            binding.inputNumbers.text = ""
        }

        binding.btnAllClear.setOnClickListener {
            binding.inputNumbers.text = ""
            binding.result.text = ""
        }

        // Set up click listener for equals button
        binding.btnEquals.setOnClickListener {
            try {
                val exp = ExpressionBuilder(binding.inputNumbers.text.toString()).build()
                binding.result.text = exp.evaluate().toInt().toString()
            }catch(e: Exception){
                //handling arithmetic expression
                binding.inputNumbers.text = ""
                binding.result.text = ""
                Toast.makeText(this, "Invalid Operation", Toast.LENGTH_SHORT).show()
            }
        }

        //Set up click listener for back button
        binding.btnBack.setOnClickListener{
            val currentText = binding.inputNumbers.text.toString()
            if (currentText.isNotEmpty()) {
                binding.inputNumbers.text = currentText.substring(0, currentText.length - 1)
            }
        }



    }
}
