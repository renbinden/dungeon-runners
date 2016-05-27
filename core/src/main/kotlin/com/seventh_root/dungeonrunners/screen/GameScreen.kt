package com.seventh_root.dungeonrunners.screen

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx.graphics
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.maps.MapRenderer
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.physics.box2d.*
import com.seventh_root.dungeonrunners.component.BodyComponent
import com.seventh_root.dungeonrunners.component.BrainComponent
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
    val brainMapper = ComponentMapper.getFor(BrainComponent::class.java)
    val units = 1.0F/30.0F;
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
                            val player:PlayerEnt = PlayerEnt(obj.properties.get("x") as Float,obj.properties.get("y") as Float,P2U(16F),P2U(16F),false);
                            player.body = world.createBody(player.bodyDef)
                            player.body.createFixture(player.fixtureDef)
                            player.body.setTransform(P2U(player.pos.x+player.bodyoffset.x), P2U(player.pos.y+player.bodyoffset.y), 0F)
                            player.entity.add(BodyComponent(player.body))
                            controllers.add(player.controller)
                            player.entity.add(BrainComponent(player))
                            engine.addEntity(player.entity)
                        }
                        "breakablespawn" -> {
                            val breakable:BreakableEnt = BreakableEnt(obj.properties.get("x") as Float,obj.properties.get("y") as Float,P2U(16F),P2U(16F));
                            breakable.body = world.createBody(breakable.bodyDef)
                            println("Creating box");
                            breakable.body.createFixture(breakable.fixtureDef)
                            breakable.body.setTransform(P2U(breakable.pos.x+breakable.bodyoffset.x), P2U(breakable.pos.y+breakable.bodyoffset.y), 0F)
                            breakable.entity.add(BodyComponent(breakable.body))
                            breakable.entity.add(BrainComponent(breakable))
                            engine.addEntity(breakable.entity)

                        }
                        "wall" -> {
                            val entity: Entity = Entity()
                            val bodyDef:BodyDef = BodyDef()
                            val fixtureDef:FixtureDef = FixtureDef()
                            val shape:PolygonShape = PolygonShape()
                            var xd = obj.properties.get("x") as Float
                            var yd = obj.properties.get("y") as Float
                            var wd = obj.properties.get("width") as Float
                            var hd = obj.properties.get("height") as Float
                            shape.setAsBox(P2U(wd/2F), P2U(hd/2F))
                            fixtureDef.shape = shape
                            fixtureDef.friction = 1F
                            val body = world.createBody(bodyDef)
                            body.createFixture(fixtureDef)
                            body.setTransform(P2U(xd+(wd/2F)), P2U(yd+(hd/2F)), 0F)
                            entity.add(BodyComponent(body))
                            engine.addEntity(entity)
                        }
                        else -> {
                            //DEFAULT UNKNOWN//
                        }
                                                        }
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
                .filter { entity -> brainMapper.has(entity) }
                .forEach { entity -> brainMapper.get(entity).brain.tick()}
        engine.entities
                .filter { entity -> spriteMapper.has(entity) }
                .filter { entity -> bodyMapper.has(entity) }
                .filter { entity -> brainMapper.has(entity) }
                .forEach { entity -> spriteMapper.get(entity).sprite.setPosition(U2P(bodyMapper.get(entity).body.position.x+brainMapper.get(entity).brain.spriteoffset.x), U2P(bodyMapper.get(entity).body.position.y+brainMapper.get(entity).brain.spriteoffset.y)) }
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
        return px//(px*(1F/worldScale))
    }

    fun U2P(un:Float):Float
    {
        return un//Math.floor((worldScale*un).toDouble()).toFloat()//(1/worldUnits)*un
    }
    //Temp Disable Scaling for box2D//
}