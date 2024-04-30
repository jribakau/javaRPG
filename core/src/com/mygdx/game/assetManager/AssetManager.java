package com.mygdx.game.assetManager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.entities.Tile;
import com.mygdx.game.utils.Files;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class AssetManager {
    private final Map<String, TextureRegion> animalTextures;
    private final Map<String, TextureRegion> itemTextures;
    private final Map<String, TextureRegion> tileTextures;
    private final Map<String, TextureRegion> monsterTextures;
    private final Map<String, TextureRegion> rogueTextures;
    private final List<List<Tile>> levels;
    private final TextureUtils textureUtils;

    public AssetManager() {
        textureUtils = new TextureUtils();
        animalTextures = textureUtils.importSpriteSheetsFromFile(Files.ANIMALS_TXT_PATH, Files.ANIMALS_IMG_PATH);
        itemTextures = textureUtils.importSpriteSheetsFromFile(Files.ITEMS_TXT_PATH, Files.ITEMS_IMG_PATH);
        tileTextures = textureUtils.importSpriteSheetsFromFile(Files.TILES_TXT_PATH, Files.TILES_IMG_PATH);
        monsterTextures = textureUtils.importSpriteSheetsFromFile(Files.MONSTERS_TXT_PATH, Files.MONSTERS_IMG_PATH);
        rogueTextures = textureUtils.importSpriteSheetsFromFile(Files.ROGUES_TXT_PATH, Files.ROGUES_IMG_PATH);
        levels = Collections.singletonList(textureUtils.importTilesFromFile(Files.LEVEL_1_TXT_PATH, tileTextures));
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

    public Texture getTileTextureByIndex(int index) {
        return textureUtils.getTextureByIndex(index, tileTextures);
    }

    public Texture getItemTextureByIndex(int index) {
        return textureUtils.getTextureByIndex(index, itemTextures);
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

    public Texture getRandomTileTexture() {
        return textureUtils.getRandomTexture(tileTextures);
    }

    public Texture getRandomItemTexture() {
        return textureUtils.getRandomTexture(itemTextures);
    }
}