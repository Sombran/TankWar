package com.tank.stage

import com.tank.TankGame
import java.awt.Graphics
import java.awt.event.KeyEvent

/**
 * @author youbo
 * 2018/2/3
 */
abstract class StageAbstract(val context: TankGame) {

    abstract fun paint(g: Graphics)

    open fun action() {}

    open fun keyPressed(e: KeyEvent) {}

    open fun keyReleased(e: KeyEvent) {}
}