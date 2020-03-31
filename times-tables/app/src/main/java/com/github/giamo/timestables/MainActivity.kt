package com.github.giamo.timestables

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val MIN_INPUT = 1
        const val MAX_INPUT = 20
    }

    private var arrayAdapter: ArrayAdapter<Int>? = null
    private var timeTableValues = Array<Int>(10) { 0 }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seekBar.max = MAX_INPUT
        seekBar.setProgress(MIN_INPUT)
        updateTimeTableElements(MIN_INPUT)
        labelTextView.text = labelText(MIN_INPUT)

        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, timeTableValues)
        timeTableList.adapter = arrayAdapter

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (progress >= MIN_INPUT) {
                    updateTimeTableElements(progress)
                    labelTextView.text = labelText(progress)
                } else {
                    updateTimeTableElements(MIN_INPUT)
                    seekBar?.setProgress(MIN_INPUT)
                    labelTextView.text = labelText(MIN_INPUT)
                }
                arrayAdapter!!.notifyDataSetChanged()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })
    }

    private fun updateTimeTableElements(n: Int) {
        for (i in 0..9) {
            timeTableValues[i] = n * (i+1)
        }
    }

    private fun labelText(n: Int): String { return "$n Time Table" }
}
