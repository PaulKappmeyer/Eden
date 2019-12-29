/*
 * 
 */
package gamelogic.player;

import java.util.LinkedList;

import gameengine.Mob;
import gameengine.graphics.AnimationPlayer;
import gameengine.hitbox.CircleHitbox;
import gameengine.maths.Vector2D;
import gamelogic.Direction;
import gamelogic.GameResources;
import gamelogic.Main;
import gamelogic.Projectile;

/**
 * 
 * @author Paul
 *
 */
public class Player extends Mob{

	private float SHOOT_COOLDOWN = 0.5f;
	private int MAX_AMMO = 10;
	private float RELOAD_TIME = 1f;

	public LinkedList<Projectile> projectiles;
	private boolean canShoot = true;
	private float currentShootCooldown = 0;
	private int currentAmmo = MAX_AMMO;
	private float currentReloadTime = 0;

	private int level = 1;
	private int exp = 0;
	private int MAX_EXP[] = new int[] {100, 110, 130, 160, 200, 250, 300, 500, 1000, 2000, 5000, 10000, 20000, 30000, 40000, 50000};

	public Player(float x, float y) {
		super(x, y, 128, 128, 400, 400, 0.1f, 1000, 0.35f);
		this.animationPlayer = new AnimationPlayer(GameResources.PLAYER_ANIMATION_SET, GameResources.PLAYER_ANIMATION_SET.getAnimation("player_stand_" + walkDirectionString));
		this.hitbox = new CircleHitbox(centerPosition, 35);
		this.projectiles = new LinkedList<Projectile>();
	}

	public void addExp(int ammount) {
		exp += ammount;
	}

	@Override
	public void update(float tslf) {
		super.update(tslf);

		if(exp >= MAX_EXP[level-1]) {
			exp -= MAX_EXP[level-1];
			if(level < MAX_EXP.length-1) level++;
			else {
				level = MAX_EXP.length;
				exp = MAX_EXP[level-1];
			}
		}

		//Shooting
		if(canShoot) {
			if(PlayerInput.isLeftMouseButtonDown()) {
				Vector2D mousePosition = PlayerInput.getMousePosition();
				Vector2D velocityVector = new Vector2D(mousePosition.x - centerPosition.x + Main.camera.getX(), mousePosition.y - centerPosition.y + Main.camera.getY());
				Projectile projectile = new Projectile(getCenterPositionX(), getCenterPositionY(), velocityVector.x, velocityVector.y);
				projectiles.add(projectile);
				currentAmmo--;
				stopWalking();
				getKnockbacked(new Vector2D(-velocityVector.x, -velocityVector.y), getMAX_KNOCKBACK_AMOUNT()/2, getMAX_KNOCKBACK_TIME()/2);
				canShoot = false;
			}
		}else {
			if(currentAmmo > 0) {
				currentShootCooldown += tslf;
				if(currentShootCooldown >= SHOOT_COOLDOWN) {
					canShoot = true;
					currentShootCooldown = 0;
				}
			}else {
				currentReloadTime += tslf;
				if(currentReloadTime >= RELOAD_TIME) {
					currentAmmo = MAX_AMMO;
					currentReloadTime = 0;
				}
			}
		}
		//Walking
		if(!gotDamaged) {
			checkInput();

			if(isWalking) {
				animationPlayer.loop("player_walk_" + walkDirectionString);
			}
		}
	}

	public void checkInput() {
		boolean isPressing = false;
		walkDirectionVector.x = 0;
		walkDirectionVector.y = 0;
		if(PlayerInput.isUpKeyDown() && !(PlayerInput.isLeftKeyDown() || PlayerInput.isRightKeyDown())) {
			isPressing = true;
			walkDirectionVector.y = -1;
			walkDirectionString = Direction.up;
		}
		if(PlayerInput.isUpKeyDown() && PlayerInput.isRightKeyDown()) {
			isPressing = true;
			walkDirectionVector.y = -0.7f;
			walkDirectionVector.x = 0.7f;
		}
		if(PlayerInput.isUpKeyDown() && PlayerInput.isLeftKeyDown()) {
			isPressing = true;
			walkDirectionVector.y = -0.7f;
			walkDirectionVector.x = -0.7f;
		}
		if(PlayerInput.isDownKeyDown() && !(PlayerInput.isLeftKeyDown() || PlayerInput.isRightKeyDown())) {
			isPressing = true;
			walkDirectionVector.y = 1;
			walkDirectionString = Direction.down;
		}
		if(PlayerInput.isDownKeyDown() && PlayerInput.isRightKeyDown()) {
			isPressing = true;
			walkDirectionVector.y = 0.7f;
			walkDirectionVector.x = 0.7f;
		}
		if(PlayerInput.isDownKeyDown() && PlayerInput.isLeftKeyDown()) {
			isPressing = true;
			walkDirectionVector.y = 0.7f;
			walkDirectionVector.x = -0.7f;
		}
		if(PlayerInput.isLeftKeyDown() && !(PlayerInput.isUpKeyDown() || PlayerInput.isDownKeyDown())) {
			isPressing = true;
			walkDirectionVector.x = -1;
			walkDirectionString = Direction.left;
		}
		if(PlayerInput.isRightKeyDown() && !(PlayerInput.isUpKeyDown() || PlayerInput.isDownKeyDown())) {
			isPressing = true;
			walkDirectionVector.x = 1;
			walkDirectionString = Direction.right;
		}
		if(isPressing) {
			isWalking = true;
		} else 	if(walkDirectionVector.x == 0 && walkDirectionVector.y == 0) {
			stopWalking();
		}
	}

	//------------------------Damaged
	@Override
	public void getDamaged(float damageAmount) {
		if(!gotDamaged) {
			super.getDamaged(damageAmount);
			animationPlayer.loop("player_getDamaged_" + walkDirectionString);
		}
	}

	//--------------------------Getters
	public int getLevel() {
		return level;
	}

	public int getExp() {
		return exp;
	}

	public int[] getMAX_EXP() {
		return MAX_EXP;
	}
	
	public int getCurrentAmmo() {
		return currentAmmo;
	}
	
	public int getMAX_AMMO() {
		return MAX_AMMO;
	}
}
