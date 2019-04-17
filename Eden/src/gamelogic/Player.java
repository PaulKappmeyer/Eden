/*
 * 
 */
package gamelogic;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

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
			this.image = ImageLoader.loadImage(".\\res\\eden_32.png");
			int tilesize = 32;
			float time = 0.1f;
			player_walk_down = new Animation(new BufferedImage[] {image.getSubimage(0, 0, tilesize, tilesize), image.getSubimage(0, tilesize, tilesize, tilesize),
					image.getSubimage(0, tilesize*2, tilesize, tilesize), image.getSubimage(0, tilesize*3, tilesize, tilesize)}, time);
			player_walk_up = new Animation(new BufferedImage[] {image.getSubimage(tilesize, 0, tilesize, tilesize), image.getSubimage(tilesize, tilesize, tilesize, tilesize),
					image.getSubimage(tilesize, tilesize*2, tilesize, tilesize), image.getSubimage(tilesize, tilesize*3, tilesize, tilesize)}, time);
			player_walk_left = new Animation(new BufferedImage[] {image.getSubimage(tilesize*2, 0, tilesize, tilesize), image.getSubimage(tilesize*2, tilesize, tilesize, tilesize),
					image.getSubimage(tilesize*2, tilesize*2, tilesize, tilesize), image.getSubimage(tilesize*2, tilesize*3, tilesize, tilesize)}, time);
			player_walk_right = new Animation(new BufferedImage[] {image.getSubimage(tilesize*3, 0, tilesize, tilesize), image.getSubimage(tilesize*3, tilesize, tilesize, tilesize),
					image.getSubimage(tilesize*3, tilesize*2, tilesize, tilesize), image.getSubimage(tilesize*3, tilesize*3, tilesize, tilesize)}, time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		currentAnimation = player_walk_down;
	}

	@Override
	public void update(float tslf) {
		if(PlayerInput.isUpKeyDown()) {
			position.y -= walkspeed * tslf;
			currentAnimation = player_walk_up;
			currentAnimation.play();
		}
		if(PlayerInput.isLeftKeyDown()) {
			position.x -= walkspeed * tslf;
			currentAnimation = player_walk_left;
			currentAnimation.play();
		}
		if(PlayerInput.isDownKeyDown()) {
			position.y += walkspeed * tslf;
			currentAnimation = player_walk_down;
			currentAnimation.play();
		}
		if(PlayerInput.isRightKeyDown()) {
			position.x += walkspeed * tslf;
			currentAnimation = player_walk_right;
			currentAnimation.play();
		}
		
		if(!(PlayerInput.isDownKeyDown() || PlayerInput.isLeftKeyDown() || PlayerInput.isRightKeyDown() || PlayerInput.isUpKeyDown())){
			currentAnimation.reset();
			currentAnimation.stop();
		}
		
		currentAnimation.update(tslf);
		
		this.image = currentAnimation.getCurrentFrame();
	}

	@Override
	public void draw(Graphics graphics) {
		graphics.drawImage(image, (int)position.x, (int)position.y, width, height, null);
	}
	
}
