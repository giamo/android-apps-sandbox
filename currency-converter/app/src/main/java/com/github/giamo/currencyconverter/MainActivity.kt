package com.github.giamo.currencyconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val EUR_USD_RATIO = 1.1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun convert(view: View) {
        val amountToConvertTextEUR = currencyEditText.text.toString()
        if (amountToConvertTextEUR.isEmpty()) {
            Toast.makeText(this, "An amount of money should be specified", Toast.LENGTH_SHORT)
                .show()
        } else {
            val amountToConvertEUR = amountToConvertTextEUR.toDouble()
            val convertedAmountUSD = amountToConvertEUR * EUR_USD_RATIO

            Toast.makeText(
                this,
                "EUR ${amountToConvertEUR.format(2)} = USD ${convertedAmountUSD.format(2)}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun Double.format(numDigits: Int) = "%.${numDigits}f".format(this)
}

