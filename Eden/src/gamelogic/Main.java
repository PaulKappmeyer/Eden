/*
 * 
 */
package gamelogic;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Comparator;
import java.util.LinkedList;

import gameengine.GameBase;
import gameengine.maths.MyRandom;
import gameengine.maths.Vector2D;
import gamelogic.player.Player;

/**
 * 
 * 
 *
 */
public class Main extends GameBase{

	static int width;
	static int height;

	public static final MyRandom RANDOM = new MyRandom();

	public static Player player;
	private TiledMap tiledMap;
	private LinkedList<Zombie> zombies;

	public static void main(String[] args) {
		Main main = new Main();
		Main.width = 1280;
		Main.height = 860;
		main.start("Eden", width, height);
	}	

	@Override
	public void init() {
		GameResources.load();

		player = new Player(400, 400);
		tiledMap = new TiledMap(100, 100, 128);
		zombies = new LinkedList<Zombie>();
		for (int i = 0; i < 100; i++) {
			Vector2D position = RANDOM.nextVector2D(750, 200, 3500, 3500);
			zombies.add(new Zombie(position.x, position.y));
		}
		zombies.add(new Zombie(750, 400));
	}

	@Override
	public void update(float tslf) {
		player.update(tslf);
		for (Zombie zombie : zombies) {
			zombie.update(tslf);
			if(zombie.getHitbox().isOverlapping(player.getHitbox())) {
				zombie.getKnockbacked(Vector2D.subtract(zombie.getCenterPosition(), player.getCenterPosition()).makeUnitVector());
				zombie.getDamaged(50);
				player.getDamaged(50);
				player.getKnockbacked(Vector2D.subtract(player.getCenterPosition(), zombie.getCenterPosition()).makeUnitVector());
			}
		}
		zombies.sort(new Comparator<Zombie>() {

			@Override
			public int compare(Zombie zombie1, Zombie zombie2) {
				if(zombie1.isAlive && zombie2.isAlive || !zombie1.isAlive && !zombie2.isAlive) {
					if(zombie1.getCenterPositionY() > zombie2.getCenterPositionY()) {
						return 1;
					}else if(zombie1.getCenterPositionY() < zombie2.getCenterPositionY()) {
						return -1;
					}else {
						return 0;
					}
				}else if(zombie1.isAlive && !zombie2.isAlive){
					return 1;
				}else if(!zombie1.isAlive && zombie2.isAlive) {
					return -1;
				}else {
					return 0;
				}
			}
		});
	}

	@Override
	public void draw(Graphics graphics) {
		graphics.setColor(Color.LIGHT_GRAY);
		graphics.fillRect(0, 0, width, height);

		graphics.translate((int)-player.getX() + width/2 - player.getWidth()/2, (int)-player.getY()  + height/2 - player.getHeight()/2);

		tiledMap.draw(graphics);
		for (Zombie zombie : zombies) {
			zombie.draw(graphics);
		}
		player.draw(graphics);
	}	
}