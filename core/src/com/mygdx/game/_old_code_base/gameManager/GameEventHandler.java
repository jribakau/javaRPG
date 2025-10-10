//package com.mygdx.game.old_code_base.gameManager;
//
//import com.mygdx.game.old_code_base.assetManager.AssetManager;
//import com.mygdx.game.old_code_base.entity.Character;
//import com.mygdx.game.old_code_base.entity.Entity;
//import com.mygdx.game.old_code_base.enums.TextureTypeEnum;
//import com.mygdx.game.old_code_base.events.entity.*;
//import com.mygdx.game.old_code_base.events.input.PlayerMoveEvent;
//import com.mygdx.game.old_code_base.events.input.PlayerStopEvent;
//import com.mygdx.game.old_code_base.events.level.GenerateRandomTilesEvent;
//import com.mygdx.game.old_code_base.events.level.LoadLevelEvent;
//import com.mygdx.game.old_code_base.levelManager.Level;
//
///**
// * Handles all game events for the GameManager.
// * Separates event handling logic from the main GameManager class.
// */
//public record GameEventHandler(Level level, AssetManager assetManager) {
//    // ========== Player Movement Events ==========
//
//    public void onPlayerMove(PlayerMoveEvent event) {
//        level.getPlayer().move(event.getDx(), event.getDy());
//    }
//
//    public void onPlayerStop(PlayerStopEvent event) {
//        level.getPlayer().stop();
//    }
//
//    // ========== Entity Interaction Events ==========
//
//    public void onEntityHighlight(EntityHighlightEvent event) {
//        for (Entity entity : level.getEntitiesInView()) {
//            entity.setHighlight(entity.containsPoint(event.getWorldX(), event.getWorldY()));
//        }
//    }
//
//    public void onEntityClick(EntityClickEvent event) {
//        // Handle entity clicks - can be extended in the future
//        // Example: Show entity info, start dialogue, etc.
//    }
//
//    // ========== Entity Management Events ==========
//
//    public void onRemoveEntity(RemoveEntityEvent event) {
//        level.getCharacterList().removeIf(entity -> entity.getId().toString().equals(event.getEntityId()));
//    }
//
//    public void onRemoveLastEntity(RemoveLastEntityEvent event) {
//        if (!level.getCharacterList().isEmpty()) {
//            level.getCharacterList().removeLast();
//        }
//    }
//
//    public void onAddEntityAtPlayer(AddEntityAtPlayerEvent event) {
//        float playerX = level.getPlayer().getPosition().getX();
//        float playerY = level.getPlayer().getPosition().getY();
//        Character newCharacter = new Character(playerX, playerY, assetManager.getRandomTexture(TextureTypeEnum.ROGUE));
//        level.getCharacterList().add(newCharacter);
//    }
//
//    // ========== Level Management Events ==========
//
//    public void onLoadLevel(LoadLevelEvent event) {
//        level.clearTileList();
//        level.setTileList(assetManager.getLevels().get(event.getLevelId()));
//    }
//
//    public void onGenerateRandomTiles(GenerateRandomTilesEvent event) {
//        level.getTileList().clear();
//        level.generateLevel();
//    }
//}
//
