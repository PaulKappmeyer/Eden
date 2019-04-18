/*
 * 
 */
package gameengine.graphics;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Daniel
 *
 */
public class AnimationSet {
	private Map<String, Animation> animations;
	
	public AnimationSet() {
		animations = new HashMap<String, Animation>();
	}
	
	public void addAnimation(String name, Animation animation) {
		animations.put(name, animation);
	}
	
	public Animation getAnimation(String name) {
		return animations.get(name);
	}
}
