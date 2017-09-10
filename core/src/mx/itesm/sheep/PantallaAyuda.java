package mx.itesm.sheep;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by josepablo on 9/9/17.
 */

class PantallaAyuda extends Pantalla {

    private final Juego juego;
    private Texture bgMenu;
    private Texture backButton;
    private Stage escenaAyuda;

    public PantallaAyuda(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        cargarTexturas();
        crearEscenaAyuda();
        Gdx.input.setInputProcessor(escenaAyuda);
    }

    private void crearEscenaAyuda() {

        escenaAyuda = new Stage(vista);

        //Background

        TextureRegionDrawable trdBg =  new
                TextureRegionDrawable(new TextureRegion(bgMenu));
        Image bg = new Image(trdBg);
        bg.setPosition(0,0);
        escenaAyuda.addActor(bg);

        // Botón jugar
        TextureRegionDrawable trdBack = new
                TextureRegionDrawable(new TextureRegion(backButton));
        ImageButton btnBack = new ImageButton(trdBack);
        btnBack.setPosition(464,874);
        escenaAyuda.addActor(btnBack);

        //Listener botón
        btnBack.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.log("clicked","*********** TOUCH **********");
                juego.setScreen(new PantallaMenu(juego));
            }
        } );
    }

    private void cargarTexturas() {
        bgMenu = new Texture("menuBg.png");
        backButton = new Texture("backButton.png");

    }

    @Override
    public void render(float delta) {
        borrarPantalla(0,0,0);
        batch.setProjectionMatrix(camara.combined);
        escenaAyuda.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
