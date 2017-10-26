package mx.itesm.sheep;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class Juego extends Game
{

	private Music music;

	@Override
	public void create() {
		setScreen(new LoadingScreen(this));  // Splash MainScreen
		music = Gdx.audio.newMusic(Gdx.files.internal("mainMenu_song.mp3"));
	}

	public void startMusic(){
		music.play();
		music.setLooping(true);
	}

	public void pauseMusic(){
		music.pause();
	}
}

