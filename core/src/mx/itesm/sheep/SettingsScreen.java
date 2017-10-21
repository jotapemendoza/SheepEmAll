package mx.itesm.sheep;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
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

public class SettingsScreen extends MainScreen {

    private final Juego juego;
    private Stage escenaSettings;
    private Texture musicButton;
    private Texture fxButton;
    private Texture bg;
    private Texture backButton;
    private Texture pressedMusicButton;
    private Texture pressedBackButton;
    private Preferences prefs;
    private Texture noMusicButton;
    private Texture pressedNoMusicButton;


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
        backButton = new Texture("backButton.png");
        pressedMusicButton = new Texture("pressedMusicButton.png");
        pressedBackButton = new Texture("pressedBackButton.png");
        noMusicButton = new Texture("noMusicButton.png");
        pressedNoMusicButton = new Texture("pressedNoMusicButton.png");
    }

    private void crearEscenaSettings(){

        escenaSettings = new Stage(vista);

        prefs = Gdx.app.getPreferences("My Preferences");




        TextureRegionDrawable trdBg =  new
                TextureRegionDrawable(new TextureRegion(bg));
        Image bg = new Image(trdBg);
        bg.setPosition(0,0);
        escenaSettings.addActor(bg);



    // Music button ---------------------------------------------------


        TextureRegionDrawable trdMusic = new
                TextureRegionDrawable(new TextureRegion(musicButton));
        TextureRegionDrawable trdMusicpr = new
                TextureRegionDrawable(new TextureRegion(pressedMusicButton));



        TextureRegionDrawable trdNoMusic = new
                TextureRegionDrawable(new TextureRegion(noMusicButton));
        TextureRegionDrawable trdNoMusicpr = new
                TextureRegionDrawable(new TextureRegion(pressedNoMusicButton));


        ImageButton btnNoMusic = new ImageButton(trdNoMusic,trdNoMusicpr);
        btnNoMusic.setPosition(374,631);
        ImageButton btnMusic =  new ImageButton(trdMusic, trdMusicpr);
        btnMusic.setPosition(374,631);

        if(prefs.getBoolean("musicOn")){
            escenaSettings.addActor(btnMusic);
        }else{
            escenaSettings.addActor(btnNoMusic);
        }


        btnMusic.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                prefs.putBoolean("musicOn",!prefs.getBoolean("musicOn"));

            }
        } );

        btnNoMusic.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                prefs.putBoolean("musicOn",!prefs.getBoolean("musicOn"));
            }
        } );


    //-----------------------------------------------------------------

    // FX button ------------------------------------------------------

        TextureRegionDrawable trdFx = new
                TextureRegionDrawable(new TextureRegion(fxButton));
        final ImageButton btnFx = new ImageButton(trdFx);
        btnFx.setPosition(374,1167);
        escenaSettings.addActor(btnFx);
    //-----------------------------------------------------------------



        TextureRegionDrawable trdBack = new
                TextureRegionDrawable(new TextureRegion(backButton));
        TextureRegionDrawable trdBackpr = new
                TextureRegionDrawable(new TextureRegion(pressedBackButton));
        final ImageButton btnBack = new ImageButton(trdBack, trdBackpr);
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
