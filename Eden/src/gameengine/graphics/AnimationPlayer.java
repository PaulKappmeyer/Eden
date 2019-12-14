package gameengine.graphics;

import java.awt.image.BufferedImage;

import gamelogic.GameResources;

public class AnimationPlayer {

	private AnimationSet animationSet;
	private Animation currentAnimation;
	
	public AnimationPlayer() {
		animationSet = new AnimationSet();
	}
	public AnimationPlayer(AnimationSet animationSet) {
		this.animationSet = animationSet;
	}
	
	public void update(float tslf) {
		if(currentAnimation != null) currentAnimation.update(tslf);
	}
	
	public void loop(String name) {
		currentAnimation = animationSet.getAnimation(name);
		if(currentAnimation != null) {
			currentAnimation.loop();
		}
		else System.err.println("Animation " + name + " not found.");
	}
	
	public void stop() {
		if(currentAnimation != null) currentAnimation.stop();
	}
	
	public void reset() {
		if(currentAnimation != null) currentAnimation.reset();
	}
	
	public BufferedImage getCurrentFrame() {
		return currentAnimation != null ? currentAnimation.getCurrentFrame() : GameResources.ERROR;
	}
	
	public void addAnimation(String name, Animation animation) {
		animationSet.addAnimation(name, animation);
	}
}
