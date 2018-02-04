package com.tank

import com.tank.tankConst.DESTORY_HOME
import com.tank.tankConst.HOME

/**
 * @author youbo
 * 2018/2/3
 */
class Home : StaticObject(TankGame.WIDTH / 2 - HOME.width / 2, TankGame.HEIGHT - HOME.height) {
    var isLive = true

    override val shootable = true
    override val collisionable = true
    override var image = HOME
        get() = if (isLive) HOME else DESTORY_HOME
}