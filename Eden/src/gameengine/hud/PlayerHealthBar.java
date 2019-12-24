package gameengine.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import gameengine.maths.Vector2D;
import gamelogic.Main;
import gamelogic.player.Player;

public class PlayerHealthBar {

	private static int FULL_WIDTH = 150;
	private static int FULL_HEIGHT = 25;
	private Player player;
	private Vector2D position;
	private int currentWidth;
	private String text;
	private Font font;
	
	public PlayerHealthBar(Player player) {
		this.player = player;
		this.position = new Vector2D(10, 10);
		this.font = new Font("Arial", Font.BOLD, 15);
	}
	
	public void draw(Graphics graphics) {
		graphics.translate((int)-Main.translateX, (int)-Main.translateY);
		
		graphics.setColor(new Color(0, 255, 0, 180));
		graphics.fillRect((int)position.x, (int)position.y, currentWidth, FULL_HEIGHT);
		graphics.setColor(new Color(0, 0, 0, 180));
		graphics.drawRect((int)position.x, (int)position.y, FULL_WIDTH, FULL_HEIGHT);
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
		
		graphics.translate((int)Main.translateX, (int)Main.translateY);
	}
	
	public void update(float tslf) {
		if(player.getCurrentHealth() > 0) {
			float currentHealth = player.getCurrentHealth();
			currentWidth = (int) (currentHealth / player.MAX_HEALTH * FULL_WIDTH);
			text = "HEALTH: " + (int)player.getCurrentHealth() + "/" + player.MAX_HEALTH;
		}else {
			currentWidth = 0;
			text = "HEALTH: " + (int)0 + "/" + player.MAX_HEALTH;
		}
	}
	
}
