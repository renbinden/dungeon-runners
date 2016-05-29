package com.seventh_root.dungeonrunners.entity

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.seventh_root.dungeonrunners.asset.SpriteKit
import com.seventh_root.dungeonrunners.component.ControllerComponent
import com.seventh_root.dungeonrunners.component.SpriteComponent
import com.seventh_root.dungeonrunners.controller.KeyboardController

class PlayerEnt(sclass:String,x:Float,y:Float,width:Float,height:Float,networkplayer:Boolean): Brain()
{
    val entity:Entity = Entity()

    val bodyDef:BodyDef = BodyDef()
    lateinit var body:Body
    val fixtureDef:FixtureDef = FixtureDef()
    val shape:PolygonShape = PolygonShape()


    val texture = Texture(Gdx.files.internal("test_tiles.png"))
    val region = TextureRegion(texture, 48, 0, 16, 16)
    val sprite = Sprite()

    lateinit var curKit:SpriteKit
    var isFacing:Int = 1//up-0 down-1 left-2 right-3
    var isWalking:Boolean = false

    var curAni:Int = 0;
    var tState:Float = 0F;

    val bodyoffset:Vector2 = Vector2(width/2F,height/2F)
    val bodysize:Vector2 = Vector2(width,height)

    var isNetwork:Boolean = networkplayer;
    lateinit var controller:KeyboardController

    var skillSet:String = "debug"

init
{
    skillSet = sclass;
    pos.x = x;pos.y = y;
    spriteoffset.x = 0F//-bodyoffset.x;
    spriteoffset.y = -bodyoffset.y;
    shape.setAsBox(width/2F, height/2F)
    fixtureDef.shape = shape;
    fixtureDef.friction = 0F;
if (!isNetwork)
{
    controller = KeyboardController()
    entity.add(ControllerComponent(controller))
}
    bodyDef.type = BodyDef.BodyType.DynamicBody
   // animation.playMode = Animation.PlayMode.LOOP;
    sprite.set(Sprite(region))
    entity.add(SpriteComponent(sprite))
}

    fun spriteSet(): String
    {
        when (skillSet) {
            "debug" -> {
                return "debug_character"
            }
            else -> {
                return "NULL"
            }
        }
    }

    fun setSpriteKit(kit:SpriteKit)
    {
        curKit = kit;
    }

    fun setState()
    {
        var xv:Float = controller.linearVelocity.x
        var yv:Float = controller.linearVelocity.y
        if(yv>0||yv<0||xv>0||xv<0)
        {
            isWalking = true
        }else{
            isWalking = false
        }
        //up-0 down-1 left-2 right-3
        when (controller.lastPressed) {
            controller.keyboardMapping.upKey -> {
                isFacing = 2
            }
            controller.keyboardMapping.downKey -> {
                isFacing = 3
            }
            controller.keyboardMapping.leftKey -> {
                isFacing = 0
            }
            controller.keyboardMapping.rightKey -> {
                isFacing = 1
            }
            else -> {
                //...What key are you pressing?...//
            }
        }

    }

    override fun tick() {
        super.tick()
        setState()
        if(curKit!=null)
        {
            var newAni = isFacing
            if(isWalking) {
                newAni+= 4
            }
            if(newAni!=curAni)
            {
                curAni = newAni;
                tState = 0F;
            }

            if(curKit.animations[curAni].isAnimationFinished(tState))
            {
                tState = 0F;
            }
            //curKit.animations[curAni].playMode = Animation.PlayMode.NORMAL//
            sprite.set(Sprite(curKit.animations[curAni].getKeyFrame(tState)))
            tState++
        }
    }

}
