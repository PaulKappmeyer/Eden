package gameengine.sounds;

public class SoundPlayer {

	private SoundSet soundSet;
	private Sound currentSound;
	private boolean isPlaying = false;

	public SoundPlayer() {
		this.soundSet = new SoundSet();
	}
	public SoundPlayer(SoundSet soundSet) {
		this.soundSet = soundSet;
	}

	public void play(String name) {
		if(!isPlaying) {
			currentSound = soundSet.getSound(name);
			currentSound.play();
			isPlaying = true;
		}
	}

	public void loop(String name) {
		if(!isPlaying) {
			currentSound = soundSet.getSound(name);
			currentSound.loop();
			isPlaying = true;
		}
	}

	public void stop() {
		if(isPlaying) {
			currentSound.stop();
			isPlaying = false;
		}
	}

	public void setVolume(float volume) {
		if(currentSound == null) return;
		currentSound.setVolume(volume);
	}
	
	public void addSound(String name, Sound sound) {
		soundSet.addSound(name, sound);
	}
}
