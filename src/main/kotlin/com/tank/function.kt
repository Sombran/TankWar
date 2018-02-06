package com.tank

import sun.audio.AudioPlayer
import sun.audio.AudioStream
import java.io.File
import java.util.*

/**
 * @author youbo
 * 2018/2/6
 */

/**
 * 获得数组中随机一个值
 */
fun <T> Array<T>.shuffleData() = this[Random().nextInt(this.size)]

/**
 * 返回打乱后的数据
 */
fun <T> Array<T>.shuffle(): Array<T> {
    val rand = Random()
    val copyArray = this.copyOf()
    for (i in 0 until this.size) {
        val index = rand.nextInt(this.size)
        val tem = copyArray[index]
        copyArray[index] = copyArray[i]
        copyArray[i] = tem
    }
    return copyArray
}

fun play(file: File) {
    AudioPlayer.player.start(AudioStream(file.inputStream()))
}