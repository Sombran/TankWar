package com.tank.stage

import com.tank.Bullet
import com.tank.Home
import com.tank.StaticObject
import com.tank.TankGame
import com.tank.award.AwardAbstract
import com.tank.award.Bomb
import com.tank.award.DoubleFire
import com.tank.award.TankTimer
import com.tank.bang.BigBang
import com.tank.bang.LittleBang
import com.tank.tankConst.GAME_OVER
import com.tank.tankConst.PAUSE
import com.tank.tankEnum.Award
import com.tank.tankEnum.Direction
import com.tank.tankEnum.GameState
import com.tank.tankEnum.Player
import com.tank.tanks.*
import com.tank.wall.Steel
import java.awt.Color
import java.awt.Graphics
import java.awt.event.KeyEvent
import java.io.File
import java.util.*

/**
 * @author youbo
 * 2018/2/3
 */
class RunningStage(context: TankGame, isPair: Boolean = false, level: Int = 1) : StageAbstract(context) {

    /**
     * 英雄坦克
     */
    private val hero = Hero()

    /**
     * 玩家2
     */
    private val hero2 = if (isPair) Hero(Player.P2) else null

    /**
     * 子弹集合
     */
    private val bullets = mutableListOf<Bullet>()

    /**
     * 敌人坦克
     */
    private val enemies = mutableListOf<EnemyTank>()

    /**
     * 储存被按下的键，用来优化移动体验
     */
    private val keySet = mutableSetOf<Char>()

    /**
     * 储存静止不动的物品，比如墙、爆炸
     */
    private val objects = mutableListOf<StaticObject>()

    /**
     * 奖励，一次只能出现一个，所以使用单个变量存储
     */
    private var award: AwardAbstract? = null

    /**
     * 敌人定时时间
     */
    private var fixedTime = 0

    /**
     * 分数
     */
    private var score = 0

    /**
     * 状态
     */
    private var state = GameState.RUNNING

    /**
     * 家
     */
    private val home = Home()

    init {
        var file = File("map/$level.txt")
        if (!file.exists()) {
           file =  File("map/1.txt")
        }
        if (file.exists()) {
            // 加载地图
            val scanner = Scanner(file)
            while (scanner.hasNext()) {
                val className = scanner.next()
                val clazz = Class.forName(className)
                val x = scanner.nextInt()
                val y = scanner.nextInt()
                val staticObj = clazz.getConstructor(Int::class.java, Int::class.java).newInstance(x, y)
                objects.add(staticObj as StaticObject)
            }
        }

        // TODO 三个位置生成三个敌人
    }

    constructor(objects: List<StaticObject>, context: TankGame) : this(context) {
        this.objects.clear()
        this.objects.addAll(objects)
    }

    override fun paint(g: Graphics) {
        drawHero(g)
        drawEnemy(g)
        drawObject(g)
        drawHome(g)
        drawAward(g)
        drawBullet(g)
        drawScoreArea(g)
        drawState(g)
    }

    private fun drawHero(g: Graphics) {
        g.drawImage(hero.image, hero.x, hero.y, null)
        hero2?.let { g.drawImage(hero2.image, hero2.x, hero2.y, null) }
    }

    private fun drawBullet(g: Graphics) = bullets.forEach { g.drawImage(it.image, it.x, it.y, null) }

    private fun drawEnemy(g: Graphics) = enemies.forEach { g.drawImage(it.image, it.x, it.y, null) }

    private fun drawObject(g: Graphics) = objects.forEach { g.drawImage(it.image, it.x, it.y, it.width, it.height, null) }

    private fun drawAward(g: Graphics) = award?.let { g.drawImage(it.image, it.x, it.y, null) }

    private fun drawHome(g: Graphics) = g.drawImage(home.image, home.x, home.y, null)

