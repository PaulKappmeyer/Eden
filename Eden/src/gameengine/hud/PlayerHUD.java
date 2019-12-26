package gameengine.hud;

import java.awt.Font;
import java.awt.Graphics;

import gamelogic.player.Player;

public class PlayerHUD {

	private PlayerHealthBar playerHealthBar;
	private PlayerLevelBar playerLevelBar;
	private Font font = new Font("Arial", Font.BOLD, 15);
	
	public PlayerHUD(Player player) {
		this.playerHealthBar = new PlayerHealthBar(player, 10, 10, 150, 25, font);
		this.playerLevelBar = new PlayerLevelBar(player, 10, 45, 150, 25, font);
	}
	
	public void update(float tslf) {
		playerHealthBar.update(tslf);
		playerLevelBar.update(tslf);
	}
	
	public void draw(Graphics graphics) {
		playerHealthBar.draw(graphics);
		playerLevelBar.draw(graphics);
	}
}
