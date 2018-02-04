package com.tank

import com.tank.stage.EditMap
import com.tank.tankConst.DESTORY_HOME
import com.tank.tankConst.HOME

/**
 * @author youbo
 * 2018/2/3
 */
class Home : StaticObject(TankGame.WIDTH / 2, TankGame.HEIGHT - EditMap.MATERIAL_WIDTH + 5) {
    var isLive = true

    override val shootable = true
    override val collisionable = true
    override var image = HOME
        get() = if (isLive) HOME else DESTORY_HOME
}