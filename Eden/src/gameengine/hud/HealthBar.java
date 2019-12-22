package gameengine.hud;

import java.awt.Color;
import java.awt.Graphics;

import gameengine.Mob;
import gameengine.maths.MyMaths;
import gameengine.maths.Vector2D;

public class HealthBar {

	private static final float TIME_SHOWING_UP = 1.5f;
	private static final float TIME_FOR_SHOW_UP = 1.5f;
	private static final int FULL_WIDTH = 128;
	private static final int FULL_HEIGHT = 20;
	private static final int DISTANCE = 30;
	private Mob object;
	private Vector2D healthBarPosition;
	private int currentWidth;
	private boolean isShown;
	private boolean slidingUp;
	private boolean slidingDown;
	private float time;

	public HealthBar(Mob object) {
		this.object = object;
		this.healthBarPosition = new Vector2D();
	}

	public void draw(Graphics graphics) {
		if(isShown) {
			graphics.setColor(new Color(0, 255, 0, 180));
			graphics.fillRect((int)healthBarPosition.x, (int)healthBarPosition.y, currentWidth, FULL_HEIGHT);
			graphics.setColor(new Color(0, 0, 0, 180));
			graphics.drawRect((int)healthBarPosition.x, (int)healthBarPosition.y, FULL_WIDTH, FULL_HEIGHT);
		}
	}

	public void update(float tslf) {
		if(isShown) {
			healthBarPosition.x = object.getX();
			healthBarPosition.y = object.getY() - DISTANCE;
			if(object.getCurrentHealth() > 0)  currentWidth = (int) (object.getCurrentHealth() / object.MAX_HEALTH * FULL_WIDTH);
			else currentWidth = 0;
			
			if(slidingUp) {
				time += tslf;
				healthBarPosition.y = MyMaths.linearInterpolation(object.getY(), object.getY() - DISTANCE, time, TIME_FOR_SHOW_UP);
				if(healthBarPosition.y <= object.getY() - DISTANCE) {
					time = 0;
					slidingUp = false;
				}
			}
			else if(slidingDown) {
				time += tslf;
				healthBarPosition.y = MyMaths.linearInterpolation(object.getY() - DISTANCE, object.getY(), time, TIME_FOR_SHOW_UP);
				if(healthBarPosition.y >= object.getY()) {
					time = 0;
					slidingDown = false;
					isShown = false;
				}
			}
			else {
				time += tslf;
				if(time >= TIME_SHOWING_UP) {
					time = 0;
					hide();
				}
			}
		}
	}

	public void show() {
		isShown = true;
		slidingUp = true;
	}

	public void hide() {
		slidingDown = true;
	}

}
