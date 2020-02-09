package gamelogic;

import gameengine.Mob;
import gameengine.maths.Vector2D;

public class Gun {
	
	public static final int INFINITE_AMMO = -1;
	
	private Mob owner;
	
	private int maxAmmo;
	private int currentAmmo;
	
	private float shootCooldown;
	private float reloadTime;
	private boolean canShoot;
	private float currentTime;
	
	/**
	 * Initializes a gun with infinite ammo and a specified shoot-cooldown.
	 * @param shootCooldown
	 */
	public Gun(Mob owner, float shootCooldown) {
		this.owner = owner;
		this.maxAmmo = INFINITE_AMMO;
		this.currentAmmo = maxAmmo;
		this.shootCooldown = shootCooldown;
		this.reloadTime = 0;
	}
	
	/**
	 * Initializes a gun with a specified maximum ammo, a reload-time and a shoot-cooldown.
	 * @param maxAmmo
	 * @param shootCooldown
	 * @param reloadTime
	 */
	public Gun(Mob owner, int maxAmmo, float shootCooldown, float reloadTime) {
		this.owner = owner;
		this.maxAmmo = maxAmmo;
		this.currentAmmo = maxAmmo;
		this.shootCooldown = shootCooldown;
		this.reloadTime = reloadTime;
	}
	
	public void update(float tslf) {
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
	
	public void shoot(Vector2D position, Vector2D velocityVector) {
		if(canShoot) {
			Projectile projectile = new Projectile(owner, position.x, position.y, velocityVector.x, velocityVector.y);
			Main.projectiles.add(projectile);
			currentAmmo--;
			canShoot = false;
		}
	}
	
	//--------------------------Getters
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
