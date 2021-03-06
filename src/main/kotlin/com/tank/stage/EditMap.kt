package com.tank.stage

import com.tank.Home
import com.tank.StaticObject
import com.tank.TankGame
import com.tank.play
import com.tank.tankConst.P1_TANK_UP
import com.tank.tankConst.START_AUDIO
import com.tank.wall.Grass
import com.tank.wall.Steel
import com.tank.wall.Wall
import com.tank.wall.Water
import java.awt.Graphics
import java.awt.event.KeyEvent
import java.io.File
import java.io.PrintWriter

/**
 * @author youbo
 * 2018/2/3
 */
class EditMap(context: TankGame) : StageAbstract(context) {

    companion object {
        const val MATERIAL_WIDTH = (70 / TankGame.SCALE).toInt()
    }

    private val home = Home()

    private var x = 0
    private var y = 0

    private val material = arrayOf(Grass::class.java, Steel::class.java, Wall::class.java, Water::class.java)

    /**
     * 二维数组储存地图
     */
    private val objects = Array(TankGame.HEIGHT / MATERIAL_WIDTH) { arrayOfNulls<StaticObject>(TankGame.WIDTH / MATERIAL_WIDTH) }

    override fun paint(g: Graphics) {
        g.drawImage(home.image, home.x, home.y, home.width, home.height, null)
        g.drawImage(P1_TANK_UP, x, y, MATERIAL_WIDTH, MATERIAL_WIDTH, null)
        objects.forEach { it.forEach { it?.let { g.drawImage(it.image, it.x, it.y, MATERIAL_WIDTH, MATERIAL_WIDTH, null) } } }
    }

    override fun keyPressed(e: KeyEvent) {
        when (e.keyCode) {
            KeyEvent.VK_W, KeyEvent.VK_UP -> { if (y - MATERIAL_WIDTH >= 0) y -= MATERIAL_WIDTH }
            KeyEvent.VK_S, KeyEvent.VK_DOWN -> { if (y + MATERIAL_WIDTH * 2 <= TankGame.HEIGHT) y += MATERIAL_WIDTH }
            KeyEvent.VK_A, KeyEvent.VK_LEFT -> { if (x - MATERIAL_WIDTH >= 0) x -= MATERIAL_WIDTH }
            KeyEvent.VK_D, KeyEvent.VK_RIGHT -> { if (x + MATERIAL_WIDTH * 2 <= TankGame.WIDTH) x += MATERIAL_WIDTH }
            KeyEvent.VK_ENTER -> enterGame()
        }
        when (e.keyChar) {
            in '0'..'9' ->  addObject(e.keyChar.toInt() - '0'.toInt())
        }
        // ctrl + o
        if (e.keyCode == KeyEvent.VK_O && e.isControlDown) {
            saveMap()
            showMenu()
        }
    }

    private fun showMenu() {
        context.stage = MenuStage(context)
    }

    private fun saveMap() {
        val objs = getEffectiveObjects()
        var index = 1
        var file = File("map/$index.txt")
        file.takeUnless { it.parentFile.exists() } ?.parentFile?.mkdirs()
        while (file.exists()) {
            index++
            file = File("map/$index.txt")
        }
        for (o in objs) {
            file.appendText("${o::class.java.name} ${o.x} ${o.y}\n")
        }
    }

    private fun addObject(index: Int) {
        if (index < 0 || index > material.size) {
            return
        }
        objects[y / MATERIAL_WIDTH][x / MATERIAL_WIDTH] = if (index == 0) {
            null
        } else {
            material[index - 1].getConstructor(Int::class.java, Int::class.java).newInstance(x, y)
        }
    }

    private fun enterGame() {
        val objs = getEffectiveObjects()
        context.stage = RunningStage(objs, context, 0)
        play(START_AUDIO)
    }

    private fun getEffectiveObjects() = objects.flatMap(Array<StaticObject?>::asIterable).fold(arrayListOf()) { r: ArrayList<StaticObject>, t -> t?.let { r.add(t) }; r }
}