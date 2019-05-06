/*
 * 
 */
package gamelogic;

import java.awt.Color;
import java.awt.Graphics;

import gameengine.GameBase;

/**
 * 
 * 
 *
 */
public class Main extends GameBase{

	static int width;
	static int height;
	
	private Player player;
	private TiledMap tiledMap;
	
	public static void main(String[] args) {
		Main main = new Main();
		Main.width = 1280;
		Main.height = 860;
		main.start("Eden", width, height);
	}	

	@Override
	public void init() {
		player = new Player(400, 400);
		try {
			tiledMap = new TiledMap(100, 100, 128);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update(float tslf) {
		player.update(tslf);
	}

	@Override
	public void draw(Graphics graphics) {
		graphics.setColor(Color.LIGHT_GRAY);
		graphics.fillRect(0, 0, width, height);
		
		tiledMap.draw(graphics);
		player.draw(graphics);
	}	
}