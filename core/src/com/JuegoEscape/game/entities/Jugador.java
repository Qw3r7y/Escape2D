package com.JuegoEscape.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

public class Jugador extends Sprite implements InputProcessor {
	
	/** velMovimiento es la velocidad de movimiento del personaje **/
	private Vector2 velMovimiento = new Vector2();
	
	private float speed = 60* 2;
	
	/** 1.8f son los pixeles por segundo **/
	private float gravedad = 60 * 1.8f;
	private float tiempodeAnimacion = 0;
	private float incrementar;
	
	private boolean puedeSaltar;
	private Animation quieto, izquierda, derecha;
	
	private TiledMapTileLayer capaDeColision;
	
	public Jugador(Animation quieto, Animation izquierda, Animation derecha, TiledMapTileLayer capaDeColision) {
			super(quieto.getKeyFrame(0));
			this.quieto = quieto;
			this.izquierda = izquierda;
			this.derecha = derecha;
			this.capaDeColision = capaDeColision;
			setSize(capaDeColision.getWidth() / 3, capaDeColision.getHeight() * 1.25f);
		}

	public Vector2 getvelMovimiento() {
		return velMovimiento;
	}

	public void setvelMovimiento(Vector2 velMovimiento) {
		this.velMovimiento = velMovimiento;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public float getGravedad() {
		return gravedad;
	}

	public void setGravedad(float gravedad) {
		this.gravedad = gravedad;
	}

	
	public TiledMapTileLayer getcapaDeColision() {
		return capaDeColision;
	}

	public void setcapaDeColision(TiledMapTileLayer capaDeColision) {
		this.capaDeColision = capaDeColision;
	}
	
	@Override
	public void draw(Batch Batch) {
		update(Gdx.graphics.getDeltaTime());
		super.draw(Batch);
	}
	
	public void update(float delta) {
		/* Gravedad re cheta */
		velMovimiento.y -= gravedad * delta;
		
		/* velMovimiento de salto */
		if(velMovimiento.y > speed)
			velMovimiento.y = speed;
		else if(velMovimiento.y < -speed)
			velMovimiento.y = -speed;

		
		float oldX = getX(), oldY=getY();
		boolean collisionX = false, collisionY = false;
		
		//movimiento en X
		setX(getX() + velMovimiento.x * delta);
		
		// calculo el incrementaro para #colisionesIzq() and #colisionesDer()
				incrementar = capaDeColision.getTileWidth();
				incrementar = getWidth() < incrementar ? getWidth() / 2 : incrementar / 2;

				if(velMovimiento.x < 0) // going izquierda
					collisionX = colisionIzq();
				else if(velMovimiento.x > 0) // going derecha
					collisionX = colisionDer();

				// Colision en X
				if(collisionX) {
					setX(oldX);
					velMovimiento.x = 0;
				}

				// Movimiento en Y
				setY(getY() + velMovimiento.y * delta * 5f);

				// calculo el incrementaro para #colisionAbajo() and #colisionArriba()
				incrementar = capaDeColision.getTileHeight();
				incrementar = getHeight() < incrementar ? getHeight() / 2 : incrementar / 2;

				if(velMovimiento.y < 0) // going down
					puedeSaltar = collisionY = colisionAbajo();
				else if(velMovimiento.y > 0) // going up
					collisionY = colisionArriba();

				// colision en Y
				if(collisionY) {
					setY(oldY);
					velMovimiento.y = 0;
				}

				// actualizo la animacion
				tiempodeAnimacion += delta;
				setRegion(velMovimiento.x < 0 ? izquierda.getKeyFrame(tiempodeAnimacion) : velMovimiento.x > 0 ? derecha.getKeyFrame(tiempodeAnimacion) : quieto.getKeyFrame(tiempodeAnimacion));
	}
	
	private boolean isCellBlocked(float x, float y) {
		Cell cell = capaDeColision.getCell((int) (x / capaDeColision.getTileWidth()), (int) (y / capaDeColision.getTileHeight()));
		return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("bloqueado");
	}

	public boolean colisionDer() {
		for(float step = 0; step <= getHeight(); step += incrementar)
			if(isCellBlocked(getX() + getWidth(), getY() + step))
				return true;
		return false;
	}

	public boolean colisionIzq() {
		for(float step = 0; step <= getHeight(); step += incrementar)
			if(isCellBlocked(getX(), getY() + step))
				return true;
		return false;
	}

	public boolean colisionArriba() {
		for(float step = 0; step <= getWidth(); step += incrementar)
			if(isCellBlocked(getX() + step, getY() + getHeight()))
				return true;
		return false;

	}

	public boolean colisionAbajo() {
		for(float step = 0; step <= getWidth(); step += incrementar)
			if(isCellBlocked(getX() + step, getY()))
				return true;
		return false;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		switch(keycode) {
		case Keys.W:
			if(puedeSaltar) {
				velMovimiento.y = speed / 1.8f;
				puedeSaltar = false;
			}
			break;
		case Keys.A:
			velMovimiento.x = -speed;
			tiempodeAnimacion = 0;
			break;
		case Keys.D:
			velMovimiento.x = speed;
			tiempodeAnimacion = 0;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch(keycode) {
		case Keys.A:
		case Keys.D:
			velMovimiento.x= 0;
			tiempodeAnimacion = 0;
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}


}
