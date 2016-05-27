package com.seventh_root.dungeonrunners.entity

import com.badlogic.gdx.math.Vector2

abstract class Brain
{
    open val pos: Vector2 = Vector2(0F, 0F)
    open val spriteoffset: Vector2 = Vector2(0F, 0F)
    open fun tick() {}
}
