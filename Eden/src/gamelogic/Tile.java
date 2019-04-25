package gamelogic;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gameengine.DrawableObject;

public class Tile extends DrawableObject{
	
	BufferedImage image;
	
	public Tile(int x, int y) {
		super(x, y);
		this.image = Main.tileSet;
	}
	
	@Override
	public void update(float tslf) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(image, (int)position.x, (int)position.y, null);
	}
	
}
