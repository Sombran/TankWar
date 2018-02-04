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

    override val width: Int
        get() = image.width

    override val height: Int
        get() = image.height

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
     * 子弹碰撞检测增加的值
     */
    private val addWidth = 3

    /**
     * 子弹碰撞子弹检测，将宽度和高度加增加，以增加子弹消除几率
     */
    fun collisionBy(other: Bullet): Boolean {
        val x1 = other.x - addWidth
        val y1 = other.y - addWidth

        val x2 = other.x - addWidth
        val y2 = other.y + other.height + addWidth

        val x3 = other.x + other.width + addWidth
        val y3 = other.y - addWidth

        val x4 = other.x + other.width + addWidth
        val y4 = other.y + other.height + addWidth

        return pointIn(x1, y1) || pointIn(x2, y2) || pointIn(x3, y3) || pointIn(x4, y4)
    }
}