    private fun drawState(g: Graphics) {
        if (state == GameState.GAME_OVER) {
            g.drawImage(GAME_OVER, TankGame.WIDTH / 2 - GAME_OVER.width / 2, TankGame.HEIGHT / 2 - GAME_OVER.height / 2, null)
        } else if (state == GameState.PAUSE) {
            g.drawImage(PAUSE, TankGame.WIDTH / 2 - PAUSE.width / 2, TankGame.HEIGHT / 2 - PAUSE.height / 2, null)
        }
    }

    private fun drawScoreArea(g: Graphics) {
        g.color = Color(100, 100, 100)
        g.fillRect(TankGame.WIDTH, 0, TankGame.SCORE_AREA_WEIGHT, TankGame.HEIGHT)
        g.color = Color.BLACK
        g.drawString("score: " + score, TankGame.WIDTH + 10, 20)
    }

    override fun keyPressed(e: KeyEvent) {
        // 暂停和进行中都接收方向按键和开火按键
        if (state == GameState.RUNNING || state == GameState.PAUSE) {
            when (e.keyCode) {
                KeyEvent.VK_W -> { hero.direction = Direction.UP; hero.startMove(); keySet.add(e.keyChar) }
                KeyEvent.VK_S -> { hero.direction = Direction.DOWN; hero.startMove(); keySet.add(e.keyChar) }
                KeyEvent.VK_A -> { hero.direction = Direction.LEFT; hero.startMove(); keySet.add(e.keyChar) }
                KeyEvent.VK_D -> { hero.direction = Direction.RIGHT; hero.startMove(); keySet.add(e.keyChar) }
                KeyEvent.VK_SPACE -> hero.isFire = true
            }
            when (e.keyCode) {
                KeyEvent.VK_I -> { hero2?.direction = Direction.UP; hero2?.startMove(); keySet.add(e.keyChar) }
                KeyEvent.VK_K -> { hero2?.direction = Direction.DOWN; hero2?.startMove(); keySet.add(e.keyChar) }
                KeyEvent.VK_J -> { hero2?.direction = Direction.LEFT; hero2?.startMove(); keySet.add(e.keyChar) }
                KeyEvent.VK_L -> { hero2?.direction = Direction.RIGHT; hero2?.startMove(); keySet.add(e.keyChar) }
                KeyEvent.VK_P -> hero2?.isFire = true
            }
        }
        // 游戏进行时按回车，暂停
        if (state == GameState.RUNNING && KeyEvent.VK_ENTER == e.keyCode) {
            state = GameState.PAUSE
        } else if (state == GameState.PAUSE && KeyEvent.VK_ENTER == e.keyCode) {
            state = GameState.RUNNING
        } else if (state == GameState.GAME_OVER && KeyEvent.VK_ENTER == e.keyCode) {
            context.stage = MenuStage(context)
        }
    }

    override fun keyReleased(e: KeyEvent) {
        if (state == GameState.RUNNING || state == GameState.PAUSE) {
            when (e.keyChar) {
                'w', 's', 'a', 'd', 'i', 'k', 'j', 'l' -> { keySet.remove(e.keyChar); calculateDirection() }
                ' ' -> hero.isFire = false
                'p' -> hero2?.isFire = false
            }
        }
    }

    /**
     * 通过当前按键计算英雄要移动的方向
     */
    private fun calculateDirection() {
        // 查看当前已按下的按钮来控制方向
        when {
            keySet.contains('w') -> hero.direction = Direction.UP
            keySet.contains('s') -> hero.direction = Direction.DOWN
            keySet.contains('a') -> hero.direction = Direction.LEFT
            keySet.contains('d') -> hero.direction = Direction.RIGHT
        }
        when {
            keySet.contains('i') -> hero2?.direction = Direction.UP
            keySet.contains('k') -> hero2?.direction = Direction.DOWN
            keySet.contains('j') -> hero2?.direction = Direction.LEFT
            keySet.contains('l') -> hero2?.direction = Direction.RIGHT
        }
        if (!arrayOf('w', 's', 'a', 'd').any(keySet::contains)) hero.stopMove()
        if (!arrayOf('i', 'k', 'j', 'l').any(keySet::contains)) hero2?.stopMove()
    }

