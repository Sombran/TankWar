package com.tank.wall

import com.tank.StaticObject
import com.tank.tankConst.GRASS

/**
 * @author youbo
 * 2018/2/1
 */
class Grass(x: Int, y: Int) : StaticObject(x, y) {
    override var image = GRASS
    override val shootable = false
    override val collisionable = false
}