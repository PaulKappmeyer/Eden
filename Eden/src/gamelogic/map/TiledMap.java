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
	private Tile[][] bottomLayer;
	private Tile[][] topLayer;
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
	public TiledMap(int width, int height, int tileSize, int[][] bottomLayerIds, int[][] topLayerIds) {
		BufferedImage dungeon_tileset = GameResources.DUNGEON_TILESET;
		this.tileSet = new BufferedImage[2000];
		int a = 0;
		for (int y = 0; y < dungeon_tileset.getHeight()/16; y++) {
			for (int x = 0; x < dungeon_tileset.getWidth()/16; x++) {
				tileSet[a] = dungeon_tileset.getSubimage(x * 16, y * 16, 16, 16);
				a++;
			}
		}

		this.width = width;
		this.height = height;
		this.tileSize = tileSize;
		this.fullWidth = width * tileSize;
		this.fullHeight = height * tileSize;
		//Bottom Layer
		this.bottomLayer = new Tile[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int id = bottomLayerIds[i][j];
				bottomLayer[i][j] = new Tile(i * tileSize, j * tileSize, tileSize, tileSize, id, tileSet[id]);
			}
		}
		//Top Layer
		this.topLayer = new Tile[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int id = topLayerIds[i][j];
				if(id == -1) continue;
				topLayer[i][j] = new Tile(i * tileSize, j * tileSize, tileSize, tileSize, id, tileSet[id]);
			}
		}
	}

	public void drawBottomLayer(Graphics graphics) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				Tile tile = bottomLayer[i][j];
				if(!Main.camera.isVisibleOnCamera(tile.getX(), tile.getY(), tile.getWidth(), tile.getHeight())) continue;
				tile.draw(graphics);
			}
		}
	}

	public void drawTopLayer(Graphics graphics) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				Tile tile = topLayer[i][j];
				if(tile == null || !Main.camera.isVisibleOnCamera(tile.getX(), tile.getY(), tile.getWidth(), tile.getHeight()) || tile.getId() == -1) continue;
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
