/*
 * 
 */
package gamelogic;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import gameengine.loaders.ImageLoader;
import gameengine.maths.Vector2D;

/**
 * 
 * @author Paul
 *
 */
public class Player extends DrawableObject{

	Vector2D position;
	int width;
	int height;
	BufferedImage image;
	
	public Player(float x, float y) {
		this.position = new Vector2D(x, y);
		this.width = 32;
		this.height = 32;
		File path = new File(".\\res\\eden_tileset.png");
		try {
			this.image = ImageLoader.loadImage(path).getSubimage(0, 0, 16, 16);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(float tslf) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics graphics) {
		graphics.drawImage(image, (int)position.x, (int)position.y, width, height, null);
	}
	
}
