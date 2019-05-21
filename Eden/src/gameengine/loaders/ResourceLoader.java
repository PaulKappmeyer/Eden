/*
 * 
 */
package gameengine.loaders;

import java.awt.image.BufferedImage;

import gameengine.graphics.AnimationSet;
import gameengine.sounds.Sound;

/**
 * 
 * @author Daniel Lucarz
 *
 */
public final class ResourceLoader {
	
	@SuppressWarnings("unchecked")
	public static <T> T load(Class<T> classType, String filePath) throws Exception {
		if(classType == BufferedImage.class) {
			return (T) ImageLoader.loadImage(filePath);
		}
		else if(classType == AnimationSet.class) {
			return (T) AnimationSetLoader.loadAnimationSet(filePath);
		}
		else if(classType == Sound.class) {
			return (T) SoundLoader.loadSound(filePath);
		}
		else throw new Exception("Unsupported Type.");
	}
}
