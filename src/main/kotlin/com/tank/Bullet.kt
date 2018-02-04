package com.tank

import com.tank.tankConst.BULLET
import com.tank.tankConst.ENEMY_BULLET
import com.tank.tankEnum.Direction

/**
 * @author youbo
 * 2018/1/29
 * 子弹类
 */
class Bullet(positionX: Int, positionY: Int, val direction: Direction, bulletSpeed: Int = 3,val isGood: Boolean = false) : ImageObject() {
    override var image = (if (isGood) BULLET else ENEMY_BULLET)!!
    override var x = positionX - width / 2
    override var y = positionY - height / 2

    private val speed = bulletSpeed

    fun step() {
        when (direction) {
            Direction.UP -> y -= speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
            Direction.RIGHT -> x += speed
        }
    }

    fun outOfBounds() = x < 0 || x > TankGame.WIDTH || y < 0 || y > TankGame.HEIGHT
}