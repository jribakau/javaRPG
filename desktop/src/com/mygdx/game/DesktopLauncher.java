package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.utils.Settings;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("RPG");
		config.setWindowedMode(Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
		config.setForegroundFPS(Settings.FRAME_RATE);
		new Lwjgl3Application(new RPG(), config);
	}
}
