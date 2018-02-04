package com.tank.tanks

import com.tank.tankConst.GREY_TANK_DOWN
import com.tank.tankConst.GREY_TANK_LEFT
import com.tank.tankConst.GREY_TANK_RIGHT
import com.tank.tankConst.GREY_TANK_UP
import com.tank.tankEnum.Direction

/**
 * @author youbo
 * 2018/1/29
 */
class GreyTank: EnemyTank() {
    override val images = mapOf(
            Direction.UP to GREY_TANK_UP,
            Direction.DOWN to GREY_TANK_DOWN,
            Direction.LEFT to GREY_TANK_LEFT,
            Direction.RIGHT to GREY_TANK_RIGHT
    )

    override var speed = 2
    override var life = 1
    override var bulletSpeed = 3

    override fun getScore() = 100
}