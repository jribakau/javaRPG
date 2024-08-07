package com.mygdx.game.assetManager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.entity.Character;
import com.mygdx.game.entity.Tile;
import com.mygdx.game.enums.TextureTypeEnum;
import com.mygdx.game.utils.Files;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class AssetManager {
    private final Map<TextureTypeEnum, Map<String, TextureRegion>> textures;
    private final List<List<Tile>> levels;
    private final List<Character> characters;

    private final AssetUtils assetUtils;

    public AssetManager() {
        assetUtils = new AssetUtils();
        textures = new HashMap<>();

        loadTextures(TextureTypeEnum.ANIMAL, Files.ANIMALS_TXT_PATH, Files.ANIMALS_IMG_PATH);
        loadTextures(TextureTypeEnum.ITEM, Files.ITEMS_TXT_PATH, Files.ITEMS_IMG_PATH);
        loadTextures(TextureTypeEnum.TILE, Files.TILES_TXT_PATH, Files.TILES_IMG_PATH);
        loadTextures(TextureTypeEnum.MONSTER, Files.MONSTERS_TXT_PATH, Files.MONSTERS_IMG_PATH);
        loadTextures(TextureTypeEnum.ROGUE, Files.ROGUES_TXT_PATH, Files.ROGUES_IMG_PATH);

        levels = Collections.singletonList(assetUtils.importTilesFromFile(Files.LEVEL_1_TXT_PATH, textures.get(TextureTypeEnum.TILE)));
        characters = assetUtils.importCharactersFromFile(Files.CHARACTERS_JSON_PATH, textures.get(TextureTypeEnum.ANIMAL));
    }

    private void loadTextures(TextureTypeEnum type, String txtPath, String imgPath) {
        textures.put(type, assetUtils.importSpriteSheetsFromFile(txtPath, imgPath));
    }

    public Texture getTextureByIndex(TextureTypeEnum type, String textureName) {
        return assetUtils.getTextureByName(textureName, textures.get(type));
    }

    public Texture getRandomTexture(TextureTypeEnum type) {
        return assetUtils.getRandomTexture(textures.get(type));
    }
}