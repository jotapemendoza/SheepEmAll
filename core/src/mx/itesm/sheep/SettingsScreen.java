package mx.itesm.sheep;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by josepablo on 9/14/17.
 */

public class SettingsScreen extends ScreenTemplate {

    private final SheepEm sheepEm;
    private Stage escenaSettings;
    private Texture musicButton,
            backButton,
            noMusicButton,
            restartButton,
            pressedRestart,
            pressedMusicButton,
            pressedBackButton,
            pressedNoMusicButton,
            pressedNoSfxButton,
            pressedSfxButton,
            noSfxButton,
            background;

    private ImageButton btnMusic,
            btnNoMusic;

    private Texture sfxButton;
    private ImageButton btnSfx;
    private ImageButton btnNoSfx;
    private boolean popup;

    private popScene popScene;

    public SettingsScreen(SheepEm sheepEm){
        this.sheepEm = sheepEm;
    }

    @Override
    public void show() {
        cargarTexturas();
        crearEscenaSettings();
        Gdx.input.setInputProcessor(escenaSettings);
        Gdx.input.setCatchBackKey(true);
        popup = false;
        popScene = new popScene(view,batch);
    }

    private void cargarTexturas() {
        background = new Texture("sBg.png");
        musicButton = new Texture("Buttons/unpressed/musicButton.png");
        restartButton =  new Texture("fx.png");
        pressedRestart =  new Texture("pressedFx.png");
        backButton = new Texture("Buttons/unpressed/backButton.png");
        pressedMusicButton = new Texture("Buttons/pressed/pressedMusicButton.png");
        pressedBackButton = new Texture("Buttons/pressed/pressedBackButton.png");
        noMusicButton = new Texture("Buttons/unpressed/noMusicButton.png");
        pressedNoMusicButton = new Texture("Buttons/pressed/pressedNoMusicButton.png");
        sfxButton = new Texture("Buttons/unpressed/sfxButton.png");
        pressedSfxButton = new Texture("Buttons/pressed/PressedSfxButton.png");
        noSfxButton = new Texture("Buttons/unpressed/NoSfxButton.png");
        pressedNoSfxButton = new Texture("Buttons/pressed/PressedNoSfxButton.png");

    }

