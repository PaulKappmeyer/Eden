package gameengine.sounds;

public class SoundPlayer {

	private SoundSet soundSet;
	private Sound currentSound;
	
	public SoundPlayer() {
		soundSet = new SoundSet();
	}
	public SoundPlayer(SoundSet soundSet) {
		this.soundSet = soundSet;
	}
	
	public void play(String name) {
		currentSound = soundSet.getSound(name);
		currentSound.play();
	}
	
	public void loop(String name) {
		currentSound = soundSet.getSound(name);
		currentSound.loop();
	}
	
	public void stop() {
		currentSound.stop();
	}
	
	public void addSound(String name, Sound sound) {
		soundSet.addSound(name, sound);
	}
}
