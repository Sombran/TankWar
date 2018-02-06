package com.tank

import com.tank.stage.EditMap
import com.tank.stage.MenuStage
import com.tank.stage.RunningStage
import com.tank.stage.StageAbstract
import com.tank.tankEnum.GameState
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.util.*
import javax.swing.JFrame
import javax.swing.JPanel

/**
 * @author youbo
 * 2018/1/28
 */
class TankGame : JPanel() {

    companion object {
        const val WIDTH = 13 * EditMap.MATERIAL_WIDTH
        const val HEIGHT = 14 * EditMap.MATERIAL_WIDTH

        const val SCORE_AREA_WEIGHT = 200

        const val DELAY: Long = 1000 / 100

        /**
         * 显示缩放倍数
         */
        const val SCALE = 1.5
    }

    var stage: StageAbstract = MenuStage(this)

    override fun paint(g: Graphics) {
        drawBackground()
        stage.paint(g)
    }

    private fun drawBackground() {
        background = Color(0, 0, 0)
    }

    override fun getPreferredSize() = Dimension(TankGame.WIDTH + SCORE_AREA_WEIGHT, TankGame.HEIGHT)

    fun start() {
        // 与输入有关的更新
        updateWithInput()

        // 与输入无关的更新
        updateWithoutInput()
    }

    private fun updateWithInput() {
        addKeyListener(object : KeyAdapter() {
            override fun keyPressed(e: KeyEvent) {
                stage.keyPressed(e)
            }

            override fun keyReleased(e: KeyEvent) {
                stage.keyReleased(e)
            }
        })
    }

    private fun updateWithoutInput() {
        Timer().schedule(object : TimerTask() {
            override fun run() {
                stage.action()
                repaint()
            }
        }, TankGame.DELAY, TankGame.DELAY)
    }

}

fun main(args: Array<String>) {
    val jFrame = JFrame("TankWar")
    val game = TankGame()
    game.isFocusable = true
    jFrame.contentPane = game
    jFrame.pack()
    jFrame.isResizable = false
    jFrame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    jFrame.isVisible = true
    game.start()
}