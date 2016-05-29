package com.seventh_root.dungeonrunners.entity

import com.badlogic.gdx.math.Vector2

abstract class Brain
{
    open val pos: Vector2 = Vector2(0F, 0F)
    open val hotspot: Vector2 = Vector2(0F, 0F) //WILL BE USED TO DETERMINE CENTER AXIS OF ITEM FOR PATH-FINDING//
    open val spriteoffset: Vector2 = Vector2(0F, 0F) //REMOVE, SPRITE KIT WILL HANDLE OFF-SET//
    open fun tick() {}
}
