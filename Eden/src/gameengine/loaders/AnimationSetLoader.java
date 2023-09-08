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

		if (!fileAnimationTextFile.exists()) {
			throw new Exception("AnimationSetLoader: DescriptionFile was not found for " + fileAnimationTextFile.getAbsolutePath());
		}

		FileReader fileReader = new FileReader(fileAnimationTextFile);
		BufferedReader bufferedReader = new BufferedReader(fileReader);

		int width = Integer.parseInt(bufferedReader.readLine());
		int height = Integer.parseInt(bufferedReader.readLine());

		BufferedImage animationSetSprite = ResourceLoader.load(BufferedImage.class, filePath);
		AnimationSet animationSet = new AnimationSet();

		String line = bufferedReader.readLine();
		while (line != null) {
			String name = line;
			String[]array = bufferedReader.readLine().split("]");
			BufferedImage[] sprites = new BufferedImage[array.length];

			for (int i = 0; i < array.length; i++) {
				String[] positions = array[i].split(",");
				int x = Integer.parseInt(positions[0].substring(1));
				int y = Integer.parseInt(positions[1]);
				sprites[i] = animationSetSprite.getSubimage(x*width, y*height, width, height);
			}
			float timePerSprite = Float.parseFloat(bufferedReader.readLine());
			
			Animation animation = new Animation(sprites, timePerSprite);
			animationSet.addAnimation(name, animation);
			
			line = bufferedReader.readLine();
		}

		bufferedReader.close();

		return animationSet;
	}

}
