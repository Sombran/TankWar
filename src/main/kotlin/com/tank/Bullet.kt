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
    override var x = positionX - image.width / 2
    override var y = positionY - image.height / 2

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

    /**
     * 子弹碰撞子弹检测，将宽度和高度加10，以增加子弹消除几率
     */
    fun collisionBy(other: Bullet): Boolean {
        val x1 = other.x - 5
        val y1 = other.y - 5

        val x2 = other.x - 5
        val y2 = other.y + other.height + 5

        val x3 = other.x + other.width + 5
        val y3 = other.y - 5

        val x4 = other.x + other.width + 5
        val y4 = other.y + other.height + 5

        return pointIn(x1, y1) || pointIn(x2, y2) || pointIn(x3, y3) || pointIn(x4, y4)
    }
}