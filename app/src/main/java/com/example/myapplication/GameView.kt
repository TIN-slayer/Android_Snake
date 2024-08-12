package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.TextView

class GameView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var snake: Snake = Snake()



    fun goSnake(){
        snake.move()
        invalidate()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        snake.draw(canvas)
    }

}