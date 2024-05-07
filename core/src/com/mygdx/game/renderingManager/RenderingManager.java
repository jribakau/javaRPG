package com.mygdx.game.renderingManager;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.entity.*;
import com.mygdx.game.entity.Character;
import com.mygdx.game.levelManager.Level;
import com.mygdx.game.screenManager.ScreenUtils;
import com.mygdx.game.uiManager.UIGameInfo;

import java.util.List;

public class RenderingManager {
    public static void renderGame(SpriteBatch spriteBatch, Level level, UIGameInfo uiGameInfo, boolean isEntityDebug, Camera camera) {
        ScreenUtils.clearScreen();
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        renderEntities(spriteBatch, level.getTileList(), level.getCharacterList(), level.getItemList(), level.getPlayer());
        uiGameInfo.draw();
        spriteBatch.end();

        renderHighlightedEntities(camera, level.getEntitiesInView());

        if (isEntityDebug) {
            renderEntityDebugShapes(camera, level);
        }
    }

    private static void renderEntities(SpriteBatch batch, List<Tile> tileList, List<Character> characterList, List<Item> itemList, Player player) {
        for (Tile tile : tileList) {
            renderEntity(batch, tile);
        }

        for (Item item : itemList) {
            renderEntity(batch, item);
        }

        for (Character character : characterList) {
            character.getEntityBehavior().update(character);
            renderEntity(batch, character);
        }
        renderEntity(batch, player);
    }

    private static void renderEntity(SpriteBatch batch, Entity entity) {
        if (entity.getIsVisible()) {
            entity.draw(batch);
        }
    }

    private static void renderHighlightedEntities(Camera camera, List<Entity> entities) {
        for (Entity entity : entities) {
            if (entity.isHighlight()) {
                entity.getShapeRenderer().setProjectionMatrix(camera.combined);
                entity.drawHighlight();
            }
        }
    }

    private static void renderEntityDebugShapes(Camera camera, Level level) {
        Player player = level.getPlayer();
        player.getShapeRenderer().setProjectionMatrix(camera.combined);
        player.drawDebug();

        for (Character character : level.getCharacterList()) {
            character.getShapeRenderer().setProjectionMatrix(camera.combined);
            character.drawDebug();
        }
    }
}