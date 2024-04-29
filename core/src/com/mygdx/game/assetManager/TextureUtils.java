package com.mygdx.game.assetManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.mygdx.game.utils.Constants.TEXTURE_SIZE;

public class TextureUtils {
    public Map<String, TextureRegion> parseTextures(String txtPath, String pngPath) {
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
                int col = Character.toLowerCase(parts[1].charAt(0)) - 'a';
                String animalName = parts[2].trim();
                TextureRegion animalTexture = new TextureRegion(texture, col * TEXTURE_SIZE, row * TEXTURE_SIZE, TEXTURE_SIZE, TEXTURE_SIZE);
                textures.put(animalName, animalTexture);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return textures;
    }

    public Texture textureRegionToTexture(TextureRegion region) {
        if (region == null) {
            return null;
        }

        System.out.println("x: " + (region.getRegionX() / 32 + 1) + " y: " + (region.getRegionY() / 32 + 1) + " name: ");

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

    public Texture getTextureByIndex(int index, Map<String, TextureRegion> textures) {
        if (textures == null || textures.isEmpty()) {
            return null;
        }
        return textureRegionToTexture(new ArrayList<>(textures.values()).get(index));
    }
}
