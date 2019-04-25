package gameengine.sounds;

public class SoundPlayer {

	private SoundSet sounds;
	private Sound currentSound;
	
	public SoundPlayer(SoundSet sounds) {
		this.sounds = sounds;
	}
	
	public void play(String name) {
		currentSound = sounds.getSound(name);
		currentSound.play();
	}
	
	public void loop(String name) {
		currentSound = sounds.getSound(name);
		currentSound.loop();
	}
	
	public void stop() {
		currentSound.stop();
	}
}
