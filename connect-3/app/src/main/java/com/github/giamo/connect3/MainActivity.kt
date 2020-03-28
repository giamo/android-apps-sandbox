package com.github.giamo.connect3

import android.graphics.Color
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

        val WINNING_COMBINATIONS = arrayOf(
            // horizontal combinations
            arrayOf(Pair(0, 0), Pair(1, 0), Pair(2, 0)),
            arrayOf(Pair(0, 1), Pair(1, 1), Pair(2, 1)),
            arrayOf(Pair(0, 2), Pair(1, 2), Pair(2, 2)),

            // vertical combinations
            arrayOf(Pair(0, 0), Pair(0, 1), Pair(0, 2)),
            arrayOf(Pair(1, 0), Pair(1, 1), Pair(1, 2)),
            arrayOf(Pair(2, 0), Pair(2, 1), Pair(2, 2)),

            // diagonal combinations
            arrayOf(Pair(0, 0), Pair(1, 1), Pair(2, 2)),
            arrayOf(Pair(0, 2), Pair(1, 1), Pair(2, 0))
        )
    }

    private var board: Array<Array<Int>> = Array(3) { _ -> arrayOf(EMPTY, EMPTY, EMPTY) }
    private var currentPlayer = RED
    private var gameEnded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun dropToken(view: View) {
        val imageView = view as ImageView

        val position = retrievePosition(imageView.tag)
        if (position == null) {
            Toast.makeText(this, "An error has occurred", Toast.LENGTH_SHORT).show()
            return
        }

        if (isBoardCellEmpty(position) && !gameEnded) {
            updateBoard(position)

            if (currentPlayer == RED) imageView.setImageResource(R.drawable.red)
            else imageView.setImageResource(R.drawable.yellow)

            imageView.translationY = -1000f
            imageView.alpha = 1f
            imageView.animate().translationYBy(1000f).rotation(360f).setDuration(500)

            evaluateBoard()
            updateCurrentPlayer()
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

        currentPlayer = RED
        gameEnded = false
        resultTextView.visibility = View.INVISIBLE
    }

    private fun retrievePosition(tag: Any?): Pair<Int, Int>? {
        return tag.let {
            when (it) {
                cell00.tag -> Pair(0, 0)
                cell01.tag -> Pair(0, 1)
                cell02.tag -> Pair(0, 2)
                cell10.tag -> Pair(1, 0)
                cell11.tag -> Pair(1, 1)
                cell12.tag -> Pair(1, 2)
                cell20.tag -> Pair(2, 0)
                cell21.tag -> Pair(2, 1)
                cell22.tag -> Pair(2, 2)
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

    private fun areAllBoardCellsTaken(): Boolean {
        for (x in 0..2) {
            for (y in 0..2) {
                if (board[x][y] == EMPTY) return false
            }
        }

        return true
    }

    private fun haveSameColor(position1: Pair<Int, Int>, position2: Pair<Int, Int>): Boolean {
        return board[position1.first][position1.second] == board[position2.first][position2.second]
    }

    private fun evaluateBoard() {
        for (winningCombination in WINNING_COMBINATIONS) {
            if (haveSameColor(winningCombination[0], winningCombination[1]) &&
                haveSameColor(winningCombination[1], winningCombination[2]) &&
                !isBoardCellEmpty(winningCombination[0])
            ) {
                if (currentPlayer == RED) {
                    resultTextView.text = "RED player wins!"
                    resultTextView.setTextColor(Color.RED)
                } else {
                    resultTextView.text = "YELLOW player wins!"
                    resultTextView.setTextColor(Color.YELLOW)
                }
                resultTextView.visibility = View.VISIBLE
                gameEnded = true
                return
            }
        }

        if (areAllBoardCellsTaken()) {
            resultTextView.text = "It's a tie!"
            resultTextView.setTextColor(Color.GRAY)
            resultTextView.visibility = View.VISIBLE
            gameEnded = true
        }
    }

    private fun updateCurrentPlayer() {
        currentPlayer =
            if (currentPlayer == RED) YELLOW
            else RED
    }
}
