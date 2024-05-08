package com.mygdx.game.assetManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mygdx.game.entity.Character;
import com.mygdx.game.entity.Tile;
import com.mygdx.game.enums.EntityTypeEnum;
import com.mygdx.game.enums.TileTypeNum;
import com.mygdx.game.utils.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.*;

import static com.mygdx.game.utils.Constants.TEXTURE_SIZE_32x32;

public class AssetUtils {
    public Map<String, TextureRegion> importSpriteSheetsFromFile(String txtPath, String pngPath) {
        Map<String, TextureRegion> textures = new HashMap<>();
        Texture texture = new Texture(Gdx.files.internal(pngPath));
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Gdx.files.internal(txtPath).read()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split("\\.");
                int row = Integer.parseInt(parts[0]) - 1;
                int col = java.lang.Character.toLowerCase(parts[1].charAt(0)) - 'a';
                String animalName = parts[2].trim();
                TextureRegion animalTexture = new TextureRegion(texture, col * TEXTURE_SIZE_32x32, row * TEXTURE_SIZE_32x32, TEXTURE_SIZE_32x32, TEXTURE_SIZE_32x32);
                textures.put(animalName, animalTexture);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return textures;
    }

    public List<Tile> importTilesFromFile(String txtPath, Map<String, TextureRegion> textures) {
        List<Tile> tiles = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Gdx.files.internal(txtPath).read()))) {
            String line;
            int row = 0;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                for (int col = 0; col < parts.length; col++) {
                    int index = Integer.parseInt(parts[col]);
                    String tileName = TileTypeNum.values()[index].name();
                    Texture texture = getTextureByName(tileName, textures);
                    Tile tile = new Tile(col * Constants.TILE_WIDTH, row * Constants.TILE_HEIGHT, texture);
                    tiles.add(tile);
                }
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tiles;
    }

    public List<Character> importCharactersFromFile(String jsonPath, Map<String, TextureRegion> textures) {
        List<Character> characters = new ArrayList<>();

        try {
            String json = Gdx.files.internal(jsonPath).readString();
            Type type = new TypeToken<Map<String, Map<String, String>>>(){}.getType();
            Map<String, Map<String, String>> data = new Gson().fromJson(json, type);

            for (Map<String, String> characterData : data.values()) {
                String textureName = characterData.get("texture");
                Texture texture = getTextureByName(textureName, textures);
                Character character = new Character(0, 0, texture);
                character.setName(characterData.get("name"));
                character.setEntityTypeEnum(EntityTypeEnum.valueOf(characterData.get("entityType")));
                characters.add(character);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return characters;
    }

    public Texture textureRegionToTexture(TextureRegion region) {
        if (region == null) {
            return null;
        }

        Texture texture = region.getTexture();
        texture.getTextureData().prepare();

        Pixmap pixmap = texture.getTextureData().consumePixmap();
        Pixmap pixmapRegion = new Pixmap(region.getRegionWidth(), region.getRegionHeight(), pixmap.getFormat());

        pixmapRegion.drawPixmap(pixmap, 0, 0, region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight());

        Texture newTexture = new Texture(pixmapRegion);
        pixmapRegion.dispose();
        pixmap.dispose();

        return newTexture;
    }

    public Texture getRandomTexture(Map<String, TextureRegion> textures) {
        if (textures == null || textures.isEmpty()) {
            return null;
        }

        int randomIndex = new Random().nextInt(textures.size());
        return textureRegionToTexture(new ArrayList<>(textures.values()).get(randomIndex));
    }

    public Texture getTextureByName(String textureName, Map<String, TextureRegion> textures) {
        if (textures == null || textures.isEmpty()) {
            return null;
        }
        return textureRegionToTexture(textures.get(textureName));
    }
}
