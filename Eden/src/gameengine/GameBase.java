/*
 * 
 */
package gameengine;

import java.awt.Graphics;

import gameengine.graphics.Window;
import gameengine.inputs.KeyboardInputManager;
import gameengine.inputs.MouseInputManager;

/**
 * 
 * @author Felix Kappmeyer & Daniel Lucarz
 *
 */

public abstract class GameBase {
	private Window window;
	
	//-----------------------------------------------ABSTRACT METHODS FOR SUB-CLASS
	public abstract void init();
	public abstract void update(float tslf);
	public abstract void draw(Graphics graphics);
	//-----------------------------------------------END ABSTRACT METHODS
	
	/**
	 * Creates a new window and starts the game loop
	 * @param title The title of the window
	 * @param width The width of the window
	 * @param height The height of the window
	 */
	public void run(String title, int width, int height) {
		window = new Window(title, width, height);
		
		//Adding inputManagers to window
		window.addKeyListener(new KeyboardInputManager());
		window.addMouseListener(new MouseInputManager(window));
		
		init(); //Calling method init() in the sub-class
		
		long lastFrame = System.currentTimeMillis();
		
		while(true) {
			
			//Calculating time since last frame
			long thisFrame = System.currentTimeMillis();
			float tslf = (float)(thisFrame - lastFrame) / 1000f;
			lastFrame = thisFrame;
			
			update(tslf); //Calling method update() in the sub-class 
			
			Graphics g = window.beginDrawing();
			draw(g); //Calling method draw() in the sub-class
			window.endDrawing(g);
		}
	}
}