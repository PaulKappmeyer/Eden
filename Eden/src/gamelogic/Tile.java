package gamelogic;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gameengine.DrawableObject;

public class Tile extends DrawableObject{
	
	BufferedImage image;
	int size;
	
	public Tile(int x, int y, int size) {
		super(x, y);
		this.size = size;
		this.image = Main.tileSet;
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
