package mx.itesm.sheep;

import com.badlogic.gdx.Gdx;

/**
 * Muestra una pantalla inicial durante cierto tiempo.
 */

class LoadingScreen extends ScreenTemplate
{
    private SheepEm sheepEm;
    private float tiempo;   // Tiempo transcurrido


    public LoadingScreen(SheepEm sheepEm) {
        this.sheepEm = sheepEm;
    }
// Se ejecuta cuando esta pantalla es la principal del sheepEm
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
            sheepEm.setScreen(new MenuScreen(sheepEm));
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
