/*
 * 
 */
package gamelogic.player;

import java.awt.Graphics;
import java.util.LinkedList;

import gameengine.Mob;
import gameengine.graphics.AnimationPlayer;
import gameengine.hitbox.CircleHitbox;
import gameengine.hud.PlayerHealthBar;
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

	public static final float SHOOT_COOLDOWN = 0.5f;

	private PlayerHealthBar playerHealthBar;

	public LinkedList<Projectile> projectiles;
	private boolean canShoot = true;
	private float currentShootCooldown;

	public Player(float x, float y) {
		super(x, y, 128, 128, 400, 400, 0.1f, 1000, 0.35f);
		this.animationPlayer = new AnimationPlayer(GameResources.PLAYER_ANIMATION_SET, GameResources.PLAYER_ANIMATION_SET.getAnimation("player_stand_" + walkDirectionString));
		this.hitbox = new CircleHitbox(centerPosition, 35);
		this.playerHealthBar = new PlayerHealthBar(this);
		this.projectiles = new LinkedList<Projectile>();
	}

	@Override
	public void draw(Graphics graphics) {
		for (Projectile projectile : projectiles) {
			projectile.draw(graphics);
		}
		super.draw(graphics);
		playerHealthBar.draw(graphics);
	}

	@Override
	public void update(float tslf) {
		super.update(tslf);
		playerHealthBar.update(tslf);

		//Shooting
		if(canShoot) {
			if(PlayerInput.isLeftMouseButtonDown()) {
				Vector2D mousePosition = PlayerInput.getMousePosition();
				Vector2D velocityVector = new Vector2D(mousePosition.x - centerPosition.x - Main.translateX, mousePosition.y - centerPosition.y - Main.translateY);
				Projectile projectile = new Projectile(getCenterPositionX(), getCenterPositionY(), velocityVector.x, velocityVector.y);
				projectiles.add(projectile);
				stopWalking();
				getKnockbacked(new Vector2D(-velocityVector.x, -velocityVector.y), MAX_KNOCKBACK_AMOUNT/2, SHOOT_COOLDOWN/2);
				canShoot = false;
			}
		}else {
			currentShootCooldown += tslf;
			if(currentShootCooldown >= SHOOT_COOLDOWN) {
				canShoot = true;
				currentShootCooldown = 0;
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
}
