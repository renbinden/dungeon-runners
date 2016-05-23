package com.seventh_root.dungeonrunners.system

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.EntitySystem
import com.seventh_root.dungeonrunners.component.BodyComponent
import com.seventh_root.dungeonrunners.component.ControllerComponent


object MovementSystem: EntitySystem() {

    val controllerMapper = ComponentMapper.getFor(ControllerComponent::class.java)
    val bodyMapper = ComponentMapper.getFor(BodyComponent::class.java)

    override fun update(deltaTime: Float) {
        engine.entities.forEach { entity ->
            if (bodyMapper.has(entity) && controllerMapper.has(entity)) {
                val x = controllerMapper.get(entity).controller.linearVelocity.x * 400
                val y = controllerMapper.get(entity).controller.linearVelocity.y * 400
                bodyMapper.get(entity).body.setLinearVelocity(x, y)
            }
        }
    }
}