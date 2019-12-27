package gamelogic.enemies;

import java.util.Comparator;

import gameengine.Mob;

/**
 * This class is used for the y-sort of the "zombies".
 * @author Paul
 *
 */
public class ZombieSort implements Comparator<Mob>{

	@Override
	public int compare(Mob zombie1, Mob zombie2) {
		if(zombie1.isAlive() && zombie2.isAlive() || !zombie1.isAlive() && !zombie2.isAlive()) {
			if(zombie1.getCenterPositionY() > zombie2.getCenterPositionY()) {
				return 1;
			}else if(zombie1.getCenterPositionY() < zombie2.getCenterPositionY()) {
				return -1;
			}else {
				return 0;
			}
		}else if(zombie1.isAlive() && !zombie2.isAlive()){
			return 1;
		}else if(!zombie1.isAlive() && zombie2.isAlive()) {
			return -1;
		}else {
			return 0;
		}
	}

}
