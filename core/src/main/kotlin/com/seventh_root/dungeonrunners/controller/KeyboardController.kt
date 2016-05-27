package com.seventh_root.dungeonrunners.controller

import com.badlogic.gdx.Gdx.input

class KeyboardController(val keyboardMapping: KeyboardMapping = KeyboardMapping()): Controller() {

    override fun tick() {
        var x = 0F
        var y = 0F
        var s = 1F
        if (input.isKeyPressed(keyboardMapping.upKey)) {
            y += s//(0.1F*(1F/30F))
        }
        if (input.isKeyPressed(keyboardMapping.leftKey)) {
            x -= s//(0.1F*(1F/30F))
        }
        if (input.isKeyPressed(keyboardMapping.downKey)) {
            y -= s//(0.1F*(1F/30F))
        }
        if (input.isKeyPressed(keyboardMapping.rightKey)) {
            x += s//(0.1F*(1F/30F))
        }
        linearVelocity.set(x, y)
    }

}