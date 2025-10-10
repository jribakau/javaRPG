package com.mygdx.game.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.core.ServiceLocator;
import com.mygdx.game.core.services.CameraService;
import com.mygdx.game.core.services.RenderService;
import com.mygdx.game.entity.Player;
import com.mygdx.game.entity.components.InventoryComponent;
import com.mygdx.game.entity.components.StatsComponent;

/**
 * InventoryState - Inventory screen overlay
 * Shows player inventory, stats, and equipment
 */
public class InventoryState extends GameState {
    private CameraService cameraService;
    private RenderService renderService;
    private BitmapFont font;
    private BitmapFont titleFont;
    private final Player player;

    public InventoryState(GameStateManager stateManager, Player player) {
        super(stateManager);
        this.player = player;
    }

    @Override
    protected void onCreate() {
        cameraService = ServiceLocator.get(CameraService.class);
        renderService = ServiceLocator.get(RenderService.class);

        font = new BitmapFont();
        font.getData().setScale(1.3f);

        titleFont = new BitmapFont();
        titleFont.getData().setScale(2.0f);
        titleFont.setColor(Color.CYAN);

        Gdx.app.log("InventoryState", "Inventory state created");
    }

    @Override
    public void onEnter() {
        Gdx.app.log("InventoryState", "Inventory opened");
    }

    @Override
    public void onExit() {
        Gdx.app.log("InventoryState", "Inventory closed");
    }

    @Override
    public void update(float delta) {
        // Check for close inventory
        if (Gdx.input.isKeyJustPressed(Input.Keys.I) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            stateManager.popState();
        }
    }

    @Override
    public void render(float delta) {
        // Render semi-transparent overlay
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        renderService.beginShapes(cameraService, ShapeRenderer.ShapeType.Filled);
        renderService.getShapeRenderer().setColor(0, 0, 0, 0.8f);
        renderService.getShapeRenderer().rect(0, 0, 800, 600);
        renderService.endShapes();

        // Render inventory panel
        renderService.beginShapes(cameraService, ShapeRenderer.ShapeType.Filled);
        renderService.getShapeRenderer().setColor(0.2f, 0.2f, 0.3f, 0.95f);
        renderService.getShapeRenderer().rect(100, 100, 600, 400);
        renderService.endShapes();

        // Render inventory border
        renderService.beginShapes(cameraService, ShapeRenderer.ShapeType.Line);
        renderService.getShapeRenderer().setColor(Color.GOLD);
        renderService.getShapeRenderer().rect(100, 100, 600, 400);
        renderService.endShapes();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        // Render inventory contents
        renderService.begin(cameraService);

        titleFont.draw(renderService.getBatch(), "INVENTORY", 320, 470);

        if (player != null) {
            StatsComponent stats = player.getStatsComponent();
            InventoryComponent inventory = player.getInventoryComponent();

            float y = 420;

            // Player stats section
            font.setColor(Color.YELLOW);
            font.draw(renderService.getBatch(), "=== Character Stats ===", 150, y);
            font.setColor(Color.WHITE);
            y -= 30;

            if (stats != null) {
                font.draw(renderService.getBatch(), "Name: " + player.getPlayerComponent().getName(), 150, y);
                y -= 25;
                font.draw(renderService.getBatch(), "Level: " + stats.getLevel() + " (XP: " + stats.getExperience() + ")", 150, y);
                y -= 25;
                font.draw(renderService.getBatch(), "HP: " + stats.getHealth() + "/" + stats.getMaxHealth(), 150, y);
                y -= 25;
                font.draw(renderService.getBatch(), "MP: " + stats.getMana() + "/" + stats.getMaxMana(), 150, y);
                y -= 25;
                font.draw(renderService.getBatch(), "Attack: " + stats.getAttack(), 150, y);
                y -= 25;
                font.draw(renderService.getBatch(), "Defense: " + stats.getDefense(), 150, y);
                y -= 25;
                font.draw(renderService.getBatch(), "Magic: " + stats.getMagic(), 150, y);
                y -= 25;
            }

            font.draw(renderService.getBatch(), "Gold: " + player.getPlayerComponent().getGold(), 150, y);
            y -= 40;

            // Inventory section
            font.setColor(Color.YELLOW);
            font.draw(renderService.getBatch(), "=== Inventory ===", 150, y);
            font.setColor(Color.WHITE);
            y -= 30;

            if (inventory != null) {
                font.draw(renderService.getBatch(), "Capacity: " + inventory.getItemCount() + "/" + inventory.getMaxCapacity(), 150, y);
                y -= 25;

                if (inventory.getItemCount() == 0) {
                    font.setColor(Color.GRAY);
                    font.draw(renderService.getBatch(), "Empty", 150, y);
                    font.setColor(Color.WHITE);
                } else {
                    font.draw(renderService.getBatch(), "(Items will be displayed here)", 150, y);
                }
            }
        }

        // Controls
        font.setColor(Color.GRAY);
        font.draw(renderService.getBatch(), "Press I or ESC to close", 250, 130);
        font.setColor(Color.WHITE);

        renderService.end();
    }

    @Override
    public boolean blocksUpdate() {
        return true; // Don't update playing state while in inventory
    }

    @Override
    public boolean blocksRender() {
        return false; // Still render playing state below
    }

    @Override
    public void dispose() {
        if (font != null) {
            font.dispose();
        }
        if (titleFont != null) {
            titleFont.dispose();
        }
    }
}
