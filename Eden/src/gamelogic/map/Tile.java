package gamelogic.map;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gameengine.DrawableObject;

/**
 * 
 * @author Paul
 *
 */
public class Tile extends DrawableObject{

	public static final boolean DRAW_ID = false;
	public static final boolean DRAW_OUTLINE = false;
	private int id;
	private static final Font font = new Font("Arial", Font.PLAIN, 20);

	public Tile(int x, int y, int width, int height, int id, BufferedImage image) {
		super(x, y, width, height);
		this.id = id;
		this.image = image;
	}

	@Override
	public void update(float tslf) {
		// TODO Auto-generated method stub
	}

	@Override
	public void draw(Graphics graphics) {
		graphics.drawImage(image, (int)position.x, (int)position.y, width, height, null);

		graphics.setColor(Color.BLACK);

		if(DRAW_OUTLINE)graphics.drawRect((int)position.x, (int)position.y, width, height);

		if(DRAW_ID) {
			// Get the FontMetrics
			FontMetrics metrics = graphics.getFontMetrics(font);
			// Determine the X coordinate for the text
			int x = (int)(position.x + (width - metrics.stringWidth(""+id)) / 2);
			// Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
			int y = (int)(position.y + ((height - metrics.getHeight()) / 2) + metrics.getAscent());
			// Set the font
			graphics.setFont(font);
			// Draw the String
			graphics.drawString(""+id, x, y);
		}
	}

	//-------------------------Getters
	public int getId() {
		return id;
	}
}
