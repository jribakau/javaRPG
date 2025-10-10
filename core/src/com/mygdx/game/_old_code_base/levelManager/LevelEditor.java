//package com.mygdx.game.old_code_base.levelManager;
//
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.mygdx.game.old_code_base.assetManager.AssetManager;
//import com.mygdx.game.old_code_base.entity.Tile;
//import com.mygdx.game.old_code_base.enums.TextureTypeEnum;
//import com.mygdx.game.old_code_base.utils.Constants;
//
//import java.util.ArrayList;
//
//public class LevelEditor {
//    private final ArrayList<Tile> tileList;
//    private final AssetManager assetManager;
//    private Tile selectedTile;
//
//    public LevelEditor(AssetManager assetManager) {
//        this.tileList = new ArrayList<>();
//        this.assetManager = assetManager;
//    }
//
//    public void selectTile(String textureName) {
//        this.selectedTile = new Tile(0, 0, assetManager.getTextureByIndex(TextureTypeEnum.TILE, textureName));
//    }
//
//    public void placeTile(int x, int y) {
//        if (selectedTile != null) {
//            selectedTile.getPosition().setX(x * Constants.TILE_WIDTH);
//            selectedTile.getPosition().setY(y * Constants.TILE_HEIGHT);
//            tileList.add(selectedTile);
//        }
//    }
//
//    public void draw(SpriteBatch batch) {
//        for (Tile tile : tileList) {
//            tile.draw(batch);
//        }
//        if (selectedTile != null) {
//            selectedTile.draw(batch);
//        }
//    }
//}