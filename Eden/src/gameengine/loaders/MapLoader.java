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
		
		if(!mapFile.exists()) throw new Exception("Map " + filePath + " not found.");
		
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
		int[][] ids = new int[width][height];
		for (int y = 0; y < height; y++) {
			String line = bufferedReader.readLine();
			String[] data = line.split(",");
			for (int x = 0; x < width; x++) {
				ids[x][y] = Integer.parseInt(data[x]);
			}
		}
		
		bufferedReader.close();
		
		TiledMap map = new TiledMap(width, height, tileWidth, ids);
		return map;
	}
}
