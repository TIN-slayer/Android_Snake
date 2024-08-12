package com.example.myapplication

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import java.util.ArrayDeque
import kotlin.random.Random


class Snake {
    val gridWidth = 10
    var gridHeight = 0
    var sneakSize = 0f
    var canvasWidth = 0f
    var canvasHeight = 0f
    var lineWidth = 0f
    var lineOffset = 0f
    var gameStarted = false
    val grid = ArrayList<ArrayList<Int>>()
    val sneakBody = ArrayDeque<Pair<Int, Int>>()
    var direct: Direction = Direction.stop
    var ateApple = false
    var score = 0
    var scoreText = "SCORE: 0"
    var hasLost = false

    fun draw(canvas: Canvas) {
        if (!gameStarted) {
            gameStarted = true
            canvasWidth = canvas.width.toFloat()
            sneakSize = canvasWidth / (gridWidth + 2)
            gridHeight = (canvas.height.toFloat() / sneakSize).toInt() - 2
            canvasHeight = (gridHeight + 2) * sneakSize
            lineWidth = sneakSize / 2
            lineOffset = (sneakSize - lineWidth) / 2
            for (i in 0 until gridWidth) {
                val buff = ArrayList<Int>()
                for (j in 0 until gridHeight) {
                    buff.add(0)
                }
                grid.add(buff)
            }
            sneakBody.addLast(Pair(0, 0))
            grid[0][0] = 1
            spawnApple()
        }

        // left line
        canvas.drawRect(
            lineOffset,
            lineOffset,
            lineOffset + lineWidth,
            canvasHeight - lineOffset,
            Paint()
        )
        // right line
        canvas.drawRect(
            canvasWidth - (lineOffset + lineWidth),
            lineOffset,
            canvasWidth - lineOffset,
            canvasHeight - lineOffset,
            Paint()
        )
        // top line
        canvas.drawRect(
            lineOffset,
            lineOffset,
            canvasWidth - lineOffset,
            lineOffset + lineWidth,
            Paint()
        )
        // bot line
        canvas.drawRect(
            lineOffset,
            canvasHeight - (lineOffset + lineWidth),
            canvasWidth - lineOffset,
            canvasHeight - lineOffset,
            Paint()
        )


        for (i in 0 until gridWidth) {
            for (j in 0 until gridHeight) {
                if (grid[i][j] > 0) {
                    val paint = Paint()
                    if (grid[i][j] == 2) {
                        paint.color = Color.RED
                    }
                    canvas.drawRect(
                        (i + 1) * sneakSize,
                        (j + 1) * sneakSize,
                        (i + 2) * sneakSize,
                        (j + 2) * sneakSize,
                        paint
                    )
                }
            }
        }
    }

    fun moveTo(d: Direction) {
        if (((d == Direction.up && direct == Direction.down) ||
                    (d == Direction.down && direct == Direction.up) ||
                    (d == Direction.left && direct == Direction.right) ||
                    (d == Direction.right && direct == Direction.left)) &&
            sneakBody.size > 1
        ) {
            return
        }
        direct = d
    }

    fun move() {
        if (hasLost) {
            hasLost = false
            ateApple = false
            direct = Direction.stop
            sneakBody.clear()
            sneakBody.addLast(Pair(0, 0))
            for (i in 0 until gridWidth) {
                for (j in 0 until gridHeight) {
                    grid[i][j] = 0
                }
            }
            grid[0][0] = 1
            score = 0
            scoreText = "SCORE: 0"
            spawnApple()
        }

        var head = sneakBody.peekLast()
        if (head == null || direct == Direction.stop) {
            return
        }
        var x = head.first
        var y = head.second
        if (direct == Direction.left) {
            x = (x - 1)
            if (x < 0) {
                x = gridWidth - 1
            }
        }
        if (direct == Direction.right) {
            x = (x + 1) % gridWidth
        }
        if (direct == Direction.up) {
            y = (y - 1)
            if (y < 0) {
                y = gridHeight - 1
            }
        }
        if (direct == Direction.down) {
            y = (y + 1) % gridHeight
        }

        sneakBody.addLast(Pair(x, y))
        if (!ateApple) {
            val tail = sneakBody.pollFirst()!!
            grid[tail.first][tail.second] = 0
        }
        ateApple = false
        head = sneakBody.peekLast()!!
        if (grid[head.first][head.second] == 1) {
            hasLost = true
        } else if (grid[head.first][head.second] == 2) {
            ateApple = true
            spawnApple()
            score++
            scoreText = "SCORE: $score"
        }
        grid[head.first][head.second] = 1
    }

    fun spawnApple() {
        val positions = ArrayList<Pair<Int, Int>>()
        for (i in 0 until gridWidth) {
            for (j in 0 until gridHeight) {
                if (grid[i][j] == 0) {
                    positions.add(Pair(i, j))
                }
            }
        }
        if (positions.size == 0) {
            hasLost = true
        }
        val rndInd = Random.nextInt(positions.size)
        val apple = positions[rndInd]
        grid[apple.first][apple.second] = 2
    }
}
