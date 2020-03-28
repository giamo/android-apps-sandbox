package com.github.giamo.connect3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity() : AppCompatActivity() {

    companion object {
        const val EMPTY = 0
        const val RED = 1
        const val YELLOW = 2
    }

    private var board: Array<Array<Int>> = Array<Array<Int>>(3) { _ -> arrayOf(EMPTY, EMPTY, EMPTY) }
    private var currentPlayer = RED;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun dropToken(view: View) {
        val imageView = view as ImageView

        val position = retrievePosition(imageView.tag?.toString())
        if (position == null) {
            Toast.makeText(this, "An error has occurred", Toast.LENGTH_SHORT).show()
            return
        }

        if (isBoardCellEmpty(position)) {
            updateBoard(position)
            imageView.translationY = -1000f
            imageView.alpha = 1f
            currentPlayer = if (currentPlayer == RED) {
                imageView.setImageResource(R.drawable.red)
                YELLOW
            } else {
                imageView.setImageResource(R.drawable.yellow)
                RED
            }

            imageView.animate().translationYBy(1000f).setDuration(1000)
        }
    }

    fun resetGame(view: View) {
        for (x in 0..2) {
            for (y in 0..2) {
                board[x][y] = EMPTY
            }
        }
        cell00.alpha = 0f
        cell01.alpha = 0f
        cell02.alpha = 0f
        cell10.alpha = 0f
        cell11.alpha = 0f
        cell12.alpha = 0f
        cell20.alpha = 0f
        cell21.alpha = 0f
        cell22.alpha = 0f
    }

    private fun retrievePosition(tag: String?): Pair<Int, Int>? {
        return tag.let {
            when (it) {
                "00" -> Pair(0, 0)
                "01" -> Pair(0, 1)
                "02" -> Pair(0, 2)
                "10" -> Pair(1, 0)
                "11" -> Pair(1, 1)
                "12" -> Pair(1, 2)
                "20" -> Pair(2, 0)
                "21" -> Pair(2, 1)
                "22" -> Pair(2, 2)
                else -> null
            }
        }
    }

    private fun updateBoard(position: Pair<Int, Int>) {
        if (isBoardCellEmpty(position)) board[position.first][position.second] = currentPlayer
    }

    private fun isBoardCellEmpty(position: Pair<Int, Int>): Boolean {
        return (board[position.first][position.second] == EMPTY)
    }
}
