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

public class SettingsScreen extends ScreenTemplate {

    private final Juego juego;
    private Stage escenaSettings;
    private Texture musicButton,
            backButton,
            noMusicButton,
            restartButton,
            pressedRestart,
            pressedMusicButton,
            pressedBackButton,
            pressedNoMusicButton,
            background;

    private ImageButton btnMusic,
            btnNoMusic;

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
        background = new Texture("sBg.png");
        musicButton = new Texture("Buttons/unpressed/music.png");
        restartButton =  new Texture("fx.png");
        pressedRestart =  new Texture("pressedFx.png");
        backButton = new Texture("Buttons/unpressed/backButton.png");
        pressedMusicButton = new Texture("Buttons/pressed/pressedMusicButton.png");
        pressedBackButton = new Texture("Buttons/pressed/pressedBackButton.png");
        noMusicButton = new Texture("Buttons/unpressed/noMusicButton.png");
        pressedNoMusicButton = new Texture("Buttons/pressed/pressedNoMusicButton.png");
    }

    private void crearEscenaSettings(){

        escenaSettings = new Stage(vista);

        TextureRegionDrawable trdBg =  new TextureRegionDrawable(new TextureRegion(background));
        Image bg = new Image(trdBg);
        bg.setPosition(0,0);
        escenaSettings.addActor(bg);

        /*------------------------MUSIC BUTTON---------------------*/

        TextureRegionDrawable trdMusic = new TextureRegionDrawable(new TextureRegion(musicButton));
        TextureRegionDrawable trdMusicpr = new TextureRegionDrawable(new TextureRegion(pressedMusicButton));

        TextureRegionDrawable trdNoMusic = new TextureRegionDrawable(new TextureRegion(noMusicButton));
        TextureRegionDrawable trdNoMusicpr = new TextureRegionDrawable(new TextureRegion(pressedNoMusicButton));

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


        /*------------------------RESTART BUTTON---------------------*/

        TextureRegionDrawable trdRefresh = new TextureRegionDrawable(new TextureRegion(restartButton));
        TextureRegionDrawable trdRefreshPr = new TextureRegionDrawable(new TextureRegion(pressedRestart));
        final ImageButton refreshButton = new ImageButton(trdRefresh,trdRefreshPr);
        refreshButton.setPosition(374,1167);
        escenaSettings.addActor(refreshButton);
        refreshButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                pref.putBoolean("wonLevelOne", false);
                pref.putBoolean("wonLevelTwo",false);
                pref.flush();
            }
        } );

        /*--------------------------BACK BUTTON---------------------*/

        TextureRegionDrawable trdBack = new TextureRegionDrawable(new TextureRegion(backButton));
        TextureRegionDrawable trdBackpr = new TextureRegionDrawable(new TextureRegion(pressedBackButton));
        final ImageButton btnBack = new ImageButton(trdBack, trdBackpr);
        btnBack.setPosition(461,120);
        btnBack.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                juego.setScreen(new MenuScreen(juego));
            }
        } );
        escenaSettings.addActor(btnBack);

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

        if(pref.getBoolean("musicOn")){
            juego.startMenuMusic();
        }
        if(!pref.getBoolean("musicOn")){
            juego.pauseMenuMusic();
        }

        pref.flush();
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
