/*
 * 
 */
package gameengine.sounds;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Paul
 *
 */
public class SoundSet {
	private Map<String, Sound> sounds;
	
	public SoundSet() {
		sounds = new HashMap<String, Sound>();
	}
	
	public void addSound(String name, Sound sound) {
		sounds.put(name, sound);
	}
	
	public Sound getSound(String name) {
		return sounds.get(name);
	}
}
