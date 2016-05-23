package com.seventh_root.dungeonrunners.controller

import com.badlogic.gdx.controllers.Controller as Gamepad

class GamepadController(val gamepad: Gamepad, val gamepadMapping: GamepadMapping): Controller() {

    override fun tick() {
        linearVelocity.set(gamepad.getAxis(gamepadMapping.movementHorizontalAxis),
                gamepad.getAxis(gamepadMapping.movementVerticalAxis))
    }
}