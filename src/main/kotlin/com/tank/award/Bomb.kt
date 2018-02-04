package com.tank.award

import com.tank.tankConst.BOMB
import com.tank.tankEnum.Award

/**
 * @author youbo
 * 2018/2/2
 */
class Bomb : AwardAbstract() {
    override val award = Award.BOMB
    override var showImage = BOMB!!
}