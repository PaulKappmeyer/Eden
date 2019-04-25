package gameengine.graphics;

import java.awt.image.BufferedImage;

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
		currentAnimation.update(tslf);
	}
	
	public void play(String name) {
		currentAnimation = animationSet.getAnimation(name);
		currentAnimation.play();
	}
	
	public void stop() {
		currentAnimation.stop();
	}
	
	public void reset() {
		currentAnimation.reset();
	}
	
	public BufferedImage getCurrentFrame() {
		return currentAnimation.getCurrentFrame();
	}
	
	public void addAnimation(String name, Animation animation) {
		animationSet.addAnimation(name, animation);
	}
}
