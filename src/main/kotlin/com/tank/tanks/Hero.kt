package com.tank.tanks

import com.tank.Bullet
import com.tank.TankGame
import com.tank.stage.EditMap
import com.tank.tankConst.*
import com.tank.tankEnum.Direction
import com.tank.tankEnum.Player
import java.awt.image.BufferedImage

/**
 * @author youbo
 * 2018/1/29
 * 英雄坦克
 */
class Hero(private val player: Player = Player.P1) : Tank() {
    private val p1Images = mapOf<Direction, BufferedImage>(
            Direction.UP to P1_TANK_UP,
            Direction.DOWN to P1_TANK_DOWN,
            Direction.LEFT to P1_TANK_LEFT,
            Direction.RIGHT to P1_TANK_RIGHT
    )

    private val p2Images = mapOf<Direction, BufferedImage>(
            Direction.UP to P2_TANK_UP,
            Direction.DOWN to P2_TANK_DOWN,
            Direction.LEFT to P2_TANK_LEFT,
            Direction.RIGHT to P2_TANK_RIGHT
    )

    override var image = if (player == Player.P1) p1Images[direction]!! else p2Images[direction]!!
        get() = if (player == Player.P1) p1Images[direction]!! else p2Images[direction]!!

    /**
     * 是否正在开火
     */
    var isFire = false

    override var bulletSpeed = 4

    override var life = 3

    private var shootDelay = 80

    init {
        init()
    }

    fun init() {
        subtractFire()
        initPosition()
    }

    fun initPosition() {
        if (player == Player.P1) {
            x = TankGame.WIDTH / 2 - EditMap.MATERIAL_WIDTH * 2
            y = TankGame.HEIGHT - height - 1
        } else {
            x = TankGame.WIDTH / 2 + EditMap.MATERIAL_WIDTH * 2
            y = TankGame.HEIGHT - height - 1
        }
    }

    override fun step() {
        super.step()
        if (shootIndex != 0) {
            shootIndex--
        }
    }

    fun addFire() {
        doubleFire = true
        bulletSpeed = 7
        shootDelay = 50
        speed = 2
    }

    fun subtractFire() {
        doubleFire = false
        bulletSpeed = 4
        shootDelay = 70
        speed = 1
    }

    private var shootIndex = 0
    override fun shoot(): Array<Bullet> {
        return if (isFire && shootIndex == 0) {
            var bulletX = x + width / 2
            var bulletY = y + height / 2
            var bulletX2 = x + width / 2
            var bulletY2 = y + height / 2
            when (direction) {
                Direction.UP -> { bulletY -= height / 2; bulletY2 -= height }
                Direction.DOWN -> { bulletY += height / 2; bulletY2 += height }
                Direction.LEFT -> { bulletX -= width / 2; bulletX2 -= width }
                Direction.RIGHT -> { bulletX += width / 2; bulletX2 += width }
            }
            shootIndex = shootDelay
            if (!doubleFire) {
                arrayOf(Bullet(bulletX, bulletY, direction, bulletSpeed, true))
            } else {
                arrayOf(Bullet(bulletX, bulletY, direction, bulletSpeed, true),
                        Bullet(bulletX2, bulletY2, direction, bulletSpeed, true))
            }
        } else {
            arrayOf()
        }
    }
}