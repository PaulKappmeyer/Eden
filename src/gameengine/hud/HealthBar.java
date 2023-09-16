package gameengine.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import gameengine.Mob;
import gameengine.maths.MyMaths;
import gameengine.maths.Vector2D;

/**
 * 
 * @author Paul
 *
 */
public class HealthBar {

	private static final float TIME_SHOWING_UP = 1.5f;
	private static final float TIME_FOR_SHOW_UP = 1.5f;
	private static final int FULL_WIDTH = 128;
	private static final int FULL_HEIGHT = 20;
	private static final int DISTANCE = 30;
	private static final boolean SHOW_TEXT = true;

	private Color backgroundColor = new Color(0, 255, 0, 50);
	private Color color = new Color(0, 255, 0, 255);
	private Color outlineColor = new Color(0, 0, 0, 255);
	private Color textColor = new Color(255, 255, 255, 255);

	private Mob object;
	private Vector2D position;
	private int currentWidth;
	private boolean isShown;
	private boolean slidingUp;
	private boolean slidingDown;
	private float time;

	private String text = new String();
	private Font font = new Font("Arial", Font.BOLD, 15);

	public HealthBar(Mob object) {
		this.object = object;
		this.position = new Vector2D();
	}

	public void draw(Graphics graphics) {
		if (isShown) {
			graphics.setColor(backgroundColor);
			graphics.fillRect((int)position.x, (int)position.y, FULL_WIDTH, FULL_HEIGHT);

			graphics.setColor(color);
			graphics.fillRect((int)position.x, (int)position.y, currentWidth, FULL_HEIGHT);

			graphics.setColor(outlineColor);
			graphics.drawRect((int)position.x, (int)position.y, FULL_WIDTH, FULL_HEIGHT);

			if (SHOW_TEXT) {
				graphics.setColor(textColor);
				// Get the FontMetrics
				FontMetrics metrics = graphics.getFontMetrics(font);
				// Determine the X coordinate for the text
				int x = (int)(position.x + (FULL_WIDTH - metrics.stringWidth(text)) / 2);
				// Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
				int y = (int)(position.y + ((FULL_HEIGHT - metrics.getHeight()) / 2) + metrics.getAscent());
				// Set the font
				graphics.setFont(font);
				// Draw the String
				graphics.drawString(text, x, y);
			}
		}
	}

	private String getText(float currentHealth, int maxHealth) {
		return "" + (int)currentHealth;
	}
	
	public void update(float tslf) {
		if (isShown) {
			position.x = object.getX();
			position.y = object.getY() - DISTANCE; 
			currentWidth = (int) MyMaths.mapValue(object.getCurrentHealth(), 0, object.getMaxHealth(), 0, FULL_WIDTH);
			text = getText(object.getCurrentHealth(), object.getMaxHealth());
			
			if (slidingUp) {
				time += tslf;
				position.y = MyMaths.linearInterpolation(object.getY(), object.getY() - DISTANCE, time, TIME_FOR_SHOW_UP);
				if (position.y <= object.getY() - DISTANCE) {
					time = 0;
					slidingUp = false;
				}
			} else if (slidingDown) {
				time += tslf;
				position.y = MyMaths.linearInterpolation(object.getY() - DISTANCE, object.getY(), time, TIME_FOR_SHOW_UP);
				if (position.y >= object.getY()) {
					time = 0;
					slidingDown = false;
					isShown = false;
				}
			} else {
				time += tslf;
				if (time >= TIME_SHOWING_UP) {
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
