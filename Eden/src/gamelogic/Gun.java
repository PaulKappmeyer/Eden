package gamelogic;

import java.awt.Graphics;
import java.util.LinkedList;

import gameengine.maths.Vector2D;

public class Gun {
	
	public static final int INFINITE_AMMO = -1;
	
	private int maxAmmo;
	private int currentAmmo;
	
	private float shootCooldown;
	private float reloadTime;
	private boolean canShoot;
	private LinkedList<Projectile> projectiles;
	private float currentTime;
	
	/**
	 * Initializes a gun with infinite ammo and a specified shoot-cooldown.
	 * @param shootCooldown
	 */
	public Gun(float shootCooldown) {
		this.maxAmmo = INFINITE_AMMO;
		this.currentAmmo = maxAmmo;
		this.shootCooldown = shootCooldown;
		this.reloadTime = 0;
		this.projectiles = new LinkedList<>();
	}
	
	/**
	 * Initializes a gun with a specified maximum ammo, a reload-time and a shoot-cooldown.
	 * @param maxAmmo
	 * @param shootCooldown
	 * @param reloadTime
	 */
	public Gun(int maxAmmo, float shootCooldown, float reloadTime) {
		this.maxAmmo = maxAmmo;
		this.currentAmmo = maxAmmo;
		this.shootCooldown = shootCooldown;
		this.reloadTime = reloadTime;
		this.projectiles = new LinkedList<>();
	}
	
	public void update(float tslf) {
		//Update the projectiles
		for (Projectile projectile : projectiles) {
			projectile.update(tslf);
		}
		
		if(canShoot) return;
		//Manage shoot-cooldown
		if(currentAmmo > 0) {
			currentTime += tslf;
			if(currentTime >= shootCooldown) {
				canShoot = true;
				currentTime = 0;
			}
		}
		//Manage reload
		else {
			currentTime += tslf;
			if(currentTime >= reloadTime) {
				currentAmmo = maxAmmo;
				currentTime = 0;
			}
		}
	}
	
	public void draw(Graphics graphics) {
		for (Projectile projectile : projectiles) {
			projectile.draw(graphics);
		}
	}
	
	public void shoot(Vector2D position, Vector2D velocityVector) {
		if(canShoot) {
			Projectile projectile = new Projectile(position.x, position.y, velocityVector.x, velocityVector.y);
			projectiles.add(projectile);
			currentAmmo--;
			canShoot = false;
		}
	}
	
	//--------------------------Getters
	public LinkedList<Projectile> getProjectiles() {
		return projectiles;
	}
	
	public boolean canShoot() {
		return canShoot;
	}
	
	public int getCurrentAmmo() {
		return currentAmmo;
	}

	public int getMaxAmmo() {
		return maxAmmo;
	}
}
