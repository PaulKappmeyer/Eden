/*
 * 
 */
package gamelogic.map;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gamelogic.GameResources;

/**
 * 
 * @author Paul
 *
 */
public class TiledMap {

	private BufferedImage tileSet;
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
	public TiledMap(int width, int height, int tileSize) {
		this.tileSet = GameResources.TILESET;
		this.width = width;
		this.height = height;
		this.tileSize = tileSize;
		this.fullWidth = width * tileSize;
		this.fullHeight = height * tileSize;
		this.tiles = new Tile[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				tiles[i][j] = new Tile(i * tileSize, j * tileSize, tileSize, tileSet);
			}
		}
	}
	
	public void draw(Graphics graphics) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				tiles[i][j].draw(graphics);
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