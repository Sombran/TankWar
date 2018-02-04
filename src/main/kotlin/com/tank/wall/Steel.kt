package com.tank.wall

import com.tank.StaticObject
import com.tank.tankConst.STEEL

/**
 * @author youbo
 * 2018/2/1
 */
class Steel(x: Int, y: Int) : StaticObject(x, y) {
    override var image = STEEL
    override val shootable = true
    override val collisionable = true
}