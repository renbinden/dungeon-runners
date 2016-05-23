package com.seventh_root.dungeonrunners

import com.badlogic.gdx.Game
import com.seventh_root.dungeonrunners.screen.GameScreen
import com.seventh_root.dungeonrunners.screen.MenuScreen

object DungeonRunners : Game() {

    var gameScreen: GameScreen? = null

    override fun create() {
        setScreen(MenuScreen)
    }

    override fun dispose() {
        MenuScreen.dispose()
        gameScreen?.dispose()
    }

}
