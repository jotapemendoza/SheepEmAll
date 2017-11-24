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
		setScreen(new SplashScreen(this));  // Splash ScreenTemplate
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
		menuMusic.setLooping(false);
	}

	public void stopMenuMusic(){
		menuMusic.stop();
		menuMusic.setLooping(false);
	}

	public void playLevelOneMusic() {
		levelOneMusic.setVolume(0.8f);
		levelOneMusic.play();
		levelOneMusic.setLooping(true);
	}

	public void pauseLevelOneMusic(){
		levelOneMusic.pause();
		levelOneMusic.setLooping(false);
	}

	public void stopLevelOneMusic(){
		levelOneMusic.stop();
	}

	public void playLevelThreeMusic(){
		levelTwoMusic.setVolume(0.8f);
		levelTwoMusic.play();
		levelTwoMusic.setLooping(true);
	}

	public void pauseLevelThreeMusic(){
		levelTwoMusic.pause();
		levelTwoMusic.setLooping(false);
	}

	public void stopLevelThreeMusic(){
		levelTwoMusic.stop();
	}


	public void playLost(){
		lost.play();
	}

	public void stopLost(){
		lost.stop();
	}


}

