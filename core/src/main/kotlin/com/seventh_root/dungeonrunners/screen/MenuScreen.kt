package com.seventh_root.dungeonrunners.screen

import com.badlogic.gdx.Gdx.*
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.seventh_root.dungeonrunners.DungeonRunners


object MenuScreen: ScreenAdapter() {

    val spriteBatch = SpriteBatch()
    val titleTexture = Texture(files.internal("title.png"))

    override fun show() {
        DungeonRunners.screen = LobbyScreen
    }

    override fun render(delta: Float) {
        gl.glClearColor(0F, 0F, 0F, 1F)
        gl.glClear(GL_COLOR_BUFFER_BIT)
        spriteBatch.begin()
        spriteBatch.draw(titleTexture,
                ((graphics.width - titleTexture.width) / 2).toFloat(),
                (graphics.height - titleTexture.height).toFloat())
        spriteBatch.end()
    }

}