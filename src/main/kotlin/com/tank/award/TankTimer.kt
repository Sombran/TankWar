package com.tank.award

import com.tank.tankConst.TIMER
import com.tank.tankEnum.Award


/**
 * @author youbo
 * 2018/2/2
 */
class TankTimer : AwardAbstract() {
    override val award = Award.TIMER
    override var showImage = TIMER!!
}