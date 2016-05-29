package com.seventh_root.dungeonrunners.asset

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*


class Assets {

    fun loadTexture(file: String): Texture
    {
        return Texture(Gdx.files.internal(file))
    }
    fun loadSpriteSet(sheetname:String):SpriteKit
    {
        println("Loading Sprite Sheet : '${sheetname}'");
        when (sheetname) {
            "debug_character" -> {
                val characterDebug = loadTexture("character_test.png");
                //left=0,right=1,up=2,down=3//
                val characterDebugUP = intToAnimation(1F,characterDebug,21,26,intArrayOf(2))
                val characterDebugDOWN = intToAnimation(1F,characterDebug,21,26,intArrayOf(5))
                val characterDebugRIGHT = intToAnimation(1F,characterDebug,21,26,intArrayOf(8))
                val characterDebugLEFT = intToAnimation(1F,characterDebug,21,26,intArrayOf(11))
                val characterDebugWALKUP = intToAnimation(15F,characterDebug,21,26,intArrayOf(0,2,1,2))
                val characterDebugWALKDOWN = intToAnimation(15F,characterDebug,21,26,intArrayOf(3,5,4,5))
                val characterDebugWALKRIGHT = intToAnimation(15F,characterDebug,21,26,intArrayOf(6,8,7,8))
                val characterDebugWALKLEFT = intToAnimation(15F,characterDebug,21,26,intArrayOf(9,11,10,11))
                val characterDebugAnimation = arrayOf(characterDebugUP,characterDebugDOWN,characterDebugLEFT,characterDebugRIGHT,characterDebugWALKUP,characterDebugWALKDOWN,characterDebugWALKLEFT,characterDebugWALKRIGHT)
                return SpriteKit(characterDebug,characterDebugAnimation)
            }
            else -> {
                return SpriteKit(Texture(Gdx.files.internal("test_tiles.png")),arrayOf())
            }
        }
    }

    fun intToAnimation(delta:Float,texture:Texture,width:Int,height:Int,intArray:IntArray):Animation
    {
        val spw:Int = Math.floor((texture.width/width).toDouble()).toInt()
        val textureRegionArray:com.badlogic.gdx.utils.Array<TextureRegion> = com.badlogic.gdx.utils.Array<TextureRegion>(intArray.size)
        intArray.forEach { currentIndex -> textureRegionArray.add(TextureRegion(texture,(currentIndex-(Math.floor((currentIndex/spw).toDouble())*spw)).toInt()*width,Math.floor((currentIndex/spw).toDouble()).toInt()*height,width,height))}
        return Animation(delta,textureRegionArray)
    }

    fun playSound(sound: Sound)
    {
     //   if (soundEnabled)
            sound.play(1F);
    }
}