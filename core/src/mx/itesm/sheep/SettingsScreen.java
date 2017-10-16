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
 * Created by josepablo on 9/14/17.
 */

public class SettingsScreen extends Screen {

    private final Juego juego;
    private Stage escenaSettings;
    private Texture musicButton;
    private Texture fxButton;
    private Texture bg;
    private Texture backButton;

    public SettingsScreen(Juego juego){
        this.juego = juego;
    }
    @Override
    public void show() {
        cargarTexturas();
        crearEscenaSettings();
        Gdx.input.setInputProcessor(escenaSettings);
    }

    private void cargarTexturas() {
        bg = new Texture("sBg.png");
        musicButton = new Texture("music.png");
        fxButton  =  new Texture("fx.png");
        backButton = new Texture("backButtonr.png");
    }

    private void crearEscenaSettings(){

        escenaSettings = new Stage(vista);

        TextureRegionDrawable trdBg =  new
                TextureRegionDrawable(new TextureRegion(bg));
        Image bg = new Image(trdBg);
        bg.setPosition(0,0);
        escenaSettings.addActor(bg);



        TextureRegionDrawable trdMusic = new
                TextureRegionDrawable(new TextureRegion(musicButton));
        final ImageButton btnMusic = new ImageButton(trdMusic);
        btnMusic.setPosition(374,631);
        escenaSettings.addActor(btnMusic);

        TextureRegionDrawable trdFx = new
                TextureRegionDrawable(new TextureRegion(fxButton));
        final ImageButton btnFx = new ImageButton(trdFx);
        btnFx.setPosition(374,1167);
        escenaSettings.addActor(btnFx);




        /****Boton de regreso****/
        TextureRegionDrawable trdBack = new
                TextureRegionDrawable(new TextureRegion(backButton));
        final ImageButton btnBack = new ImageButton(trdBack);
        btnBack.setPosition(461,120);
        escenaSettings.addActor(btnBack);

        //Listener botón

        btnBack.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                juego.setScreen(new MenuScreen(juego));
            }
        } );


    }

    @Override
    public void render(float delta) {
        borrarPantalla(0,0,0);
        batch.setProjectionMatrix(camara.combined);
        escenaSettings.draw();
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
