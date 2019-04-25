/*
 * 
 */
package gamelogic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gameengine.GameBase;
import gameengine.loaders.RessourceLoader;

/**
 * 
 * 
 *
 */
public class Main extends GameBase{

	static int width;
	static int height;
	static BufferedImage tileSet;
	
	Player player;
	Tile[][] tiles;
	
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
			tileSet = RessourceLoader.load(BufferedImage.class, ".\\res\\Dungeon_1.png");
		} catch (Exception e) {
			e.printStackTrace();
		}
		int width = 100;
		int height = 100;
		tiles = new Tile[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				tiles[i][j] = new Tile(i * 16, j * 16);
			}
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
		
		int width = tiles.length;
		int height = tiles[0].length;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				tiles[i][j].draw(graphics);
			}
		}
		
		player.draw(graphics);
	}
	
}
