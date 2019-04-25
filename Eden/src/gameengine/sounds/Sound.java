/*
 * 
 */
package gameengine.sounds;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 * 
 * @author Daniel
 *
 */
public class Sound {
	private Clip clip;
	private FloatControl volume;
	
	public Sound(Clip clip) {
		this.clip = clip;
		volume = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
	}
	
	public void play() {
		clip.setMicrosecondPosition(0);
		clip.start();
	}
	
	public void stop() {
		clip.stop();
	}
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		clip.start();
	}
	
	public void setVolume(float value) {
		volume.setValue(value);
	}
}
