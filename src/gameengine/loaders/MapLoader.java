package gameengine.loaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import gamelogic.map.TiledMap;

/**
 * 
 * @author Paul
 *
 */
public class MapLoader {

	static TiledMap loadMap (String filePath) throws Exception{
		File mapFile = new File(filePath);
		
		if (!mapFile.exists()) {
			throw new Exception("Map " + filePath + " not found: " + mapFile.getAbsolutePath());
		}
		
		FileReader fileReader = new FileReader(mapFile);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		bufferedReader.readLine(); //[header]
		int width = Integer.parseInt(bufferedReader.readLine().split("=")[1]);
		int height = Integer.parseInt(bufferedReader.readLine().split("=")[1]);
		int tileWidth = Integer.parseInt(bufferedReader.readLine().split("=")[1]);
		@SuppressWarnings("unused")
		int tileHeight = Integer.parseInt(bufferedReader.readLine().split("=")[1]);
		bufferedReader.readLine(); //orientation
		bufferedReader.readLine(); //background_color
		bufferedReader.readLine();
		bufferedReader.readLine(); //[tilesets]
		bufferedReader.readLine(); //[tileset]
		bufferedReader.readLine(); 
		
		bufferedReader.readLine(); //[layer]
		bufferedReader.readLine(); //type = Ground
		bufferedReader.readLine(); //data
		int[][] bottomLayer = new int[width][height];
		for (int y = 0; y < height; y++) {
			String line = bufferedReader.readLine();
			String[] data = line.split(",");
			for (int x = 0; x < width; x++) {
				bottomLayer[x][y] = Integer.parseInt(data[x])-1;
			}
		}
		
		bufferedReader.readLine(); //
		bufferedReader.readLine(); //[layer]
		bufferedReader.readLine(); //type = Top
		bufferedReader.readLine(); //data
		int[][] topLayer = new int[width][height];
		for (int y = 0; y < height; y++) {
			String line = bufferedReader.readLine();
			String[] data = line.split(",");
			for (int x = 0; x < width; x++) {
				topLayer[x][y] = Integer.parseInt(data[x])-1;
			}
		}
		
		bufferedReader.close();
		
		TiledMap map = new TiledMap(width, height, tileWidth, bottomLayer, topLayer);
		return map;
	}
}
