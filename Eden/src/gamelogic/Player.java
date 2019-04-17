/*
 * 
 */
package gamelogic;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import gameengine.inputs.KeyboardInputManager;
import gameengine.loaders.ImageLoader;
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
	Animation player_walk_up;
	Animation player_walk_down;
	Animation player_walk_left;
	Animation player_walk_right;
	Animation currentAnimation;
	int walkspeed;
	
	public Player(float x, float y) {
		this.position = new Vector2D(x, y);
		this.width = 128;
		this.height = 128;
		this.walkspeed = 100;
		try {
			this.image = ImageLoader.loadImage(".\\res\\eden_tileset.png");
			player_walk_down = new Animation(new BufferedImage[] {image.getSubimage(0, 0, 16, 16), image.getSubimage(0, 16, 16, 16),
					image.getSubimage(0, 32, 16, 16), image.getSubimage(0, 48, 16, 16)}, 0.25f);
			player_walk_up = new Animation(new BufferedImage[] {image.getSubimage(16, 0, 16, 16), image.getSubimage(16, 16, 16, 16),
					image.getSubimage(16, 32, 16, 16), image.getSubimage(16, 48, 16, 16)}, 0.25f);
			player_walk_left = new Animation(new BufferedImage[] {image.getSubimage(32, 0, 16, 16), image.getSubimage(32, 16, 16, 16),
					image.getSubimage(32, 32, 16, 16), image.getSubimage(32, 48, 16, 16)}, 0.25f);
			player_walk_right = new Animation(new BufferedImage[] {image.getSubimage(48, 0, 16, 16), image.getSubimage(48, 16, 16, 16),
					image.getSubimage(48, 32, 16, 16), image.getSubimage(48, 48, 16, 16)}, 0.25f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		currentAnimation = player_walk_down;
	}

	@Override
	public void update(float tslf) {
		if(KeyboardInputManager.isKeyDown(KeyEvent.VK_W)) {
			position.y -= walkspeed * tslf;
			currentAnimation = player_walk_up;
			currentAnimation.play();
		}
		if(KeyboardInputManager.isKeyDown(KeyEvent.VK_A)) {
			position.x -= walkspeed * tslf;
			currentAnimation = player_walk_left;
			currentAnimation.play();
		}
		if(KeyboardInputManager.isKeyDown(KeyEvent.VK_S)) {
			position.y += walkspeed * tslf;
			currentAnimation = player_walk_down;
			currentAnimation.play();
		}
		if(KeyboardInputManager.isKeyDown(KeyEvent.VK_D)) {
			position.x += walkspeed * tslf;
			currentAnimation = player_walk_right;
			currentAnimation.play();
		}
		
		currentAnimation.update(tslf);
		
		this.image = currentAnimation.getCurrentFrame();
	}

	@Override
	public void draw(Graphics graphics) {
		graphics.drawImage(image, (int)position.x, (int)position.y, width, height, null);
	}
	
}
