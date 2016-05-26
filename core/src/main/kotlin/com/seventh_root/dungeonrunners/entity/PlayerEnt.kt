package com.seventh_root.dungeonrunners.entity

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.seventh_root.dungeonrunners.component.ControllerComponent
import com.seventh_root.dungeonrunners.component.SpriteComponent
import com.seventh_root.dungeonrunners.controller.Controller
import com.seventh_root.dungeonrunners.controller.KeyboardController

class PlayerEnt(x:Float,y:Float,width:Float,height:Float,networkplayer:Boolean)
{
    val entity:Entity = Entity()
    val bodyDef:BodyDef = BodyDef();
    val pos:Vector2 = Vector2(x,y);
    val shape:PolygonShape = PolygonShape();
    val fixtureDef:FixtureDef = FixtureDef();
    lateinit var controller:KeyboardController;
    var isNetwork:Boolean = networkplayer;
    val texture = Texture(Gdx.files.internal("test_tiles.png"))
    val region = TextureRegion(texture, 48, 0, 16, 16)

init
{
    shape.setAsBox(width/2, height/2)
    fixtureDef.shape = shape;
    fixtureDef.friction = 1F;
if (!isNetwork)
{
    controller = KeyboardController()
    entity.add(ControllerComponent(controller))
}
    bodyDef.type = BodyDef.BodyType.DynamicBody
    entity.add(SpriteComponent(Sprite(region)))
}
}
