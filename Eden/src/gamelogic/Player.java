/*
 * 
 */
package gamelogic;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import gameengine.inputs.KeyboardInputManager;
import gameengine.loaders.ResourceLoader;
import gameengine.maths.Vector2D;

/**
 * 
 * @author Paul
 *
 */
public class Player extends DrawableObject{

	int width;
	int height;
	BufferedImage image;
	int walkspeed;
	
	public Player(float x, float y) {
		this.position = new Vector2D(x, y);
		this.width = 32;
		this.height = 32;
		this.walkspeed = 100;
		try {
			this.image = ResourceLoader.load(BufferedImage.class, ".\\res\\eden_tileset.png").getSubimage(0, 0, 16, 16);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(float tslf) {
		if(KeyboardInputManager.isKeyDown(KeyEvent.VK_W)) {
			position.y -= walkspeed * tslf;
		}
		if(KeyboardInputManager.isKeyDown(KeyEvent.VK_A)) {
			position.x -= walkspeed * tslf;
		}
		if(KeyboardInputManager.isKeyDown(KeyEvent.VK_S)) {
			position.y += walkspeed * tslf;
		}
		if(KeyboardInputManager.isKeyDown(KeyEvent.VK_D)) {
			position.x += walkspeed * tslf;
		}
	}

	@Override
	public void draw(Graphics graphics) {
		graphics.drawImage(image, (int)position.x, (int)position.y, width, height, null);
	}
	
}
