/*
 * 
 */
package gameengine.loaders;

import java.awt.image.BufferedImage;
import javax.sound.sampled.Clip;

import gameengine.graphics.AnimationSet;

/**
 * 
 * @author Daniel Lucarz
 *
 */
public final class RessourceLoader {
	
	@SuppressWarnings("unchecked")
	public static <T> T load(Class<T> classType, String filePath) throws Exception {
		if(classType == BufferedImage.class) {
			return (T) ImageLoader.loadImage(filePath);
		}
		else if(classType == AnimationSet.class) {
			return (T) AnimationSetLoader.loadAnimationSet(filePath);
		}
		else if(classType == Clip.class) {
			return (T) SoundLoader.loadSound(filePath);
		}
		else throw new Exception("Unsupported Type.");
	}
}
