package com.seventh_root.dungeonrunners.screen

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.Gdx.graphics
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.maps.MapRenderer
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.physics.box2d.*
import com.seventh_root.dungeonrunners.component.BodyComponent
import com.seventh_root.dungeonrunners.component.SpriteComponent
import com.seventh_root.dungeonrunners.controller.Controller
import com.seventh_root.dungeonrunners.entity.BreakableEnt
import com.seventh_root.dungeonrunners.entity.PlayerEnt
import com.seventh_root.dungeonrunners.system.MovementSystem


class GameScreen(var world: World, var map: TiledMap): ScreenAdapter() {

    val engine = Engine()
    val spriteBatch = SpriteBatch()
    val controllers = mutableListOf<Controller>()
    val camera = OrthographicCamera()
    val mapRenderer: MapRenderer = OrthogonalTiledMapRenderer(map, spriteBatch)
    val spriteMapper = ComponentMapper.getFor(SpriteComponent::class.java)
    val bodyMapper = ComponentMapper.getFor(BodyComponent::class.java)
    val worldScale = 30F;
    val debugRenderer = Box2DDebugRenderer();
    init {
        camera.setToOrtho(false, graphics.width.toFloat(), graphics.height.toFloat())
        engine.addSystem(MovementSystem)
        map.layers
                .map { layer -> layer.objects }
                .forEach {
                    objects -> objects.forEach {
                        obj ->
                    when (obj.properties.get("type")) {
                        "playerspawn" -> {
                            val player:PlayerEnt = PlayerEnt(obj.properties.get("x") as Float,obj.properties.get("y") as Float,16F,16F,false);
                            val body = world.createBody(player.bodyDef)
                            body.createFixture(player.fixtureDef)
                            body.setTransform(player.pos.x, player.pos.y, 0F)
                            player.entity.add(BodyComponent(body))
                            controllers.add(player.controller)
                            engine.addEntity(player.entity)
                        }
                        "breakablespawn" -> {
                            val breakable:BreakableEnt = BreakableEnt(obj.properties.get("x") as Float,obj.properties.get("y") as Float,16F,16F);
                            val body = world.createBody(breakable.bodyDef)
                            println("Creating box");
                            body.createFixture(breakable.fixtureDef)
                            body.setTransform(breakable.pos.x, breakable.pos.y, 0F)
                            breakable.entity.add(BodyComponent(body))
                            engine.addEntity(breakable.entity)
                        }
                        "wall" -> {
                          /*  val breakable:BreakableEnt = BreakableEnt(obj.properties.get("x") as Float,obj.properties.get("y") as Float);
                            val body = world.createBody(breakable.bodyDef)
                            println("Creating box");
                            body.createFixture(breakable.fixtureDef)
                            body.setTransform(breakable.pos.x, breakable.pos.y, 0F)
                            breakable.entity.add(BodyComponent(body))
                            engine.addEntity(breakable.entity)*/
                        }
                        else -> {
                            //DEFAULT UNKNOWN//
                        }
                                                        }
                           /* val entity = Entity()
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
                            entity.add(BodyComponent(body))*/
                            //engine.addEntity(entity)
                    }
                }
    }

    override fun render(delta: Float) {
        // tick
        controllers.forEach { controller -> controller.tick() }
        engine.update(delta)
        world.step(delta, 6, 2)
        world.clearForces()
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

        debugRenderer.render(world, camera.combined)
    }

    override fun dispose() {
        map.dispose()
    }

    fun P2U(px:Float):Float
    {
        return (px*(1/worldScale))
    }

    fun U2P(un:Float):Float
    {
        return (worldScale*un)//(1/worldUnits)*un
    }

}