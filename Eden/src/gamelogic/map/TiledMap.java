/*
 * 
 */
package gamelogic.map;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gamelogic.GameResources;
import gamelogic.Main;

/**
 * 
 * @author Paul
 *
 */
public class TiledMap {

	private BufferedImage[] tileSet;
	private Tile[][] tiles;
	private int width;	/*The width of the tiled map*/
	private int height;	/*The height of the tiled map*/
	private int tileSize;	/*The size of each tile*/
	private int fullWidth;
	private int fullHeight;
	
	/**
	 * 
	 * @param width The width of the tiled map; number of tiles in x-direction
	 * @param height The height of the tiled map; number of tiles in y-direction
	 * @param tileSize The size of each tile in pixels
	 * @throws Exception
	 */
	public TiledMap(int width, int height, int tileSize, int[][] ids) {
		this.tileSet = GameResources.TILESET;
		this.width = width;
		this.height = height;
		this.tileSize = tileSize;
		this.fullWidth = width * tileSize;
		this.fullHeight = height * tileSize;
		this.tiles = new Tile[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int id = ids[i][j];
				BufferedImage tileImage = tileSet[2];
				if(id == 130) tileImage = tileSet[1];
				else if(id == 115) tileImage = tileSet[2];
				tiles[i][j] = new Tile(i * tileSize, j * tileSize, tileSize, tileSize, ids[i][j], tileImage);
			}
		}
	}
	
	public void draw(Graphics graphics) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				Tile tile = tiles[i][j];
				if(!Main.isVisibleOnScreen(tile.getX(), tile.getY(), tile.getWidth(), tile.getHeight())) continue;
				tile.draw(graphics);
			}
		}
	}
	
	public int getFullWidth() {
		return fullWidth;
	}
	
	public int getFullHeight() {
		return fullHeight;
	}
	
	public int getTileSize() {
		return tileSize;
	}
}
