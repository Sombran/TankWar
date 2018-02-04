package com.tank.stage

import com.tank.TankGame
import com.tank.tankConst.SELECT
import java.awt.Graphics
import java.awt.event.KeyEvent

/**
 * @author youbo
 * 2018/2/3
 */
class MenuStage(context: TankGame) : StageAbstract(context) {

    private val select = SelectIndex(TankGame.WIDTH / 2 - SELECT.width / 2, TankGame.HEIGHT / 2 - SELECT.height / 2, SELECT.height / 3)

    override fun paint(g: Graphics) {
        drawBackground(g)
        drawSelect(g)
    }

    private fun drawBackground(g: Graphics) = g.drawImage(SELECT, TankGame.WIDTH / 2 - SELECT.width / 2, TankGame.HEIGHT / 2 - SELECT.height / 2, null)

    private fun drawSelect(g: Graphics) = g.drawImage(select.image, select.x, select.y, null)

    override fun keyPressed(e: KeyEvent) {
          when (e.keyCode) {
            KeyEvent.VK_UP -> select.preIndex()
            KeyEvent.VK_DOWN -> select.nextIndex()
            KeyEvent.VK_ENTER -> enter()
        }
    }

    private fun enter() {
        when (select.index) {
            0 -> context.stage = RunningStage(context)
            1 -> context.stage = RunningStage(context, true)
            2 -> context.stage = EditMap(context)
        }
    }
}