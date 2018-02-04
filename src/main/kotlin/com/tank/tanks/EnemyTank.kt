package com.tank.tanks

import com.tank.Bullet
import com.tank.TankGame
import com.tank.tankConst.GREY_TANK_UP
import com.tank.tankConst.P1_TANK_UP
import com.tank.tankEnum.Award
import com.tank.tankEnum.Direction
import java.awt.image.BufferedImage
import java.util.*

/**
 * @author youbo
 * 2018/1/29
 */
abstract class EnemyTank: Tank(), Enemy {

    abstract override var speed: Int

    abstract override var life: Int

    protected abstract val images: Map<Direction, BufferedImage>

    override var y = 1

    /**
     * 是否还有奖励，只有第一枪有奖励
     */
    private var hasAward = true

    /**
     * GREY_TANK_UP只是用来占位
     */
    override var image: BufferedImage = GREY_TANK_UP
        get() = images[direction]!!

    private var shootIndex = 0
    override fun shoot(): Array<Bullet> {
        // shootIndex为攻击间隔，只有为0才有机会发射
        if (shootIndex != 0) {
            shootIndex--
        } else {
            // 只有5%的机会发射，发射后随机设定攻击间隔
            if (Random().nextInt(100) > 95) {
                var bulletX = x + width / 2
                var bulletY = y + height / 2
                when (direction) {
                    Direction.UP -> bulletY -= height / 2
                    Direction.DOWN -> bulletY += height / 2
                    Direction.LEFT -> bulletX -= width / 2
                    Direction.RIGHT -> bulletX += width / 2
                }
                shootIndex = Random().nextInt(200) + 150
                return arrayOf(Bullet(bulletX, bulletY, direction, bulletSpeed))
            }
        }
        return arrayOf()
    }

    private var moveIndex = 0
    override fun step() {
        if (moveIndex != 0) {
            moveIndex--
        } else {
            isMove = true
            val rand = Random()
            moveIndex = rand.nextInt(100) + 1
            direction = Direction.values()[rand.nextInt(4)]
        }
        super.step()
    }

    /**
     * 重新走一个方向
     */
    fun reStep() {
        val rand = Random()
        moveIndex = rand.nextInt(100) + 1
        direction = Direction.values()[rand.nextInt(4)]
    }

    /**
     * 获得奖励，出现奖励的几率为5分之1
     */
    fun getAward(): Award {
        val rand = Random()
        if (!hasAward || rand.nextInt(50) > 10) {
            hasAward = false
            return Award.NONE
        }
        hasAward = false
        val values = Award.values()
        return values[rand.nextInt(values.size - 1) + 1]
    }
}