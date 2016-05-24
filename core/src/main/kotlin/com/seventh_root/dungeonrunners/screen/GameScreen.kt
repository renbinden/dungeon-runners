package com.seventh_root.dungeonrunners.screen

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx.files
import com.badlogic.gdx.Gdx.graphics
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.MapRenderer
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.World
import com.seventh_root.dungeonrunners.component.BodyComponent
import com.seventh_root.dungeonrunners.component.ControllerComponent
import com.seventh_root.dungeonrunners.component.SpriteComponent
import com.seventh_root.dungeonrunners.controller.Controller
import com.seventh_root.dungeonrunners.controller.KeyboardController
import com.seventh_root.dungeonrunners.system.MovementSystem


class GameScreen(var world: World, var map: TiledMap): ScreenAdapter() {

    val engine = Engine()
    val spriteBatch = SpriteBatch()
    val controllers = mutableListOf<Controller>()
    val camera = OrthographicCamera()
    val mapRenderer: MapRenderer = OrthogonalTiledMapRenderer(map, spriteBatch)
    val spriteMapper = ComponentMapper.getFor(SpriteComponent::class.java)
    val bodyMapper = ComponentMapper.getFor(BodyComponent::class.java)

    init {
        camera.setToOrtho(false, graphics.width.toFloat(), graphics.height.toFloat())
        engine.addSystem(MovementSystem)
        map.layers
                .map { layer -> layer.objects }
                .forEach {
                    objects -> objects.forEach {
                        obj ->
                            val entity = Entity()
                            val bodyDef = BodyDef()
                            bodyDef.type = BodyDef.BodyType.DynamicBody
                            val body = world.createBody(bodyDef)
                            val fixtureDef = FixtureDef()
                            val shape = PolygonShape()
                            shape.setAsBox(16F, 16F)
                            fixtureDef.shape = PolygonShape()
                            body.createFixture(fixtureDef)
                            val rectangleMapObject = obj as RectangleMapObject
                            body.setTransform(rectangleMapObject.rectangle.x, rectangleMapObject.rectangle.y, 0F)
                            entity.add(BodyComponent(body))
                            if (obj.properties.get("type") == "player") {
                                val controller = KeyboardController()
                                controllers.add(controller)
                                entity.add(ControllerComponent(controller))
                                val texture = Texture(files.internal("test_tiles.png"))
                                val region = TextureRegion(texture, 48, 0, 16, 16)
                                entity.add(SpriteComponent(Sprite(region)))
                            }
                            engine.addEntity(entity)
                    }
                }
    }

    override fun render(delta: Float) {
        // tick
        controllers.forEach { controller -> controller.tick() }
        engine.update(delta)
        world.step(delta, 6, 2)
        // render
        camera.update()
        mapRenderer.setView(camera)
        mapRenderer.render()
        spriteBatch.begin()
        engine.entities
                .filter { entity -> spriteMapper.has(entity) }
                .filter { entity -> bodyMapper.has(entity) }
                .forEach { entity -> spriteMapper.get(entity).sprite.setPosition(bodyMapper.get(entity).body.position.x, bodyMapper.get(entity).body.position.y) }
        engine.entities
                .filter { entity -> spriteMapper.has(entity) }
                .map { entity -> spriteMapper.get(entity) }
                .map { spriteComponent -> spriteComponent.sprite }
                .forEach { sprite -> sprite.draw(spriteBatch) }
        spriteBatch.end()
    }

    override fun dispose() {
        map.dispose()
    }

}