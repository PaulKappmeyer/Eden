package gameengine.graphics;

import java.awt.image.BufferedImage;

public class AnimationPlayer {

	private AnimationSet animations;
	private Animation currentAnimation;
	
	public AnimationPlayer(AnimationSet animations) {
		this.animations = animations;
	}
	
	public void update(float tslf) {
		currentAnimation.update(tslf);
	}
	
	public void play(String name) {
		currentAnimation = animations.getAnimation(name);
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
}
