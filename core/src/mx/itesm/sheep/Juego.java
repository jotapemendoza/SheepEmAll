package mx.itesm.sheep;

import com.badlogic.gdx.Game;

public class Juego extends Game
{
	@Override
	public void create() {
		setScreen(new LoadingScreen(this));  // Splash MainScreen
	}
}

