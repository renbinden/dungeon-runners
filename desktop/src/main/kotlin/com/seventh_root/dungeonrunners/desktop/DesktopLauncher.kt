package com.seventh_root.dungeonrunners.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.seventh_root.dungeonrunners.DungeonRunners

fun main(arg: Array<String>) {
    val config = LwjglApplicationConfiguration()
    val displayMode = LwjglApplicationConfiguration.getDesktopDisplayMode()
    config.setFromDisplayMode(displayMode)
    config.fullscreen = false
    config.width = 600
    config.height = 400
    LwjglApplication(DungeonRunners, config)
}
