package com.mygdx.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.entity.Player;
import com.mygdx.game.entity.components.StatsComponent;
import com.mygdx.game.world.World;

/**
 * GameHUD - Heads-Up Display for gameplay
 * Demonstrates the power of the new UI system
 */
public class GameHUD {
    private final UIService uiService;
    private final UIBuilder builder;

    // UI Components
    private UIPanel mainPanel;
    private UILabel titleLabel;
    private UILabel fpsLabel;
    private UILabel entityCountLabel;
    private UILabel systemCountLabel;
    private UILabel controlsLabel;

    // Player info panel
    private UIPanel playerPanel;
    private UILabel playerNameLabel;
    private UIProgressBar healthBar;
    private UILabel levelLabel;
    private UILabel xpLabel;
    private UILabel goldLabel;
    private UILabel positionLabel;

    // World info panel
    private UIPanel worldPanel;
    private UILabel worldNameLabel;
    private UILabel worldSizeLabel;

    public GameHUD(UIService uiService) {
        this.uiService = uiService;
        this.builder = new UIBuilder(uiService);

        createMainInfoPanel();
        createPlayerPanel();
        createWorldPanel();
    }

    private void createMainInfoPanel() {
        // Main info panel (top-left)
        mainPanel = builder.panel(10, 10, 300, 150)
            .background(new Color(0, 0, 0, 0.5f))
            .border(new Color(0.7f, 0.7f, 0.7f, 0.8f), 2)
            .padding(10)
            .build("mainPanel");

        // Add labels to main panel
        titleLabel = new UILabel("RPG Game - System Architecture", 20, 140, uiService.getDefaultFont())
            .withColor(Color.CYAN)
            .withShadow(true);
        mainPanel.add(titleLabel);

        fpsLabel = new UILabel("FPS: 0", 20, 120, uiService.getDefaultFont())
            .withColor(Color.WHITE)
            .withShadow(true);
        mainPanel.add(fpsLabel);

        entityCountLabel = new UILabel("Entities: 0", 20, 100, uiService.getDefaultFont())
            .withColor(Color.WHITE)
            .withShadow(true);
        mainPanel.add(entityCountLabel);

        systemCountLabel = new UILabel("Systems: 0", 20, 80, uiService.getDefaultFont())
            .withColor(Color.WHITE)
            .withShadow(true);
        mainPanel.add(systemCountLabel);

        controlsLabel = new UILabel("WASD: Move | ESC: Menu", 20, 60, uiService.getDefaultFont())
            .withColor(Color.YELLOW)
            .withShadow(true);
        mainPanel.add(controlsLabel);
    }

    private void createPlayerPanel() {
        // Player panel (top-left, below main panel)
        playerPanel = builder.panel(10, 170, 300, 180)
            .background(new Color(0.1f, 0.1f, 0.3f, 0.6f))
            .border(new Color(0.5f, 0.5f, 1.0f, 0.8f), 2)
            .padding(10)
            .build("playerPanel");

        playerNameLabel = new UILabel("Player: Hero", 20, 340, uiService.getDefaultFont())
            .withColor(Color.GOLD)
            .withShadow(true);
        playerPanel.add(playerNameLabel);

        // Health bar
        healthBar = builder.progressBar(20, 310, 260, 20, 100)
            .fillColor(Color.RED)
            .backgroundColor(new Color(0.3f, 0, 0, 0.8f))
            .borderColor(Color.WHITE)
            .text("HP: ")
            .animation(true, 3.0f)
            .build("healthBar");
        playerPanel.add(healthBar);

        levelLabel = new UILabel("Level: 1", 20, 285, uiService.getDefaultFont())
            .withColor(Color.WHITE)
            .withShadow(true);
        playerPanel.add(levelLabel);

        xpLabel = new UILabel("XP: 0 / 100", 20, 265, uiService.getDefaultFont())
            .withColor(Color.CYAN)
            .withShadow(true);
        playerPanel.add(xpLabel);

        goldLabel = new UILabel("Gold: 0", 20, 245, uiService.getDefaultFont())
            .withColor(Color.YELLOW)
            .withShadow(true);
        playerPanel.add(goldLabel);

        positionLabel = new UILabel("Position: (0, 0)", 20, 225, uiService.getDefaultFont())
            .withColor(Color.LIGHT_GRAY)
            .withShadow(true);
        playerPanel.add(positionLabel);
    }

    private void createWorldPanel() {
        // World panel (top-left, below player panel)
        worldPanel = builder.panel(10, 360, 300, 80)
            .background(new Color(0.1f, 0.3f, 0.1f, 0.6f))
            .border(new Color(0.5f, 1.0f, 0.5f, 0.8f), 2)
            .padding(10)
            .build("worldPanel");

        worldNameLabel = new UILabel("World: Loading...", 20, 425, uiService.getDefaultFont())
            .withColor(Color.GREEN)
            .withShadow(true);
        worldPanel.add(worldNameLabel);

        worldSizeLabel = new UILabel("Size: 0x0", 20, 405, uiService.getDefaultFont())
            .withColor(Color.WHITE)
            .withShadow(true);
        worldPanel.add(worldSizeLabel);
    }

    /**
     * Update HUD with current game state
     */
    public void update(int fps, int entityCount, int systemCount, Player player, World world) {
        // Update main info
        fpsLabel.setText("FPS: " + fps);
        entityCountLabel.setText("Entities: " + entityCount);
        systemCountLabel.setText("Systems: " + systemCount + " active");

        // Update player info
        if (player != null) {
            playerNameLabel.setText("Player: " + player.getPlayerComponent().getName());

            StatsComponent stats = player.getStatsComponent();
            if (stats != null) {
                healthBar.setValue(stats.getHealth());
                healthBar.setMaxValue(stats.getMaxHealth());

                levelLabel.setText("Level: " + stats.getLevel());

                int currentXP = stats.getExperience();
                int nextLevelXP = stats.getLevel() * 100; // Simple XP calculation
                xpLabel.setText("XP: " + currentXP + " / " + nextLevelXP);

                // Color code health bar based on health percentage
                float healthPercent = healthBar.getPercentage();
                if (healthPercent > 0.6f) {
                    healthBar.withFillColor(Color.GREEN);
                } else if (healthPercent > 0.3f) {
                    healthBar.withFillColor(Color.YELLOW);
                } else {
                    healthBar.withFillColor(Color.RED);
                }
            }

            goldLabel.setText("Gold: " + player.getPlayerComponent().getGold());

            positionLabel.setText("Position: (" +
                (int) player.getPositionComponent().getX() + ", " +
                (int) player.getPositionComponent().getY() + ")");
        }

        // Update world info
        if (world != null) {
            worldNameLabel.setText("World: " + world.getWorldName());
            worldSizeLabel.setText("Size: " + world.getTileMap().getWidth() +
                "x" + world.getTileMap().getHeight() + " tiles");
        }
    }

    /**
     * Toggle HUD visibility
     */
    public void setVisible(boolean visible) {
        mainPanel.setVisible(visible);
        playerPanel.setVisible(visible);
        worldPanel.setVisible(visible);
    }

    /**
     * Clean up resources
     */
    public void dispose() {
        // Components are managed by UIService
    }
}