    override fun action() {
        // 游戏进行中和玩家死亡时敌人都能运动f
        if (state == GameState.RUNNING || state == GameState.GAME_OVER) {
            stepAction()
            collisionAction()
            shootAction()
            hitTankAction()
            hitWallAction()
            hitHomeAction()
            dieAction()
            outOfBoundsAction()
            newEnemyAction()
            clearAction()
        }
    }

    private fun stepAction() {
        hero.step()
        hero2?.step()
        bullets.forEach(Bullet::step)
        // 只有定时为0时敌人才能移动
        if (fixedTime == 0) {
            enemies.forEach(EnemyTank::step)
        }
        objects.forEach(StaticObject::step)
        award?.step()
    }

    private fun shootAction() {
        bullets.addAll(hero.shoot())
        hero2?.let { bullets.addAll(it.shoot()) }
        // 定时为0时敌人才能开枪
        if (fixedTime == 0) {
            enemies.forEach { bullets.addAll(it.shoot()) }
        }
    }

    private fun outOfBoundsAction() {
        bullets.filter(Bullet::outOfBounds).forEach {
            bullets.remove(it)
            objects.add(LittleBang(it.x, it.y))
        }
    }

    /**
     * 随机获得一个敌人
     */
    private fun nextEnemy() {
        // TODO
    }

    private fun newEnemyAction() {
        // TODO 检测当前敌人数，小于4时，并且三个位置有空位，生成新敌人
    }

    /**
     * 碰撞
     */
    private fun collisionAction() {
        collisionWallAction()
        collisionTankAction()
        collisionBulletAction()
        collisionAwardAction()
        collisionHomeAction()
    }

    /**
     * 清理
     */
    private fun clearAction() {
        // 清除失效对象
        objects.filterNot(StaticObject::isShow).forEach { objects.remove(it) }
        // 定时减一
        if (fixedTime > 0) fixedTime--
    }

    /**
     * 坦克死亡检测
     */
    private fun dieAction() {
        enemies.filterNot(Tank::isLive).forEach { score += it.getScore(); enemies.remove(it); objects.add(BigBang(it.x, it.y)) }
        if (!hero.isLive) {
            // 将玩家移动到一个无效位置
            hero.x = -100
            hero.y = -100
            hero.stopMove()
        }
        hero2?.takeIf { !it.isLive } ?.let {
            it.x = -200
            it.y = -200
            it.stopMove()
        }
        if (!home.isLive || (!hero.isLive && (hero2 == null || !hero2.isLive))) {
            hero.stopMove()
            hero2?.stopMove()
            state = GameState.GAME_OVER
        }
    }

    /**
     * 子弹攻击坦克检测
     */
    private fun hitTankAction() {
        val bulletIter = bullets.iterator()
        while (bulletIter.hasNext()) {
            val bullet = bulletIter.next()
            if (bullet.isGood) {
                val enemyIter = enemies.iterator()
                while (enemyIter.hasNext()) {
                    val enemy = enemyIter.next()
                    if (enemy.shootBy(bullet)) {
                        enemy.subtractLife()
                        bulletIter.remove()
                        if (enemy.isLive) {
                            objects.add(LittleBang(bullet.x, bullet.y))
                        }
                        awardHand(enemy.getAward())
                        break
                    }
                }
            } else {
                // 敌人子弹
                if (hitHero(bullet, hero)) bulletIter.remove()
                else hero2?.takeIf { hitHero(bullet, it) } ?.let { bulletIter.remove() }
            }
        }
    }

