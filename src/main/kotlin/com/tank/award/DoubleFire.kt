package com.tank.award

import com.tank.tankConst.DOUBLE_FIRE
import com.tank.tankEnum.Award

/**
 * @author youbo
 * 2018/2/2
 */
class DoubleFire : AwardAbstract() {
    override val award = Award.DOUBLE_FIRE
    override var showImage = DOUBLE_FIRE!!
}