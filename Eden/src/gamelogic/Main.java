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

	public static final MyRandom RANDOM = new MyRandom();

	public static Player player;
	private PlayerHUD playerHUD;
	public static TiledMap tiledMap;
	private LinkedList<Mob> zombies;
	private ZombieSort zombieSort;
	public static  Camera camera;
	
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
		zombies = new LinkedList<Mob>();
		for (int i = 0; i < 10; i++) {
			Vector2D position = RANDOM.nextVector2D(750, 200, 3500, 3500);
			zombies.add(new ShootingZombie(position.x, position.y));
		}
		zombieSort = new ZombieSort();
		camera = new Camera();
		camera.setFocusedObject(player);
	}

	@Override
	public void update(float tslf) {
		player.update(tslf);
		playerHUD.update(tslf);

		for (Mob zombie : zombies) {
			zombie.update(tslf);
			if(zombie.isAlive()) {
				if(zombie.getHitbox().isOverlapping(player.getHitbox())) {
					zombie.getKnockbacked(Vector2D.subtract(zombie.getCenterPosition(), player.getCenterPosition()), zombie.getMaxKnockbackAmount(), zombie.getMaxKnockbackTime());
					zombie.getDamaged(50);
					player.getDamaged(50);
					player.getKnockbacked(Vector2D.subtract(player.getCenterPosition(), zombie.getCenterPosition()), player.getMaxKnockbackAmount(), player.getMaxKnockbackTime());
				}else {
					//This loop is running from last element to first element because elements get deleted;
					LinkedList<Projectile> projectiles = player.getGun().getProjectiles();
					for(int i = projectiles.size()-1; i >= 0; i--) {
						Projectile projectile = player.getGun().getProjectiles().get(i);
						if(projectile.getX() < 0 || projectile.getY() < 0) {
							projectiles.remove(i);
							continue;
						}
						else if(projectile.getX() > tiledMap.getFullWidth() || projectile.getY() > tiledMap.getFullHeight()) {
							projectiles.remove(i);
							continue;
						}

						if(zombie.getHitbox().isOverlapping(projectile.getHitbox())) {
							zombie.getKnockbacked(projectile.getVelocityVector(), zombie.getMaxKnockbackAmount()/2, zombie.getMaxKnockbackTime()/2);
							zombie.getDamaged(50);
							if(!zombie.isAlive()) player.addExp(50);
							projectiles.remove(i);
						}
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

		tiledMap.drawBottomLayer(graphics);

		for (Mob zombie : zombies) {
			if(zombie instanceof Zombie) {
				if(!camera.isVisibleOnCamera(zombie.getX(), zombie.getY(), zombie.getWidth(), zombie.getHeight())) continue;
				zombie.draw(graphics);
				zombie.getHitbox().draw(graphics);
			}else if(zombie instanceof ShootingZombie) {
				for (Projectile projectile : ((ShootingZombie)zombie).projectiles) {
					projectile.draw(graphics);
				}
				if(!camera.isVisibleOnCamera(zombie.getX(), zombie.getY(), zombie.getWidth(), zombie.getHeight())) continue;
				zombie.draw(graphics);
			}
		}

		//Player
		player.draw(graphics);
		player.getHitbox().draw(graphics);

		tiledMap.drawTopLayer(graphics);
		
		graphics.translate((int)camera.getX(), (int)camera.getY());

		playerHUD.draw(graphics);
	}	
}