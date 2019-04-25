/*
 * 
 */
package gameengine.loaders;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.io.File;
import java.io.FileNotFoundException;

import gameengine.sounds.Sound;

/**
 * 
 * @author Daniel
 *
 */
final class SoundLoader {
	static Sound loadSound(String filePath) throws Exception {
		File soundFile = new File(filePath);
		if(!soundFile.exists()) throw new FileNotFoundException("This file could not be found");
		if(!soundFile.isFile()) throw new Exception("The given path is not a file");
		
		Clip clip = AudioSystem.getClip();
		AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
		
		clip.open(ais);
		
		return new Sound(clip);
	}
}
