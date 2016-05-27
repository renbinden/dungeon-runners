package com.seventh_root.dungeonrunners.screen

import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Box2D
import com.badlogic.gdx.physics.box2d.World
import com.seventh_root.dungeonrunners.DungeonRunners


object LobbyScreen: ScreenAdapter() {

    override fun show() {
        Box2D.init()
        val world = World(Vector2(0F, 0F) , true)
        DungeonRunners.screen = GameScreen(world, TmxMapLoader().load("debug_map.tmx"))
    }

}