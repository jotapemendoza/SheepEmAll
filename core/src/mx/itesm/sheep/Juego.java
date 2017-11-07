package mx.itesm.sheep;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class Juego extends Game
{

	private Music menuMusic;
	private Music gameMusic;
	private Music lost;

	@Override
	public void create() {
		setScreen(new LoadingScreen(this));  // Splash MainScreen
		menuMusic = Gdx.audio.newMusic(Gdx.files.internal("music/menuMusic.mp3"));
		gameMusic = Gdx.audio.newMusic(Gdx.files.internal("music/gameMusic.mp3"));
		lost = Gdx.audio.newMusic(Gdx.files.internal("music/lost.mp3"));

	}

	public void startMenuMusic(){
		menuMusic.play();
		menuMusic.setLooping(true);
	}

	public void pauseMenuMusic(){
		menuMusic.pause();
		gameMusic.setLooping(false);
	}

	public void playGameMusic() {
		gameMusic.play();
		gameMusic.setLooping(true);
	}

	public void pauseGameMusic(){
		gameMusic.pause();
		gameMusic.setLooping(false);
	}

	public void stopGameMusic(){
		gameMusic.stop();
		gameMusic.dispose();
	}

	public void playLost(){
		lost.play();
	}
}

