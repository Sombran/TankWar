package com.tank.tanks

import com.tank.tankConst.GREEN_TANK_DOWN
import com.tank.tankConst.GREEN_TANK_LEFT
import com.tank.tankConst.GREEN_TANK_RIGHT
import com.tank.tankConst.GREEN_TANK_UP
import com.tank.tankEnum.Direction

/**
 * @author youbo
 * 2018/1/29
 */
class GreenTank: EnemyTank() {
    override val images = mapOf(
            Direction.UP to GREEN_TANK_UP,
            Direction.DOWN to GREEN_TANK_DOWN,
            Direction.LEFT to GREEN_TANK_LEFT,
            Direction.RIGHT to GREEN_TANK_RIGHT
    )

    override var speed = 2
    override var life = 1
    override var bulletSpeed = 3

    override fun getScore() = 200
}