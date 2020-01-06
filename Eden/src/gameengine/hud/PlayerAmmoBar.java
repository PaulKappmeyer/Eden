/**
 * 
 */
package gameengine.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import gameengine.maths.MyMaths;
import gameengine.maths.Vector2D;
import gamelogic.player.Player;

/**
 * @author Paul
 *
 */
public class PlayerAmmoBar {

	private Color backgroundColor = new Color(150, 150, 150, 50);
	private Color color = new Color(150, 150, 150, 255);
	private Color outlineColor = new Color(0, 0, 0, 255);
	private Color textColor = new Color(255, 255, 255, 255);
	
	private int FULL_WIDTH;
	private int FULL_HEIGHT;
	private Player player;
	private Vector2D position; //Real position on the screen
	private int currentWidth;
	private String text;
	private Font font;
	
	public PlayerAmmoBar(Player player, int x, int y, int FULL_WIDTH, int FULL_HEIGHT, Font font) {
		this.player = player;
		this.position = new Vector2D(x, y);
		this.FULL_WIDTH = FULL_WIDTH;
		this.FULL_HEIGHT = FULL_HEIGHT;
		this.text = new String();
		this.font = font;
	}
	
	private String getText(int currentAmmo, int maxAmmo) {
		return "Ammo: " + (int)currentAmmo + " / " + (int)maxAmmo;
	}
	
	public void draw(Graphics graphics) {
		graphics.setColor(backgroundColor);
		graphics.fillRect((int)position.x, (int)position.y, FULL_WIDTH, FULL_HEIGHT);
		
		graphics.setColor(color);
		graphics.fillRect((int)position.x, (int)position.y, currentWidth, FULL_HEIGHT);
		
		graphics.setColor(outlineColor);
		graphics.drawRect((int)position.x, (int)position.y, FULL_WIDTH, FULL_HEIGHT);
		
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
	
	public void update(float tslf) {
		int currentAmmo = player.getGun().getCurrentAmmo();
		int maxAmmo = player.getGun().getMaxAmmo();
		currentWidth = (int) (MyMaths.mapValue(currentAmmo, 0, maxAmmo, 0, FULL_WIDTH));
		text = getText(currentAmmo, maxAmmo);
	}
	
}
