package mx.itesm.sheep;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
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
    private Texture noMusicButton;
    private Texture pressedNoMusicButton;
    private ImageButton btnMusic;
    private ImageButton btnNoMusic;

    private Music music;
    private Texture fxButtonPr;


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
        musicButton = new Texture("buttons/unpressed/music.png");
        fxButton  =  new Texture("fx.png");
        fxButtonPr  =  new Texture("pressedFx.png");
        backButton = new Texture("buttons/unpressed/backButton.png");
        pressedMusicButton = new Texture("buttons/pressed/pressedMusicButton.png");
        pressedBackButton = new Texture("buttons/pressed/pressedBackButton.png");
        noMusicButton = new Texture("buttons/unpressed/noMusicButton.png");
        pressedNoMusicButton = new Texture("buttons/pressed/pressedNoMusicButton.png");
    }

    private void crearEscenaSettings(){

        escenaSettings = new Stage(vista);


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



        btnNoMusic = new ImageButton(trdNoMusic,trdNoMusicpr);
        btnNoMusic.setPosition(374,631);
        btnMusic =  new ImageButton(trdMusic, trdMusicpr);
        btnMusic.setPosition(374,631);





        btnMusic.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                pref.putBoolean("musicOn",false);

            }
        } );

        btnNoMusic.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                pref.putBoolean("musicOn",true);
            }
        } );


    //-----------------------------------------------------------------

    // FX button ------------------------------------------------------

        TextureRegionDrawable trdFx = new TextureRegionDrawable(new TextureRegion(fxButton));
        TextureRegionDrawable trdFxpr = new TextureRegionDrawable(new TextureRegion(fxButtonPr));
        final ImageButton btnres = new ImageButton(trdFx,trdFxpr);
        btnres.setPosition(374,1167);
        escenaSettings.addActor(btnres);
        btnres.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                pref.putBoolean("played", false);
                pref.flush();
            }
        } );
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

        if(pref.getBoolean("musicOn")){
            btnMusic.setPosition(374,631);
            escenaSettings.addActor(btnMusic);
            btnNoMusic.remove();

        }
        if(!pref.getBoolean("musicOn")){
            btnMusic.setPosition(374,631);
            escenaSettings.addActor(btnNoMusic);
            btnMusic.remove();
        }

        pref.flush();

        if(pref.getBoolean("musicOn")){
            juego.startMenuMusic();
        }
        if(!pref.getBoolean("musicOn")){
            juego.pauseMenuMusic();
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
