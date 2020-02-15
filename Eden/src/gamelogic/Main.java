/*
 * 
 */
package gamelogic;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import gameengine.GameBase;
import gameengine.Mob;
import gameengine.graphics.Camera;
import gameengine.hud.PlayerHUD;
import gameengine.maths.MyRandom;
import gameengine.maths.Vector2D;
import gamelogic.enemies.ShootingZombie;
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

	public static final MyRandom RANDOM = new MyRandom();

	public static Player player;
	public static  Camera camera;
	public static TiledMap tiledMap;
	public static LinkedList<Projectile> projectiles;
	
	private PlayerHUD playerHUD;
	private LinkedList<Mob> zombies;
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
		zombies = new LinkedList<>();
		for (int i = 0; i < 10; i++) {
			Vector2D position = RANDOM.nextVector2D(750, 200, 3500, 3500);
			zombies.add(new ShootingZombie(position.x, position.y));
		}
		zombieSort = new ZombieSort();
		projectiles = new LinkedList<>();
		camera = new Camera();
		camera.setFocusedObject(player);
	}

	public void onPlayerDeath() {
		player = new Player(400, 400);
		playerHUD = new PlayerHUD(player);
		tiledMap = GameResources.MAP;
		zombies = new LinkedList<>();
		for (int i = 0; i < 10; i++) {
			Vector2D position = RANDOM.nextVector2D(750, 200, 3500, 3500);
			zombies.add(new ShootingZombie(position.x, position.y));
		}
		zombieSort = new ZombieSort();
		projectiles = new LinkedList<>();
		camera = new Camera();
		camera.setFocusedObject(player);
	}
	
	@Override
	public void update(float tslf) {
		//Update the player
		player.update(tslf);
		playerHUD.update(tslf);
		if(!player.isAlive()) {
			onPlayerDeath();
		}
		
		//Update the projectiles
		//This loop is running from last element to first element because elements might get deleted;
		for(int i = projectiles.size()-1; i >= 0; i--) {
			Projectile projectile = projectiles.get(i);
			projectile.update(tslf);
			//Checking collision to the map border
			if(projectile.getX() < 0 || projectile.getY() < 0 || projectile.getX() > tiledMap.getFullWidth() || projectile.getY() > tiledMap.getFullHeight()) {
				projectiles.remove(projectile);
				continue;
			}
			//Checking if the projectile is shoot by an enemy
			if(projectile.getOwner() == player) continue;
			if(player.getHitbox().isOverlapping(projectile.getHitbox())) {
				player.getKnockbacked(projectile.getVelocityVector(), Main.player.getMaxKnockbackAmount()/2, Main.player.getMaxKnockbackTime()/2);
				player.getDamaged(50);
				projectiles.remove(i);
			}
		}

		//Update the enemies
		for (Mob zombie : zombies) {
			zombie.update(tslf);

			if(zombie.isAlive()) {
				//Checking collision to the player
				if(zombie.getHitbox().isOverlapping(player.getHitbox())) {
					zombie.getKnockbacked(Vector2D.subtract(zombie.getCenterPosition(), player.getCenterPosition()), zombie.getMaxKnockbackAmount(), zombie.getMaxKnockbackTime());
					zombie.getDamaged(50);
					player.getKnockbacked(Vector2D.subtract(player.getCenterPosition(), zombie.getCenterPosition()), player.getMaxKnockbackAmount(), player.getMaxKnockbackTime());
					player.getDamaged(50);
				}
				//Checking collision the projectiles
				//This loop is running from last element to first element because elements might get deleted;
				for(int i = projectiles.size()-1; i >= 0; i--) {
					Projectile projectile = projectiles.get(i);
					//Check if projectile is shoot by the player
					if(projectile.getOwner() != player) continue;
					if(zombie.getHitbox().isOverlapping(projectile.getHitbox())) {
						zombie.getKnockbacked(projectile.getVelocityVector(), zombie.getMaxKnockbackAmount()/2, zombie.getMaxKnockbackTime()/2);
						zombie.getDamaged(50);
						if(!zombie.isAlive()) player.addExp(50);
						projectiles.remove(i);

					}
				}
			}
		}
		//Y-Sort
		zombies.sort(zombieSort);

		camera.update(tslf);
	}

	@Override
	public void draw(Graphics graphics) {
		graphics.setColor(Color.LIGHT_GRAY);
		graphics.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

		graphics.translate((int)-camera.getX(), (int)-camera.getY());

		//Drawing the map
		tiledMap.drawBottomLayer(graphics);

		//Drawing the projectiles
		for (Projectile projectile : projectiles) {
			if(!camera.isVisibleOnCamera(projectile.getX(), projectile.getY(), projectile.getWidth(), projectile.getHeight())) continue;
			projectile.draw(graphics);
		}

		//Drawing the enemies
		for (Mob zombie : zombies) {
			if(!camera.isVisibleOnCamera(zombie.getX(), zombie.getY(), zombie.getWidth(), zombie.getHeight())) continue;
			zombie.draw(graphics);
		}

		//Drawing the player
		player.draw(graphics);

		//Drawing the Map
		tiledMap.drawTopLayer(graphics);

		graphics.translate((int)camera.getX(), (int)camera.getY());

		//Drawing the player-hud
		playerHUD.draw(graphics);
	}	
}