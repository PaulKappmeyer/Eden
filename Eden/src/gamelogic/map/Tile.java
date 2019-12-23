package gamelogic.map;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gameengine.DrawableObject;

/**
 * 
 * @author Paul
 *
 */
public class Tile extends DrawableObject{
	
	private int size;
	
	public Tile(int x, int y, int size, BufferedImage image) {
		super(x, y, size, size);
		this.size = size;
		this.image = image;
	}
	
	@Override
	public void update(float tslf) {
		// TODO Auto-generated method stub
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(image, (int)position.x, (int)position.y, size, size, null);
	}
	
}