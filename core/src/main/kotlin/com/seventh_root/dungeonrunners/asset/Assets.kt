package com.seventh_root.dungeonrunners.asset

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*


class Assets {

    //abstract var background: Texture;

    lateinit var characterDebugani:Array<Animation>;
    public var soundEnabled = false;

    init {
        load();
    }

    open fun loadTexture(file: String): Texture
    {
        return Texture(Gdx.files.internal(file))
    }

    open fun load()
    {
            }

    open fun loadspriteset(sheetname:String):SpriteKit
    {
        println("Loading Sprite Sheet : '${sheetname}'");
        when (sheetname) {
            "debug_character" -> {
                var characterDebug = loadTexture("character_test.png");
                var characterDebugUP = Animation(1f, TextureRegion(characterDebug, 42, 0, 21, 26))
                var characterDebugDOWN = Animation(1f, TextureRegion(characterDebug, 42, 26, 21, 26))
                var characterDebugRIGHT = Animation(1f, TextureRegion(characterDebug, 42, 52, 21, 26))
                var characterDebugLEFT = Animation(15f, TextureRegion(characterDebug, 42, 78, 21, 26))
                var characterDebugWALKUP = Animation(15f, TextureRegion(characterDebug, 0, 0, 21, 26), TextureRegion(characterDebug, 42, 0, 21, 26), TextureRegion(characterDebug, 21, 0, 21, 26), TextureRegion(characterDebug, 42, 0, 21, 26))
                var characterDebugWALKDOWN = Animation(15f, TextureRegion(characterDebug, 0, 26, 21, 26), TextureRegion(characterDebug, 42, 26, 21, 26), TextureRegion(characterDebug, 21, 26, 21, 26), TextureRegion(characterDebug, 42, 26, 21, 26))
                var characterDebugWALKRIGHT = Animation(15f, TextureRegion(characterDebug, 0, 52, 21, 26), TextureRegion(characterDebug, 42, 52, 21, 26), TextureRegion(characterDebug, 21, 52, 21, 26), TextureRegion(characterDebug, 42, 52, 21, 26))
                var characterDebugWALKLEFT = Animation(15f, TextureRegion(characterDebug, 0, 78, 21, 26), TextureRegion(characterDebug, 42, 78, 21, 26), TextureRegion(characterDebug, 21, 78, 21, 26), TextureRegion(characterDebug, 42, 78, 21, 26))
                characterDebugani = arrayOf(characterDebugUP,characterDebugDOWN,characterDebugLEFT,characterDebugRIGHT,characterDebugWALKUP,characterDebugWALKDOWN,characterDebugWALKLEFT,characterDebugWALKRIGHT)
                println(characterDebugani.toString());
                return SpriteKit(characterDebug,characterDebugani)
            }
            else -> {
                return SpriteKit(Texture(Gdx.files.internal("test_tiles.png")),arrayOf())
            }
        }
    }

    open fun playSound(sound: Sound)
    {
        if (soundEnabled)
            sound.play(1F);
    }
}