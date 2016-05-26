package com.seventh_root.dungeonrunners

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*


abstract class Assets() {

    //abstract var background: Texture;
    lateinit var characterDebug: Texture;
    lateinit var characterDebugani:Array<Animation>;
    var soundEnabled = false;

    init {
        load();
    }

    open fun loadTexture(file: String): Texture
    {
        return Texture(Gdx.files.internal(file))
    }

    open fun load()
    {
        characterDebug = loadTexture("assets/character_test.png");
        var characterDebugUP = Animation(0.2f,TextureRegion(characterDebug, 42, 0, 21, 26))
        var characterDebugDOWN = Animation(0.2f, TextureRegion(characterDebug, 42, 26, 21, 26))
        var characterDebugRIGHT = Animation(0.2f, TextureRegion(characterDebug, 42, 52, 21, 26))
        var characterDebugLEFT = Animation(0.2f, TextureRegion(characterDebug, 42, 78, 21, 26))
        var characterDebugWALKUP = Animation(0.2f,TextureRegion(characterDebug, 0, 0, 21, 26), TextureRegion(characterDebug, 42, 0, 21, 26), TextureRegion(characterDebug, 21, 0, 21, 26), TextureRegion(characterDebug, 42, 0, 21, 26))
        var characterDebugWALKDOWN = Animation(0.2f,TextureRegion(characterDebug, 0, 26, 21, 26), TextureRegion(characterDebug, 42, 26, 21, 26), TextureRegion(characterDebug, 21, 26, 21, 26), TextureRegion(characterDebug, 42, 26, 21, 26))
        var characterDebugWALKRIGHT = Animation(0.2f,TextureRegion(characterDebug, 0, 52, 21, 26), TextureRegion(characterDebug, 42, 52, 21, 26), TextureRegion(characterDebug, 21, 52, 21, 26), TextureRegion(characterDebug, 42, 52, 21, 26))
        var characterDebugWALKLEFT = Animation(0.2f,TextureRegion(characterDebug, 0, 78, 21, 26), TextureRegion(characterDebug, 42, 78, 21, 26), TextureRegion(characterDebug, 21, 78, 21, 26), TextureRegion(characterDebug, 42, 78, 21, 26))
        characterDebugani = arrayOf(characterDebugUP,characterDebugDOWN,characterDebugRIGHT,characterDebugLEFT,characterDebugWALKUP,characterDebugWALKDOWN,characterDebugWALKLEFT,characterDebugWALKRIGHT)
    }

    open fun playSound(sound: Sound)
    {
        if (soundEnabled)
            sound.play(1F);
    }
}