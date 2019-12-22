package gameengine.graphics;

import java.awt.image.BufferedImage;

import gamelogic.GameResources;

public class AnimationPlayer {

	private AnimationSet animationSet;
	private Animation currentAnimation;
	private float currentPlayedTime;
	private int currentSpriteIndex;
	private boolean isPlaying;
	private boolean isLooping;
	
	public AnimationPlayer() {
		this.animationSet = new AnimationSet();
		this.currentPlayedTime = 0;
		this.currentSpriteIndex = 0;
	}
	
	public AnimationPlayer(AnimationSet animationSet) {
		this.animationSet = animationSet;
		this.currentPlayedTime = 0;
		this.currentSpriteIndex = 0;
	}
	
	public AnimationPlayer(AnimationSet animationSet, Animation firstAnimation) {
		this.animationSet = animationSet;
		this.currentAnimation = firstAnimation;
		this.currentPlayedTime = 0;
		this.currentSpriteIndex = 0;
	}
	
	public void update(float tslf) {
		if(isPlaying) {
			currentPlayedTime += tslf;
			if(currentPlayedTime >= currentAnimation.getTimePerSprite()) {
				currentPlayedTime -= currentAnimation.getTimePerSprite();
				currentSpriteIndex ++;
				if(currentSpriteIndex == currentAnimation.getSprites().length) {
					if(isLooping) currentSpriteIndex = 0;
					else {
						currentSpriteIndex = currentAnimation.getSprites().length - 1;
						stop();
					}
				}
			}
		}
	}
	
	public boolean isAnimationFinished(String name) {
		Animation newAnimation = animationSet.getAnimation(name);
		if(newAnimation == null) {
			System.err.println("Animation " + name + " not found.");
			return false;
		}else if(newAnimation == currentAnimation && isLooping == false && isPlaying == false) {
			return true;
		}else {
			return false;
		}
	}
	
	public void play(String name) {
		Animation newAnimation = animationSet.getAnimation(name);
		if(newAnimation == null) {
			System.err.println("Animation " + name + " not found.");
			return;
		}else if(newAnimation != currentAnimation) {
			reset();
			currentAnimation = newAnimation;
			isPlaying = true;
		}else {
			isPlaying = true;
		}
	}

	public void loop(String name) {
		Animation newAnimation = animationSet.getAnimation(name);
		if(newAnimation == null) {
			System.err.println("Animation " + name + " not found.");
			return;
		}else if(newAnimation != currentAnimation) {
			reset();
			currentAnimation = newAnimation;
			isPlaying = true;
			isLooping = true;
		}else {
			isPlaying = true;
			isLooping = true;
		}
	}

	public void stop() {
		isPlaying = false;
		isLooping = false;
	}
	
	/**
	 * Sets the {@link #currentPlayedTime} and the {@link #currentSpriteIndex} to zero.
	 */
	public void reset() {
		currentPlayedTime = 0;
		currentSpriteIndex = 0;
	}
	
	/**
	 * Returns the current frame of the playing animation, if the current animation equals null, returns an error-image.
	 * @return The current frame of the playing animation
	 */
	public BufferedImage getCurrentFrame() {
		return currentAnimation != null ? currentAnimation.getSprites()[currentSpriteIndex] : GameResources.ERROR;
	}
	
	/**
	 * This methods adds an animation to set of animations.
	 * @param name The name of the animation
	 * @param animation The animation to add
	 */
	public void addAnimation(String name, Animation animation) {
		animationSet.addAnimation(name, animation);
	}
}
