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

	static AnimationSet loadAnimationSet(String filePath) throws Exception{
		String filePathWithoutExtension = filePath.substring(0, filePath.lastIndexOf('.'));
		String filePathAnimationTextFile = filePathWithoutExtension + ".txt";
		File fileAnimationTextFile = new File(filePathAnimationTextFile);
		
		if(!fileAnimationTextFile.exists()) throw new Exception("AnimationSetLoader: DescriptionFile was not found for " + filePath);

		FileReader fileReader = new FileReader(fileAnimationTextFile);
		BufferedReader bufferedReader = new BufferedReader(fileReader);

		int width = Integer.parseInt(bufferedReader.readLine());
		int height = Integer.parseInt(bufferedReader.readLine());
		int numberOfAnimations = Integer.parseInt(bufferedReader.readLine());

		BufferedImage animationSetSprite = ResourceLoader.load(BufferedImage.class, filePath);
		int animationSetWidth = animationSetSprite.getWidth()/width;
		
		AnimationSet animationSet = new AnimationSet();
		
		for (int n = 0; n < numberOfAnimations; n++) {
			String name = bufferedReader.readLine();
			String[] index = bufferedReader.readLine().split(",");
			BufferedImage[] sprites = new BufferedImage[index.length];
			for (int i = 0; i < index.length; i++) {
				int a = Integer.parseInt(index[i]);
				sprites[i] = animationSetSprite.getSubimage((a%animationSetWidth)*width, Math.floorDiv(a, animationSetWidth)*height, width, height);
			}
			float timePerSprite = Float.parseFloat(bufferedReader.readLine());
			Animation animation = new Animation(sprites, timePerSprite);
			animationSet.addAnimation(name, animation);
		}
		
		bufferedReader.close();

		return animationSet;
	}

}
