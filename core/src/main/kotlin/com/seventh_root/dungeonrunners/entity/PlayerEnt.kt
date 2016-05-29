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

    //Used to set Sprite Kit by returning the class's sprite kit name... Might move to Assets class//
    //Could also be used to proc specific class specifications//
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

    //Used to set Sprite Kit//
    fun setSpriteKit(kit:SpriteKit)
    {
        curKit = kit;
    }

    //Used to determine Current State Based off Movements//
    fun setState()
    {
        var xv:Float = controller.linearVelocity.x
        var yv:Float = controller.linearVelocity.y

        //If controller is moving set walking to true//
        if(yv>0||yv<0||xv>0||xv<0)
        {
            isWalking = true
        }else{
            isWalking = false
        }


        //Check the last key pressed//
        //left=0,right=1,up=2,down=3//
        when (controller.lastPressed) {
            controller.keyboardMapping.leftKey -> {
                isFacing = 0
            }
            controller.keyboardMapping.rightKey -> {
                isFacing = 1
            }
            controller.keyboardMapping.upKey -> {
                isFacing = 2
            }
            controller.keyboardMapping.downKey -> {
                isFacing = 3
            }
            else -> {
                //...What key are you pressing?...//
            }
        }

    }

    //Used to update animation//
    //SAVE ME FROM THE DELTA x_x//
    fun updateAnimation()
    {
        if(curKit!=null)
        {
            //Check if Animation is different, If it is, Reset Animation Delta//
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

            sprite.set(Sprite(curKit.animations[curAni].getKeyFrame(tState)))

            tState++
        }
    }

    override fun tick() {
        super.tick()
        setState()
        updateAnimation()
    }

}
