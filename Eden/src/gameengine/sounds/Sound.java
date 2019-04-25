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
		if(!clip.isRunning()) {
			clip.setMicrosecondPosition(0);
			clip.start();
		}
	}

	public void stop() {
		if(clip.isRunning()) {
			clip.stop();
		}
	}

	public void loop() {
		if(!clip.isRunning()) {
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}

	public void setVolume(float value) {
		volume.setValue(value);
	}
	
	public Clip getClip() {
		return clip;
	}
}
