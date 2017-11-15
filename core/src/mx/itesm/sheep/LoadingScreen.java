package mx.itesm.sheep;

import com.badlogic.gdx.Gdx;

/**
 * Muestra una pantalla inicial durante cierto tiempo.
 */

class LoadingScreen extends ScreenTemplate
{
    private Juego juego;
    private float tiempo;   // Tiempo transcurrido


    public LoadingScreen(Juego juego) {
        this.juego = juego;
    }
// Se ejecuta cuando esta pantalla es la principal del juego
    @Override
    public void show() {
        tiempo = 0;
    }

    @Override
    public void render(float delta) {
        borrarPantalla(1, 1, 1);
        // Dibuja
        batch.setProjectionMatrix(camara.combined);
        batch.begin();


        batch.end();
        // Actualiza
        tiempo += Gdx.graphics.getDeltaTime();  // Acumula tiempo
        if (tiempo>=1) {
            juego.setScreen(new MenuScreen(juego));
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
