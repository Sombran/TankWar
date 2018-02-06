package com.tank.wall

import com.tank.*
import com.tank.tankConst.HIT_AUDIO
import com.tank.tankConst.LITTLE_STEEL
import java.awt.Graphics
import java.awt.image.BufferedImage

/**
 * @author youbo
 * 2018/2/5
 */
abstract class GroupWallAbstract(x: Int, y: Int, littleImage: BufferedImage) : StaticObject(x, y) {

    private val content = mutableListOf<StaticObject>()

    /**
     * 组合的墙默认可以攻击可以撞
     */
    override val collisionable = true
    override val shootable = true

    override var isShow = true
        get() = content.isNotEmpty()

    init {
        val w = (LITTLE_STEEL.width / TankGame.SCALE).toInt() + 3
        content.add(LittleWall(x, y, littleImage))
        content.add(LittleWall(x + w, y, littleImage))
        content.add(LittleWall(x, y + w, littleImage))
        content.add(LittleWall(x + w, y + w, littleImage))
    }

    override fun draw(g: Graphics) {
        if (content.size == 4) {
            g.drawImage(image, x, y, width, height, null)
        } else {
            content.forEach { g.drawImage(it.image, it.x, it.y, it.width, it.height, null) }
        }
    }

    override fun collisionBy(other: ImageObject): Boolean {
        return content.any { it.collisionBy(other) }
    }

    override fun shootBy(other: Bullet): Boolean {
        var isShoot = false
        content.filter { it.shootBy(other) }.forEach {
            content.takeIf { other.tank.doubleFire || this !is Steel } ?.remove(it)
            if (this is Steel && !other.tank.doubleFire) {
                play(HIT_AUDIO)
            }
            isShoot = true
        }
        return isShoot
    }

    /**
     * 组合的墙，默认可撞可攻击
     */
    private class LittleWall(x: Int, y: Int, override var image: BufferedImage) : StaticObject(x, y) {
        override val shootable = true
        override val collisionable = true
        override val width: Int
            get() = (LITTLE_STEEL.width / TankGame.SCALE).toInt() + 3

        override val height: Int
            get() = (LITTLE_STEEL.width / TankGame.SCALE).toInt() + 3
    }
}