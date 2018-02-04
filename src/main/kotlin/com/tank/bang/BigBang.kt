package com.tank.bang

import com.tank.StaticObject
import com.tank.tankConst.*

/**
 * @author youbo
 * 2018/2/1
 */
class BigBang(x: Int, y: Int) : StaticObject(x, y) {
    override val shootable = false
    override val collisionable = false

    private val images = listOf(BIG_BANG1, BIG_BANG2, BIG_BANG3, BIG_BANG4, BIG_BANG5, BIG_BANG6, BIG_BANG7, BIG_BANG8)

    override val width = 60
    override val height = 60

    /**
     * 当前显示第几个画面
     */
    private var index = 0

    override var image = images[index]

    private var showIndex = 2
    override fun step() {
        if (showIndex-- == 0) {
            index++
            // 最后一个画面停留时间长一点
            showIndex = if (index == images.size - 1) 80 else 2
            if (index < images.size) {
                image = images[index]
            } else {
                isShow = false
            }
        }
    }
}