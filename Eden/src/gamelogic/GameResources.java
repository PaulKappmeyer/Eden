/*
 * 
 */
package gamelogic;

import java.awt.image.BufferedImage;

import gameengine.graphics.AnimationSet;
import gameengine.loaders.ResourceLoader;
import gameengine.sounds.Sound;

/**
 * 
 * @author Paul
 *
 */
public final class GameResources {

	public static AnimationSet PLAYER_ANIMATION_SET;
	public static AnimationSet NPC_ANIMATION_SET;
	public static Sound PLAYER_WALK_SOUND;
	public static BufferedImage TILESET;
	
	public static void load() {
		try {	
			PLAYER_ANIMATION_SET = ResourceLoader.load(AnimationSet.class, ".\\res\\eden_64.png");
			NPC_ANIMATION_SET = ResourceLoader.load(AnimationSet.class, ".\\res\\npc_64.png");
			PLAYER_WALK_SOUND = ResourceLoader.load(Sound.class, ".\\sfx\\walking_female.wav");
			TILESET = ResourceLoader.load(BufferedImage.class, ".\\res\\Dungeon_1.png");
		} catch (Exception e) {
			
		}
	}
	
}
