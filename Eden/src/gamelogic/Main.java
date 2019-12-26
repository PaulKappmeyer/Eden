/*
 * 
 */
package gamelogic;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import gameengine.GameBase;
import gameengine.hud.PlayerHUD;
import gameengine.maths.MyRandom;
import gameengine.maths.Vector2D;
import gamelogic.enemies.Zombie;
import gamelogic.enemies.ZombieSort;
import gamelogic.map.TiledMap;
import gamelogic.player.Player;

/**
 * 
 * 
 *
 */
public class Main extends GameBase{

	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 860;
	public static float translateX;
	public static float translateY;
	
	public static final MyRandom RANDOM = new MyRandom();

	public static Player player;
	private PlayerHUD playerHUD;
	private TiledMap tiledMap;
	private LinkedList<Zombie> zombies;
	private ZombieSort zombieSort;
	
	public static void main(String[] args) {
		Main main = new Main();
		main.start("Eden", SCREEN_WIDTH, SCREEN_HEIGHT);
	}	

	@Override
	public void init() {
		GameResources.load();

		player = new Player(400, 400);
		playerHUD = new PlayerHUD(player);
		tiledMap = GameResources.MAP;
		zombies = new LinkedList<Zombie>();
		for (int i = 0; i < 100; i++) {
			Vector2D position = RANDOM.nextVector2D(750, 200, 3500, 3500);
			zombies.add(new Zombie(position.x, position.y));
		}
		zombies.add(new Zombie(750, 400));
		zombieSort = new ZombieSort();
	}

	@Override
	public void update(float tslf) {
		player.update(tslf);
		playerHUD.update(tslf);
		for (Projectile projectile : player.projectiles) {
			projectile.update(tslf);
		}
		
		for (Zombie zombie : zombies) {
			zombie.update(tslf);
			if(zombie.isAlive()) {
				if(zombie.getHitbox().isOverlapping(player.getHitbox())) {
					zombie.getKnockbacked(Vector2D.subtract(zombie.getCenterPosition(), player.getCenterPosition()), zombie.getMAX_KNOCKBACK_AMOUNT(), zombie.getMAX_KNOCKBACK_TIME());
					zombie.getDamaged(50);
					player.getDamaged(50);
					player.getKnockbacked(Vector2D.subtract(player.getCenterPosition(), zombie.getCenterPosition()), player.getMAX_KNOCKBACK_AMOUNT(), player.getMAX_KNOCKBACK_TIME());
				}else {
					//This loop is running from last element to first element because elements get deleted;
					for(int i = player.projectiles.size()-1; i >= 0; i--) {
						Projectile projectile = player.projectiles.get(i);
						if(projectile.getX() < 0 || projectile.getY() < 0) {
							player.projectiles.remove(i);
							continue;
						}
						else if(projectile.getX() > tiledMap.getFullWidth() || projectile.getY() > tiledMap.getFullHeight()) {
							player.projectiles.remove(i);
							continue;
						}
							
						if(zombie.getHitbox().isOverlapping(projectile.getHitbox())) {
							zombie.getKnockbacked(projectile.getVelocityVector(), zombie.getMAX_KNOCKBACK_AMOUNT(), zombie.getMAX_KNOCKBACK_TIME());
							zombie.getDamaged(50);
							if(!zombie.isAlive()) player.addExp(50);
							player.projectiles.remove(i);
						}
					}
				}
			}
		}
		//Y-Sort
		zombies.sort(zombieSort);

		translateX = -player.getX() + SCREEN_WIDTH/2 - player.getWidth()/2;
		translateY = -player.getY() + SCREEN_HEIGHT/2 - player.getHeight()/2;
		if(translateX > 0) translateX = 0;
		if(translateY > 0) translateY = 0;
	}

	
	@Override
	public void draw(Graphics graphics) {
		graphics.setColor(Color.LIGHT_GRAY);
		graphics.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		
		graphics.translate((int)translateX, (int)translateY);

		tiledMap.draw(graphics);
		for (Zombie zombie : zombies) {
			zombie.draw(graphics);
			zombie.getHitbox().draw(graphics);
		}
		player.draw(graphics);
		for (Projectile projectile : player.projectiles) {
			projectile.draw(graphics);
		}
		player.getHitbox().draw(graphics);
		
		graphics.translate((int)-translateX, (int)-translateY);
		
		playerHUD.draw(graphics);
	}	
}