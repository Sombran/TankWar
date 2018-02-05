package com.tank

import java.awt.Graphics

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
        get() = super.width + 6

    override val height
        get() = super.height + 6

    /**
     * 是否还需要显示，如果为false将会被清除，以后都不会显示
     */
    open var isShow: Boolean = true

    open fun step() {}

    override fun shootBy(other: Bullet): Boolean {
        if (!shootable) {
            return false
        }
        return super.shootBy(other)
    }

    override fun collisionBy(other: ImageObject): Boolean {
        if (!collisionable) {
            return false
        }
        // 坦克的四个点是否在当前对象内并且当前的四个点是否在坦克内
        return super.collisionBy(other) || other.pointIn(x, y) || other.pointIn(x + width, y)
                || other.pointIn(x, y + height) || other.pointIn(x + width, y + height)
    }

    open fun draw(g: Graphics) {
        g.drawImage(image, x, y, width, height, null)
    }
}