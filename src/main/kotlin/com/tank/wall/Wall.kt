package com.tank.wall

import com.tank.StaticObject
import com.tank.tankConst.WALL

/**
 * @author youbo
 * 2018/2/1
 */
class Wall(x: Int, y: Int) : StaticObject(x, y) {
    override var image = WALL!!
    override val shootable = true
    override val collisionable = true
}