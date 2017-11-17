package mx.itesm.sheep;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class SheepEm extends Game
{

	private Music menuMusic;
	private Music levelOneMusic;
	private Music levelTwoMusic;
	private Music lost;

	@Override
	public void create() {
		setScreen(new LoadingScreen(this));  // Splash ScreenTemplate
		menuMusic = Gdx.audio.newMusic(Gdx.files.internal("music/menuMusic.mp3"));
		levelOneMusic = Gdx.audio.newMusic(Gdx.files.internal("music/gameMusic.mp3"));
		lost = Gdx.audio.newMusic(Gdx.files.internal("music/lost.mp3"));
		levelTwoMusic = Gdx.audio.newMusic(Gdx.files.internal("music/level2.mp3"));

	}

	public void startMenuMusic(){
		menuMusic.setVolume(0.8f);
		menuMusic.play();
		menuMusic.setLooping(true);
	}

	public void pauseMenuMusic(){
		menuMusic.pause();
		levelOneMusic.setLooping(false);
	}

	public void playGameMusic() {
		levelOneMusic.setVolume(0.8f);
		levelOneMusic.play();
		levelOneMusic.setLooping(true);
	}

	public void pauseGameMusic(){
		levelOneMusic.pause();
		levelOneMusic.setLooping(false);
	}

	public void playLevelTwoMusic(){
		levelTwoMusic.setVolume(0.8f);
		levelTwoMusic.play();
		levelTwoMusic.setLooping(true);
	}

	public void stopLevelTwoMusic(){
		levelTwoMusic.stop();
	}

	public void stopGameMusic(){
		levelOneMusic.stop();
	}

	public void playLost(){
		lost.play();
	}

	public void stopLost(){
		lost.stop();
	}
}

