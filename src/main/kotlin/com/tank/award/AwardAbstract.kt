package com.tank.award

import com.tank.StaticObject
import com.tank.TankGame
import com.tank.tankConst.NOTHING40
import com.tank.tankEnum.Award
import java.awt.image.BufferedImage
import java.util.*

/**
 * @author youbo
 * 2018/2/2
 */
abstract class AwardAbstract : StaticObject(Random().nextInt(TankGame.WIDTH - 40), Random().nextInt(TankGame.HEIGHT - 40)) {
    override val shootable = false
    override val collisionable = true

    /**
     * 透明图片，用来做一闪一闪的效果
     */
    private var image2 = NOTHING40!!
    override var image = image2

    /**
     * 实际要展示的图片，由子类提供
     */
    abstract protected var showImage: BufferedImage

    abstract val award: Award

    private var showIndex = 100
    override fun step() {
        image = if (showIndex > 50) showImage else image2
        if (showIndex-- == 0) showIndex = 100
    }
}
