package com.JuegoEscape.game.screens;

import com.JuegoEscape.game.EscapeGame;
import com.JuegoEscape.game.entities.Jugador;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class PantallaJuego extends PantallaBase implements Screen {
	SpriteBatch batch;
	EscapeGame game;
	
	private TextureAtlas atlasJugador;
	private Jugador jugador;
	
	private TiledMap escenario;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camara;
	
	private int[] fondo = new int[] {0}, piso = new int[] {1}, fondotop = new int[] {2};
	
	public PantallaJuego(EscapeGame escapeGame) {
		super();
		this.game=escapeGame;
		batch = game.getSp();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camara.position.set(jugador.getX() + jugador.getWidth() / 2, jugador.getY() + jugador.getHeight() / 2, 0);
		camara.update();
		renderer.setView(camara);
		renderer.render();
		
		renderer.render(fondo);
		renderer.render(piso);
		
		renderer.getBatch().begin();
		jugador.draw(renderer.getBatch());
		renderer.getBatch().end();
		
		renderer.render(fondotop);
	}
	
	@Override
	public void resize(int width, int height) {
		camara.viewportWidth = width /1.5f;
		camara.viewportHeight= height /1.5f;
	}
	
	@Override
	public void show() {
		escenario = new TmxMapLoader().load("escenarios/escena1.tmx");
		renderer = new OrthogonalTiledMapRenderer(escenario);
		camara = new OrthographicCamera();
		
		atlasJugador = new TextureAtlas("personajes/personaje1");
		Animation quieto, izquierda, derecha;
		quieto = new Animation(1 / 2f, atlasJugador.findRegions("derecha"));
		izquierda = new Animation(1 / 6f, atlasJugador.findRegions("izquierda"));
		derecha = new Animation(1 / 6f, atlasJugador.findRegions("derecha"));
		quieto.setPlayMode(Animation.PlayMode.LOOP);
		izquierda.setPlayMode(Animation.PlayMode.LOOP);
		derecha.setPlayMode(Animation.PlayMode.LOOP);
		
		jugador = new Jugador(quieto, izquierda, derecha, (TiledMapTileLayer) escenario.getLayers().get(1));
		
        jugador.setPosition(9 * jugador.getcapaDeColision().getTileWidth(), (jugador.getcapaDeColision().getHeight() - 2) * jugador.getcapaDeColision().getTileHeight());

		
		Gdx.input.setInputProcessor(jugador);
	}
	
	@Override
	public void hide() {
		dispose();
	}
	
	@Override
	public void pause() {
	}
	
	@Override
	public void resume() {
	}
	
	@Override
	public void dispose() {
		escenario.dispose();
		renderer.dispose();
		jugador.getTexture().dispose();
		atlasJugador.dispose();
	}
}
