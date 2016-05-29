package com.seventh_root.dungeonrunners.entity

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.seventh_root.dungeonrunners.component.SpriteComponent
//TO-DO ADD IN DIFFERENT FUN & TICK TO CHECK IF BROKEN//
//CREATE FUNCTION TO RETURN A DROPENT TO BE ADDED TO THE MAIN GAMESCREEN//
class BreakableEnt(x:Float,y:Float,width:Float,height:Float):Brain()
{
    val entity:Entity = Entity()

    val bodyDef:BodyDef = BodyDef()
    lateinit var body:Body
    val fixtureDef:FixtureDef = FixtureDef()
    val shape:PolygonShape = PolygonShape()


    val texture = Texture(Gdx.files.internal("test_tiles.png"))
    val region = TextureRegion(texture, 48, 0, 16, 16)

    val bodyoffset:Vector2 = Vector2(width/2F,height/2F)
    val bodysize:Vector2 = Vector2(width,height)

    init
    {
        pos.x = x;pos.y = y;
        spriteoffset.x = -bodyoffset.x;
        spriteoffset.y = -bodyoffset.y;
        shape.setAsBox(width/2F, height/2F)
        fixtureDef.shape = shape;
        fixtureDef.friction = 0.3F;
        bodyDef.type = BodyDef.BodyType.DynamicBody
        bodyDef.linearDamping = 8F;
        entity.add(SpriteComponent(Sprite(region)))
    }

    override fun tick() {
        super.tick()
    }

}