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
	private float currentPlayedTime;
	private int currentSpriteIndex;
	private boolean isPlaying;
	
	public Animation(BufferedImage[] sprites, float timePerSprite) {
		this.sprites = sprites;
		this.timePerSprite = timePerSprite;
		this.currentPlayedTime = 0;
		this.currentSpriteIndex = 0;
	}
	
	public void update(float tslf) {
		if(isPlaying) {
			currentPlayedTime += tslf;
			if(currentPlayedTime >= timePerSprite) {
				currentPlayedTime -= timePerSprite;
				currentSpriteIndex ++;
				if(currentSpriteIndex == sprites.length) currentSpriteIndex = 0;
			}
		}
	}
	
	/**
	 * Sets the boolean {@link #isPlaying} to true
	 */
	public void play() {
		isPlaying = true;
	}
	/**
	 * Sets the boolean {@link #isPlaying} to false
	 */
	public void stop() {
		isPlaying = false;
	}
	/**
	 * Sets {@link #currentPlayedTime} and {@link #currentSpriteIndex} to zero
	 */
	public void reset() {
		currentPlayedTime = 0;
		currentSpriteIndex = 0;
	}
	/**
	 * Returns the current frame of the animation
	 * @return BufferedImage - the current frame
	 */
	public BufferedImage getCurrentFrame() {
		return sprites[currentSpriteIndex]; 
	}
}