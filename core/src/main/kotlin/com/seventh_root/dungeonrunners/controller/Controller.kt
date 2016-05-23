package com.seventh_root.dungeonrunners.controller

import com.badlogic.gdx.math.Vector2


abstract class Controller {

    val linearVelocity: Vector2 = Vector2(0F, 0F)
    open fun tick() {}

}