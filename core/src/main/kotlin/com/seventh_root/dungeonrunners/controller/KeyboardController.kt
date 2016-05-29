package com.seventh_root.dungeonrunners.controller

import com.badlogic.gdx.Gdx.input
import com.badlogic.gdx.Input.Keys.*

class KeyboardController(val keyboardMapping: KeyboardMapping = KeyboardMapping()): Controller() {
    var lastPressed:Int = keyboardMapping.downKey
    override fun tick() {
        var x = 0F
        var y = 0F
        var s = 1F
        if (input.isKeyPressed(keyboardMapping.upKey)) {
            y += s//(0.1F*(1F/30F))
            lastPressed = keyboardMapping.upKey
        }
        if (input.isKeyPressed(keyboardMapping.leftKey)) {
            x -= s//(0.1F*(1F/30F))
            lastPressed = keyboardMapping.leftKey
        }
        if (input.isKeyPressed(keyboardMapping.downKey)) {
            y -= s//(0.1F*(1F/30F))
            lastPressed = keyboardMapping.downKey
        }
        if (input.isKeyPressed(keyboardMapping.rightKey)) {
            x += s//(0.1F*(1F/30F))
            lastPressed = keyboardMapping.rightKey
        }
        linearVelocity.set(x, y)
    }

}