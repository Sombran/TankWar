package com.tank.wall

import com.tank.StaticObject
import com.tank.tankConst.WATER

/**
 * @author youbo
 * 2018/2/1
 */
class Water(x: Int, y: Int) : StaticObject(x, y) {
    override var image = WATER
    override val shootable = false
    override val collisionable = true
}