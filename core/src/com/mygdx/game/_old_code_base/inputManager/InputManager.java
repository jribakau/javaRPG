//package com.mygdx.game.old_code_base.inputManager;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.InputAdapter;
//import com.badlogic.gdx.math.Vector3;
//import com.mygdx.game.old_code_base.cameraManager.CameraManager;
//import com.mygdx.game.old_code_base.entity.Player;
//import com.mygdx.game.old_code_base.events.EventBus;
//import com.mygdx.game.old_code_base.events.entity.EntityClickEvent;
//import com.mygdx.game.old_code_base.events.entity.EntityHighlightEvent;
//import com.mygdx.game.old_code_base.events.input.PlayerMoveEvent;
//import com.mygdx.game.old_code_base.events.input.PlayerStopEvent;
//import com.mygdx.game.old_code_base.events.ui.ScreenChangeEvent;
//import com.mygdx.game.old_code_base.events.ui.ToggleDebugEvent;
//import com.mygdx.game.old_code_base.levelManager.Level;
//import com.mygdx.game.old_code_base.screenManager.LevelEditorScreen;
//import com.mygdx.game.old_code_base.screenManager.MainMenuScreen;
//import com.mygdx.game.old_code_base.utils.Keybindings;
//import com.mygdx.game.RPG;
//import lombok.Getter;
//import lombok.Setter;
//
//@Getter
//@Setter
//public class InputManager extends InputAdapter {
//    private final EventBus eventBus;
//    private Level level;
//    private CameraManager cameraManager;
//    private RPG game;
//
//    public InputManager() {
//        this.eventBus = EventBus.getInstance();
//    }
//
//    public void update() {
//        Player player = level.getPlayer();
//        float dx = 0, dy = 0;
//
//        if (Gdx.input.isKeyPressed(Keybindings.LEFT_KEY)) {
//            dx -= player.getAcceleration();
//        }
//        if (Gdx.input.isKeyPressed(Keybindings.RIGHT_KEY)) {
//            dx += player.getAcceleration();
//        }
//        if (Gdx.input.isKeyPressed(Keybindings.UP_KEY)) {
//            dy += player.getAcceleration();
//        }
//        if (Gdx.input.isKeyPressed(Keybindings.DOWN_KEY)) {
//            dy -= player.getAcceleration();
//        }
//
//        if (dx != 0 || dy != 0) {
//            eventBus.publish(new PlayerMoveEvent(dx, dy));
//        } else {
//            eventBus.publish(new PlayerStopEvent());
//        }
//
//        if (Gdx.input.isKeyJustPressed(Keybindings.DEBUG_KEY)) {
//            eventBus.publish(new ToggleDebugEvent());
//        }
//        if (Gdx.input.isKeyJustPressed(Keybindings.LEVEL_EDITOR_KEY)) {
//            eventBus.publish(new ScreenChangeEvent(new LevelEditorScreen(game)));
//        }
//
//        mouseOverEntity(Gdx.input.getX(), Gdx.input.getY());
//        if (Gdx.input.isButtonPressed(Keybindings.LEFT_CLICK)) {
//            leftClick(Gdx.input.getX(), Gdx.input.getY());
//        }
//
//        if (Gdx.input.isKeyPressed(Keybindings.ESCAPE_KEY)) {
//            eventBus.publish(new ScreenChangeEvent(new MainMenuScreen(game)));
//        }
//    }
//
//    public void mouseOverEntity(int screenX, int screenY) {
//        Vector3 worldCoordinates = cameraManager.getCamera().unproject(new Vector3(screenX, screenY, 0));
//        eventBus.publish(new EntityHighlightEvent(worldCoordinates.x, worldCoordinates.y));
//    }
//
//    public void leftClick(int screenX, int screenY) {
//        Vector3 worldCoordinates = cameraManager.getCamera().unproject(new Vector3(screenX, screenY, 0));
//        eventBus.publish(new EntityClickEvent(worldCoordinates.x, worldCoordinates.y));
//    }
//}
