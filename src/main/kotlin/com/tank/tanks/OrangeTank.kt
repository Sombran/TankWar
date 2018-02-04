package com.tank.tanks

import com.tank.tankConst.*
import com.tank.tankEnum.Direction

/**
 * @author youbo
 * 2018/1/29
 */
class OrangeTank(override var x: Int) : EnemyTank() {
    override val images = mapOf(
            Direction.UP to ORANGE_TANK_UP,
            Direction.DOWN to ORANGE_TANK_DOWN,
            Direction.LEFT to ORANGE_TANK_LEFT,
            Direction.RIGHT to ORANGE_TANK_RIGHT
    )

    override var speed = 1
    override var life = 3
    override var bulletSpeed = 2

    override fun getScore() = 300
}