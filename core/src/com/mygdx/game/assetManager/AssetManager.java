package com.mygdx.game.assetManager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.entity.Character;
import com.mygdx.game.entity.Tile;
import com.mygdx.game.utils.Files;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.HashMap;
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
    private final Map<String, TextureRegion> allTextures;
    private final List<List<Tile>> levels;
    private final List<Character> characters;

    private final AssetUtils assetUtils;

    public AssetManager() {
        assetUtils = new AssetUtils();
        animalTextures = assetUtils.importSpriteSheetsFromFile(Files.ANIMALS_TXT_PATH, Files.ANIMALS_IMG_PATH);
        itemTextures = assetUtils.importSpriteSheetsFromFile(Files.ITEMS_TXT_PATH, Files.ITEMS_IMG_PATH);
        tileTextures = assetUtils.importSpriteSheetsFromFile(Files.TILES_TXT_PATH, Files.TILES_IMG_PATH);
        monsterTextures = assetUtils.importSpriteSheetsFromFile(Files.MONSTERS_TXT_PATH, Files.MONSTERS_IMG_PATH);
        rogueTextures = assetUtils.importSpriteSheetsFromFile(Files.ROGUES_TXT_PATH, Files.ROGUES_IMG_PATH);
        levels = Collections.singletonList(assetUtils.importTilesFromFile(Files.LEVEL_1_TXT_PATH, tileTextures));

        allTextures = new HashMap<>();
        allTextures.putAll(animalTextures);
        allTextures.putAll(itemTextures);
        allTextures.putAll(tileTextures);
        allTextures.putAll(monsterTextures);
        allTextures.putAll(rogueTextures);

        characters = assetUtils.importCharactersFromFile(Files.CHARACTERS_JSON_PATH, allTextures);
    }

    public Texture getMonsterTextureByIndex(String textureName) {
        return assetUtils.getTextureByName(textureName, monsterTextures);
    }

    public Texture getRogueTextureByIndex(String textureName) {
        return assetUtils.getTextureByName(textureName, rogueTextures);
    }

    public Texture getAnimalTextureByIndex(String textureName) {
        return assetUtils.getTextureByName(textureName, animalTextures);
    }

    public Texture getTileTextureByIndex(String textureName) {
        return assetUtils.getTextureByName(textureName, tileTextures);
    }

    public Texture getItemTextureByIndex(String textureName) {
        return assetUtils.getTextureByName(textureName, itemTextures);
    }

    public Texture getRandomMonsterTexture() {
        return assetUtils.getRandomTexture(monsterTextures);
    }

    public Texture getRandomRogueTexture() {
        return assetUtils.getRandomTexture(rogueTextures);
    }

    public Texture getRandomAnimalTexture() {
        return assetUtils.getRandomTexture(animalTextures);
    }

    public Texture getRandomTileTexture() {
        return assetUtils.getRandomTexture(tileTextures);
    }

    public Texture getRandomItemTexture() {
        return assetUtils.getRandomTexture(itemTextures);
    }
}