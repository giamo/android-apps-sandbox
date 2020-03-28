package com.github.giamo.connect3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    private var nextMoveIsRed = true;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun dropToken(view: View) {
        val imageView = view as ImageView
        imageView.translationY = -1000f

        if (nextMoveIsRed) imageView.setImageResource(R.drawable.red)
        else imageView.setImageResource(R.drawable.yellow)

        imageView.animate().translationYBy(1000f).setDuration(1000)
        nextMoveIsRed = !nextMoveIsRed
    }
}
