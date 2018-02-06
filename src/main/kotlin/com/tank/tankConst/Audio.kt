package com.tank.tankConst

import java.io.File

/**
 * @author youbo
 * 2018/2/6
 */
private val path = ClassLoader.getSystemClassLoader().getResource("sound").path

val ADD_AUDIO = File("$path/add.wav")
val START_AUDIO = File("$path/start.wav")
val BLAST_AUDIO = File("$path/blast.wav")
val FIRE_AUDIO = File("$path/fire.wav")
val HIT_AUDIO = File("$path/hit.wav")