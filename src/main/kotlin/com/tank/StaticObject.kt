package com.tank

import com.tank.tanks.Tank

/**
 * @author youbo
 * 2018/2/1
 */
abstract class StaticObject(override var x: Int, override var y: Int) : ImageObject() {

    /**
     * 是否可以被子弹打中
     */
    protected abstract val shootable: Boolean

    /**
     * 是否可以被坦克碰撞
     */
    protected abstract val collisionable: Boolean

    override val width
        get() = image.width + 6

    override val height
        get() = image.height + 6

    /**
     * 是否还需要显示，如果为false将会被清除，以后都不会显示
     */
    var isShow: Boolean = true

    open fun step() {}

    fun shootBy(bullet: Bullet): Boolean {
        if (!shootable) {
            return false
        }
        return super.shootBy(bullet)
    }

    fun collisionBy(tank: Tank): Boolean {
        if (!collisionable) {
            return false
        }
        // 坦克的四个点是否在当前对象内并且当前的四个点是否在坦克内
        return super.collisionBy(tank) || tank.pointIn(x, y) || tank.pointIn(x + width, y)
                || tank.pointIn(x, y + height) || tank.pointIn(x + width, y + height)
    }
}