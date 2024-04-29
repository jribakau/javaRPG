package com.mygdx.game.assetManager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class AssetManager {
    private Map<String, TextureRegion> animalTextures;
    private Map<String, TextureRegion> itemTextures;
    private Map<String, TextureRegion> tileTextures;
    private Map<String, TextureRegion> monsterTextures;
    private Map<String, TextureRegion> rogueTextures;
    private final TextureUtils textureUtils;

    public AssetManager() {
        textureUtils = new TextureUtils();
    }

    public void loadAssets() {
        animalTextures = textureUtils.parseTextures("assets/32rogues/animals.txt", "assets/32rogues/animals.png");
//        itemTextures = textureUtils.parseTextures("assets/32rogues/items.txt", "assets/32rogues/items.png"); missing txt
//        tileTextures = textureUtils.parseTextures("assets/32rogues/tiles.txt", "assets/32rogues/tiles.png"); missing txt
        monsterTextures = textureUtils.parseTextures("assets/32rogues/monsters.txt", "assets/32rogues/monsters.png");
        rogueTextures = textureUtils.parseTextures("assets/32rogues/rogues.txt", "assets/32rogues/rogues.png");
    }

    public Texture getMonsterTextureByIndex(int index) {
        return textureUtils.getTextureByIndex(index, monsterTextures);
    }

    public Texture getRogueTextureByIndex(int index) {
        return textureUtils.getTextureByIndex(index, rogueTextures);
    }

    public Texture getAnimalTextureByIndex(int index) {
        return textureUtils.getTextureByIndex(index, animalTextures);
    }

    public Texture getRandomMonsterTexture() {
        return textureUtils.getRandomTexture(monsterTextures);
    }

    public Texture getRandomRogueTexture() {
        return textureUtils.getRandomTexture(rogueTextures);
    }

    public Texture getRandomAnimalTexture() {
        return textureUtils.getRandomTexture(animalTextures);
    }
}