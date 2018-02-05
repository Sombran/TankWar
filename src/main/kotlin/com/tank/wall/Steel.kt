package com.tank.wall

import com.tank.StaticObject
import com.tank.tankConst.LITTLE_STEEL
import com.tank.tankConst.STEEL

/**
 * @author youbo
 * 2018/2/1
 */
class Steel(x: Int, y: Int) : GroupWallAbstract(x, y, LITTLE_STEEL) {
    override var image = STEEL!!
}