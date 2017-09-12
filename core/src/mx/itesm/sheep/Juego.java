package mx.itesm.sheep;

import com.badlogic.gdx.Game;
// Estoy editando :) x2
public class Juego extends Game
{
	@Override
	public void create() {
		setScreen(new PantallaCargando(this));  // Splash Screen
	}
}

