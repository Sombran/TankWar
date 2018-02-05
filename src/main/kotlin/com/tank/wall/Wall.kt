package com.tank.wall

import com.tank.tankConst.LITTLE_WALL
import com.tank.tankConst.WALL

/**
 * @author youbo
 * 2018/2/1
 */
class Wall(x: Int, y: Int) : GroupWallAbstract(x, y, LITTLE_WALL!!) {
    override var image = WALL!!
}