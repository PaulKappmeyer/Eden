/*
 * 
 */
package gamelogic.player;

import java.awt.event.KeyEvent;

import gameengine.inputs.KeyboardInputManager;

/**
 * 
 * @author Paul
 *
 */
public final class PlayerInput {

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
