package mx.itesm.sheep;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Muestra una pantalla inicial durante cierto tiempo.
 */

class PantallaCargando extends Pantalla
{
    private Juego juego;
    private float tiempo;   // Tiempo transcurrido
    private Texture texturaReloj;   // Imagen que se muestra


    private Texture bgMenu;



    public PantallaCargando(Juego juego) {
        this.juego = juego;
    }
// Se ejecuta cuando esta pantalla es la principal del juego
    @Override
    public void show() {
        tiempo = 0;
        texturaReloj = new Texture("reloj.png");
        bgMenu = new Texture("menuBg.png");
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
        if (tiempo>=6) {
            juego.setScreen(new PantallaMenu(juego));
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
