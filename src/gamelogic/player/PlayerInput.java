/*
 * 
 */
package gamelogic.player;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import gameengine.inputs.KeyboardInputManager;
import gameengine.inputs.MouseInputManager;
import gameengine.maths.Vector2D;

/**
 * 
 * @author Paul
 *
 */
public final class PlayerInput {

	public static Vector2D getMousePosition() {
		return new Vector2D(getMouseX(), getMouseY());
	}
	
	public static float getMouseX() {
		return MouseInputManager.getMouseX();
	}
	
	public static float getMouseY() {
		return MouseInputManager.getMouseY();
	}
	
	public static boolean isLeftMouseButtonDown() {
		return MouseInputManager.isButtonDown(MouseEvent.BUTTON1);
	}
	
	/**
	 * 
	 * @return true if the walk-up-key is down
	 */
	public static boolean isUpKeyDown() {
		return KeyboardInputManager.isKeyDown(KeyEvent.VK_W) || KeyboardInputManager.isKeyDown(KeyEvent.VK_UP);
	}
	/**
	 * 
	 * @return true if the walk-down-key is down
	 */
	public static boolean isDownKeyDown() {
		return KeyboardInputManager.isKeyDown(KeyEvent.VK_S) || KeyboardInputManager.isKeyDown(KeyEvent.VK_DOWN);
	}
	/**
	 * 
	 * @return true if the walk-left-key is down
	 */
	public static boolean isLeftKeyDown() {
		return KeyboardInputManager.isKeyDown(KeyEvent.VK_A) || KeyboardInputManager.isKeyDown(KeyEvent.VK_LEFT);
	}
	/**
	 * 
	 * @return true if the walk-right-key is down
	 */
	public static boolean isRightKeyDown() {
		return KeyboardInputManager.isKeyDown(KeyEvent.VK_D) || KeyboardInputManager.isKeyDown(KeyEvent.VK_RIGHT);
	}
}
