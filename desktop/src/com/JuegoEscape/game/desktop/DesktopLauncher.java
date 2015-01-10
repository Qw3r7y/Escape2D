package com.JuegoEscape.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.JuegoEscape.game.EscapeGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new EscapeGame(), config);
		config.title= "Juego de Escape";
		config.height = 368;
		config.width = 551;
		config.useGL30 = true;
	}
}
