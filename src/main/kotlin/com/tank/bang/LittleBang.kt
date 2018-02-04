package com.tank.bang

import com.tank.StaticObject
import com.tank.tankConst.LITTLE_BANG

/**
 * @author youbo
 * 2018/1/30
 */
class LittleBang(x: Int, y: Int): StaticObject(x - 70 / 2, y - 55 / 2) {
    override val shootable = false
    override val collisionable = false

    override var image = LITTLE_BANG!!

    private var showIndex = 80
    override fun step() {
        if (showIndex-- == 0) {
            isShow = false
        }
    }
}