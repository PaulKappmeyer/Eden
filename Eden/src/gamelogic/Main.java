/*
 * 
 */
package gamelogic;

import java.awt.Graphics;

import gameengine.GameBase;

/**
 * 
 * 
 *
 */
public class Main extends GameBase{

	Player player;
	
	public static void main(String[] args) {
		Main main = new Main();
		main.start("Eden", 1280, 860);
	}	

	@Override
	public void init() {
		player = new Player(400, 400);
	}

	@Override
	public void update(float tslf) {
		player.update(tslf);
	}

	@Override
	public void draw(Graphics graphics) {
		player.draw(graphics);
	}
	
}
