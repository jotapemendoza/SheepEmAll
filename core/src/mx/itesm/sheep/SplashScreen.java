package mx.itesm.sheep;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Muestra una pantalla inicial durante cierto tiempo.
 */

class SplashScreen extends ScreenTemplate
{
    private SheepEm sheepEm;
    private float tiempo;   // Tiempo transcurrido
    private Texture logo_itesm;


    public SplashScreen(SheepEm sheepEm) {
        this.sheepEm = sheepEm;
    }
    @Override
    public void show() {
        tiempo = 0;
        logo_itesm = new Texture("logo_itesm.png");
    }

    @Override
    public void render(float delta) {
        borrarPantalla(1, 1, 1);
        // Dibuja
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(logo_itesm,(ANCHO-logo_itesm.getWidth())/2,(ALTO)/2);

        batch.end();
        // Actualiza
        tiempo += Gdx.graphics.getDeltaTime();  // Acumula tiempo
        if (tiempo>=2.5) {
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