    /**
     * 子弹打英雄
     */
    private fun hitHero(bullet: Bullet, hero: Hero): Boolean {
        val isHit = hero.shootBy(bullet)
        if (isHit) {
            if (!hero.doubleFire) {
                objects.add(BigBang(hero.x, hero.y))
                hero.subtractLife()
                hero.init()
            } else {
                hero.subtractFire()
                objects.add(LittleBang(bullet.x, bullet.y))
            }
        }
        return isHit
    }

    /**
     * 处理奖励
     */
    private fun awardHand(award: Award) {
        this.award = when (award) {
            Award.BOMB -> Bomb()
            Award.DOUBLE_FIRE -> DoubleFire()
            Award.TIMER -> TankTimer()
            else -> { this.award }
        }
    }

    /**
     * 子弹攻击墙
     */
    private fun hitWallAction() {
        val readyAdd = mutableSetOf<StaticObject>()
        objects.forEach {
            val bulletIter = bullets.iterator()
            while (bulletIter.hasNext()) {
                val bullet = bulletIter.next()
                if (it.shootBy(bullet)) {
                    if (it !is Steel || (bullet.isGood && hero.doubleFire)) {
                        it.isShow = false
                    }
                    readyAdd.add(LittleBang(bullet.x, bullet.y))
                    bulletIter.remove()
                }
            }
        }
        objects.addAll(readyAdd)
    }

    /**
     * 撞墙
     */
    private fun collisionWallAction() {
        objects.forEach { obj ->
            enemies.filter(obj::collisionBy).forEach {
                it.backStep()
                it.reStep()
            }
            if (obj.collisionBy(hero)) {
                hero.backStep()
            }
            hero2?.takeIf(obj::collisionBy)?.backStep()
        }
    }

    /**
     * 撞坦克
     */
    private fun collisionTankAction() {
        enemies.forEach { enemy ->
            enemies.forEach { other ->
                if (enemy != other && enemy.collisionBy(other)) {
                    enemy.backStep()
                    other.backStep()
                    enemy.reStep()
                    other.reStep()
                }
            }
            if (enemy.collisionBy(hero)) {
                enemy.backStep()
                hero.backStep()
            }
            hero2?.takeIf(enemy::collisionBy)?.let { it.backStep(); enemy.backStep() }
        }
        hero2?.takeIf(hero::collisionBy)?.let { hero.backStep(); it.backStep() }
    }

    /**
     * 子弹撞子弹,不是同一阵营才会抵消
     */
    private fun collisionBulletAction() {
        val readyDelete = mutableSetOf<Bullet>()
        bullets.forEach { bullet ->
            bullets.filter { bullet.isGood != it.isGood && bullet.collisionBy(it) }.forEach { readyDelete.add(it) }
        }
        bullets.removeAll(readyDelete)
    }

    /**
     * 坦克撞到家
     */
    private fun collisionHomeAction() {
        enemies.filter(home::collisionBy).forEach(EnemyTank::backStep)
        hero.takeIf(home::collisionBy)?.backStep()
        hero2?.takeIf(home::collisionBy)?.backStep()
    }

    /**
     * 子弹打到家
     */
    private fun hitHomeAction() {
        bullets.filter(home::shootBy).forEach { bullets.remove(it); home.isLive = false }
    }

    /**
     * 英雄触碰奖励
     */
    private fun collisionAwardAction() {
        award?.takeIf { it.collisionBy(hero) } ?.let { heroGetAward(it.award, hero) }
        if (hero2 != null) award?.takeIf { it.collisionBy(hero2) } ?.let { heroGetAward(it.award, hero2) }
        award = null
    }

    private fun heroGetAward(award: Award, hero: Hero) {
        when (award) {
            Award.DOUBLE_FIRE -> hero.addFire()
            Award.BOMB -> {
                enemies.forEach { objects.add(BigBang(it.x, it.y)) }
                enemies.clear()
            }
            Award.TIMER -> { fixedTime = (1000 / TankGame.DELAY).toInt() * 10 }
            else -> {}
        }
    }
}