package com.tank.tanks

import com.tank.Bullet
import com.tank.ImageObject
import com.tank.TankGame
import com.tank.tankEnum.Direction
import java.awt.image.BufferedImage

/**
 * @author youbo
 * 2018/1/29
 */
abstract class Tank : ImageObject() {
    private var oldX = -1
    private var oldY = -1

    /**
     * 覆写为var
     */
    override var x: Int = 0
    override var y: Int = 0

    /**
     * 方向
     */
    var direction = Direction.UP

    /**
     * 生命
     */
    open protected var life = 1

    /**
     * 移动速度
     */
    open protected var speed = 1

    /**
     * 是否正在移动
     */
    protected var isMove = false

    /**
     * 子弹速度
     */
    abstract var bulletSpeed: Int

    /**
     * 是否是双倍火力
     */
    var doubleFire = false

    abstract fun shoot(): Array<Bullet>

    /**
     * 是否活着
     */
    val isLive
        get() = life > 0

    open fun step() {
        if (isMove) {
            var newX = x
            var newY = y
            when (direction) {
                Direction.UP -> newY -= speed
                Direction.DOWN -> newY += speed
                Direction.LEFT -> newX -= speed
                Direction.RIGHT -> newX += speed
            }
            if (newX > 0 && newX + width < TankGame.WIDTH && newY > 0 && newY + height < TankGame.HEIGHT) {
                oldX = x
                oldY = y
                x = newX
                y = newY
            }
        }
    }

    /**
     * 返回上一步，碰撞时候使用
     */
    fun backStep() {
        if (oldX != -1 && oldY != -1) {
            x = oldX
            y = oldY
        }
    }

    /**
     * 停止移动
     */
    fun stopMove() {
        isMove = false
    }

    /**
     * 开始移动
     */
    fun startMove() {
        isMove = true
    }

    fun subtractLife() {
        life--
    }

    fun addLife() {
        life++
    }
}