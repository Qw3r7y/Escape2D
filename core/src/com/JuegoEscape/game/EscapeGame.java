package com.JuegoEscape.game;

import com.JuegoEscape.game.screens.PantallaJuego;
import com.JuegoEscape.game.screens.PantallaMenu;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EscapeGame extends Game {
	SpriteBatch batch;
	Texture img;
	PantallaJuego juego;
	PantallaMenu menu;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		
		juego = new PantallaJuego(this);
		menu = new PantallaMenu(this);
		
		//setScreen(menu);
		setScreen(juego);
	}
	
	public SpriteBatch getSp() {
		return batch;
	}
}
