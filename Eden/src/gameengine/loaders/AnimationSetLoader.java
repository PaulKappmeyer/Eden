/*
 * 
 */
package gameengine.loaders;

import java.io.FileReader;

import gameengine.graphics.Animation;
import gameengine.graphics.AnimationSet;

import java.io.File;
import java.io.BufferedReader;
import java.awt.image.BufferedImage;

/**
 * 
 * @author Daniel
 *
 */
final class AnimationSetLoader {
		
	public static AnimationSet loadAnimationSet(String filePath) throws Exception{
		String filePathWithoutExtension = filePath.substring(0, filePath.lastIndexOf('.'));
		String filePathAnimationTextFile = filePathWithoutExtension + ".txt";
		File fileAnimationTextFile = new File(filePathAnimationTextFile);
		
		if(!fileAnimationTextFile.exists()) throw new Exception("AnimationSetLoader: DescriptionFile was not found for " + filePath);
		
		FileReader fileReader = new FileReader(fileAnimationTextFile);
		
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		int tileSize = Integer.parseInt(bufferedReader.readLine());
		int frameCounts = Integer.parseInt(bufferedReader.readLine());
		int columnCounts = Integer.parseInt(bufferedReader.readLine());
		float timePerSprite = Float.parseFloat(bufferedReader.readLine());
		
		BufferedImage animationSprite = RessourceLoader.load(BufferedImage.class, filePath);
		
		AnimationSet animationSet = new AnimationSet();
		
		for(int i = 0; i < columnCounts; i++) {
			
			String name = bufferedReader.readLine();
			
			BufferedImage[] sprites = new BufferedImage[frameCounts];
			
			for(int j = 0; j < frameCounts; j++) {
				sprites[j] = animationSprite.getSubimage(tileSize * i, tileSize * j, tileSize, tileSize);
			}
			
			Animation animation = new Animation(sprites, timePerSprite);
			animationSet.addAnimation(name, animation);
		}
			
		bufferedReader.close();
		
		return animationSet;
	}
	
}