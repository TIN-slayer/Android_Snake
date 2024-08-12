package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), OnClickListener {

    var gameView: GameView? = null

    var timer = object : CountDownTimer(Long.MAX_VALUE, 300) {
        @SuppressLint("SetTextI18n")
        override fun onTick(p0: Long) {
            findViewById<TextView>(R.id.score).text = gameView!!.snake.scoreText
            gameView!!.goSnake()
        }

        override fun onFinish() {

        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gameView = findViewById<GameView>(R.id.game_view)
        timer.start()
    }

    override fun onClick(p0: View?) {
        if (p0!!.id == R.id.btn_left) {
            gameView!!.snake.moveTo(Direction.left)
        }
        if (p0!!.id == R.id.btn_right) {
            gameView!!.snake.moveTo(Direction.right)
        }
        if (p0!!.id == R.id.btn_up) {
            gameView!!.snake.moveTo(Direction.up)
        }
        if (p0!!.id == R.id.btn_down) {
            gameView!!.snake.moveTo(Direction.down)
        }
    }


}