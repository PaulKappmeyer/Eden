/*
 * 
 */
package gamelogic;

import java.awt.Color;
import java.awt.Graphics;

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
	private Zombie testZombie[];
	
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
		testZombie = new Zombie[15];
		for (int i = 0; i < testZombie.length; i++) {
			Vector2D position = RANDOM.nextVector2D(500, 500, 1500, 1500);
			testZombie[i] = new Zombie(position.x, position.y);
		}
	}

	@Override
	public void update(float tslf) {
		player.update(tslf);
		for (int i = 0; i < testZombie.length; i++) {
			testZombie[i].update(tslf);
		}
	}

	@Override
	public void draw(Graphics graphics) {
		graphics.setColor(Color.LIGHT_GRAY);
		graphics.fillRect(0, 0, width, height);
		
		graphics.translate((int)-player.getX() + width/2 - player.getWidth()/2, (int)-player.getY()  + height/2 - player.getHeight()/2);
		
		tiledMap.draw(graphics);
		player.draw(graphics);
		for (int i = 0; i < testZombie.length; i++) {
			testZombie[i].draw(graphics);
		}
	}	
}