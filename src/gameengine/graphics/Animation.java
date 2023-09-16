/*
 * 
 */
package gameengine.graphics;

import java.awt.image.BufferedImage;

/**
 * 
 * @author Paul
 *
 */
public class Animation {

	private BufferedImage[] sprites;
	private float timePerSprite;
	
	public Animation(BufferedImage[] sprites, float timePerSprite) {
		this.sprites = sprites;
		this.timePerSprite = timePerSprite;
	}
	
	public BufferedImage[] getSprites() {
		return sprites;
	}
	
	public float getTimePerSprite() {
		return timePerSprite;
	}
	
	@Override
	public String toString() {
		return "[Sprites:"+sprites.length+"]";
	}
}