    private void crearEscenaSettings(){

        escenaSettings = new Stage(view);

        escenaSettings.addListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode== Input.Keys.BACK){
                    sheepEm.setScreen(new MenuScreen(sheepEm));
                }
                return true;
            }
        });

        TextureRegionDrawable trdBg =  new TextureRegionDrawable(new TextureRegion(background));
        Image bg = new Image(trdBg);
        bg.setPosition(0,0);
        escenaSettings.addActor(bg);
        /*-------------------------SFX BUTTON----------------------*/
        TextureRegionDrawable trdSfx = new TextureRegionDrawable(new TextureRegion(sfxButton));
        TextureRegionDrawable trdPressedSfx = new TextureRegionDrawable(new TextureRegion(pressedSfxButton));

        TextureRegionDrawable trdNoSfx = new TextureRegionDrawable(new TextureRegion(noSfxButton));
        TextureRegionDrawable trdPressedNoSfx = new TextureRegionDrawable(new TextureRegion(pressedNoSfxButton));

        btnSfx = new ImageButton(trdSfx,trdPressedSfx);
        btnSfx.setPosition(640,581);
        btnSfx.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                pref.putBoolean("fxOn",false);
                pref.flush();
            }
        } );

        btnNoSfx = new ImageButton(trdNoSfx,trdPressedNoSfx);
        btnNoSfx.setPosition(640,581);
        btnNoSfx.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                pref.putBoolean("fxOn",true);
                pref.flush();
            }
        } );



        /*------------------------MUSIC BUTTON---------------------*/

        TextureRegionDrawable trdMusic = new TextureRegionDrawable(new TextureRegion(musicButton));
        TextureRegionDrawable trdMusicpr = new TextureRegionDrawable(new TextureRegion(pressedMusicButton));

        TextureRegionDrawable trdNoMusic = new TextureRegionDrawable(new TextureRegion(noMusicButton));
        TextureRegionDrawable trdNoMusicpr = new TextureRegionDrawable(new TextureRegion(pressedNoMusicButton));

        btnNoMusic = new ImageButton(trdNoMusic,trdNoMusicpr);
        btnNoMusic.setPosition(180,581);
        btnMusic =  new ImageButton(trdMusic, trdMusicpr);
        btnMusic.setPosition(180,581);

        btnMusic.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                pref.putBoolean("musicOn",false);
                pref.flush();

            }
        } );

        btnNoMusic.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                pref.putBoolean("musicOn",true);
                pref.flush();
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
                popup = true;

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

                sheepEm.setScreen(new MenuScreen(sheepEm));
            }
        } );
        escenaSettings.addActor(btnBack);

    }

    @Override
    public void render(float delta) {
        clearScreen(0,0,0);
        batch.setProjectionMatrix(camera.combined);

        escenaSettings.draw();

        if (popup==true){
            popScene.draw();
            Gdx.input.setInputProcessor(popScene);
        }

        if(pref.getBoolean("musicOn")){
            btnMusic.setPosition(180,581);
            escenaSettings.addActor(btnMusic);
            btnNoMusic.remove();

        }
        if(!pref.getBoolean("musicOn")){
            btnMusic.setPosition(180,581);
            escenaSettings.addActor(btnNoMusic);
            btnMusic.remove();
        }

        if(pref.getBoolean("fxOn")){
            btnSfx.setPosition(640,581);
            escenaSettings.addActor(btnSfx);
            btnNoSfx.remove();

        }
        if(!pref.getBoolean("fxOn")){
            btnSfx.setPosition(640,581);
            escenaSettings.addActor(btnNoSfx);
            btnSfx.remove();
        }


        if(pref.getBoolean("musicOn")){
            sheepEm.startMenuMusic();

        }
        if(!pref.getBoolean("musicOn")){
            sheepEm.stopMenuMusic();
        }

        pref.flush();
    }

    private class popScene extends Stage{
        public popScene(Viewport vista, SpriteBatch batch){

            super(vista,batch);

            Texture opaque = new Texture("opaque.png");
            TextureRegionDrawable trdOpaq = new TextureRegionDrawable(new TextureRegion(opaque));
            Image op = new Image(trdOpaq);
            op.setPosition(0,0);
            this.addActor(op);

            Texture popUp = new Texture("popUp.png");
            TextureRegionDrawable trdPopUp = new TextureRegionDrawable(new TextureRegion(popUp));
            Image rect = new Image(trdPopUp);
            rect.setPosition(187,739);
            this.addActor(rect);


            Texture yes = new Texture("Buttons/unpressed/yes.png");
            TextureRegionDrawable trdHome = new TextureRegionDrawable(new TextureRegion(yes));
            TextureRegionDrawable trdHomePr = new TextureRegionDrawable(new TextureRegion(new Texture("Buttons/pressed/PressedYes.png")));
            ImageButton homeButton = new ImageButton(trdHome, trdHomePr);
            homeButton.setPosition(581,873);
            homeButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    pref.putBoolean("wonLevelOne", false);
                    pref.putBoolean("wonLevelTwo",false);
                    pref.flush();
                    popup = false;
                    Gdx.input.setInputProcessor(escenaSettings);
                }
            });
            this.addActor(homeButton);

            Texture no = new Texture("Buttons/unpressed/no.png");
            TextureRegionDrawable trdAgain = new TextureRegionDrawable(new TextureRegion(no));
            TextureRegionDrawable trdAgainpr = new TextureRegionDrawable(new TextureRegion(new Texture("Buttons/pressed/PressedNo.png")));
            ImageButton tryAgain = new ImageButton(trdAgain, trdAgainpr);
            tryAgain.setPosition(345,873);
            tryAgain.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                   popup = false;
                   Gdx.input.setInputProcessor(escenaSettings);
                }
            });
            this.addActor(tryAgain);


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
