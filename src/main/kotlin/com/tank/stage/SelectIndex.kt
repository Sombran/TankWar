package com.tank.stage

import com.tank.tankConst.SELECT_TANK

/**
 * @author youbo
 * 2018/2/3
 */
class SelectIndex(positionX: Int, positionY: Int, val step: Int) {
    var index = 0
    val image = SELECT_TANK!!
    var x = positionX - image.width - 10
    var y = positionY

    fun preIndex() {
        if (index > 0) {
            index--
            y -= step
        }
    }

    fun nextIndex() {
        if (index < 2) {
            index++
            y += step
        }
    }
}