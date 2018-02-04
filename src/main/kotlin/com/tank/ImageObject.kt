package com.tank

import java.awt.image.BufferedImage

/**
 * @author youbo
 * 2018/2/2
 */
abstract class ImageObject {
    abstract var image: BufferedImage

    open val width
        get() = image.width / 2

    open val height
        get() = image.height / 2

    open val x = 0
    open val y = 1

    open fun collisionBy(other: ImageObject) = imageObjectIn(other)

    open fun shootBy(other: ImageObject) = imageObjectIn(other)

    private fun imageObjectIn(other: ImageObject): Boolean {
        val x1 = other.x
        val y1 = other.y

        val x2 = other.x
        val y2 = other.y + other.height

        val x3 = other.x + other.width
        val y3 = other.y

        val x4 = other.x + other.width
        val y4 = other.y + other.height

        return pointIn(x1, y1) || pointIn(x2, y2) || pointIn(x3, y3) || pointIn(x4, y4)
    }

    fun pointIn(x1: Int, y1: Int) = x1 >= x && x1 <= x + width && y1 >= y && y1 <= y + height
}