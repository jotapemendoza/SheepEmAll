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
 * El menú principal del sheepEm.
 */

class MenuScreen extends ScreenTemplate
{
    private SheepEm sheepEm;

    // Contenedor de los botones
    private Stage escenaMenu;

    // Button textures --------------------------------------------------

    private Texture playButton;
    private Texture aboutButton;
    private Texture pressedPlayButton;
    private Texture pressedAboutButton;
    private Texture settingsButton;
    private Texture pressedSettingsButton;

    // ------------------------------------------------------------------

    private Texture logo;
    private Texture grass;
    private Texture sheep;
    private Texture bgMenu;
    private Texture cloud;

    private Music music;



    private Image cloud_1;
    private Image cloud_2;
    private Image cloud_3;
    private Image cloud_4;
    private Image cloud_5;


    private int easterEgg;

    public MenuScreen(SheepEm sheepEm) {
        this.sheepEm = sheepEm;
        pref.putBoolean("played", pref.getBoolean("played"));
    }

    @Override
    public void show() {
        cargarTexturas();
        crearEscenaMenu();
        Gdx.input.setInputProcessor(escenaMenu);
        easterEgg = 0;
    }

    private void crearEscenaMenu() {

        escenaMenu = new Stage(view);
        bgandcloudGenerator();
        drawGraphics();

    // Play button ----------------------------------------------------
        TextureRegionDrawable trdPlay = new
                TextureRegionDrawable(new TextureRegion(playButton));
        TextureRegionDrawable trdprPlay = new
                TextureRegionDrawable(new TextureRegion(pressedPlayButton));
        ImageButton btnPlay = new ImageButton(trdPlay, trdprPlay);
        btnPlay.setPosition(376,681);
        escenaMenu.addActor(btnPlay);


        btnPlay.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                sheepEm.setScreen(new MapScreen(sheepEm));

            }
        } );
    //-----------------------------------------------------------------


        // About button ---------------------------------------------------
        TextureRegionDrawable trdAyuda = new
                TextureRegionDrawable(new TextureRegion(aboutButton));
        TextureRegionDrawable trdprAyuda = new
                TextureRegionDrawable(new TextureRegion(pressedAboutButton));
        ImageButton btnAyuda = new ImageButton(trdAyuda, trdprAyuda);
        btnAyuda.setPosition(195, 749);
        escenaMenu.addActor(btnAyuda);

        btnAyuda.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                sheepEm.setScreen(new AboutUsScreen(sheepEm));
            }
        } );
    //-----------------------------------------------------------------


    // Settings button ------------------------------------------------
        TextureRegionDrawable trdSettings = new
                TextureRegionDrawable(new TextureRegion(settingsButton));
        TextureRegionDrawable trdprSettings = new
                TextureRegionDrawable(new TextureRegion(pressedSettingsButton));
        ImageButton btnSettings = new ImageButton(trdSettings, trdprSettings);
        btnSettings.setPosition(728,749);
        escenaMenu.addActor(btnSettings);

        btnSettings.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                sheepEm.setScreen(new SettingsScreen(sheepEm));
            }
        } );
    //-----------------------------------------------------------------



    }

    private void bgandcloudGenerator() {
        TextureRegionDrawable trdBg =  new
                TextureRegionDrawable(new TextureRegion(bgMenu));
        Image bg = new Image(trdBg);
        bg.setPosition(0,0);
        escenaMenu.addActor(bg);



        TextureRegionDrawable cloud = new
                TextureRegionDrawable(new TextureRegion(this.cloud));

    // Moving clouds (from top to bottom) -----------------------------
        cloud_1 = new Image(cloud);
        cloud_1.setPosition(-450,1700);
        cloud_1.setSize(570,240);
        escenaMenu.addActor(cloud_1);

        cloud_2 = new Image(cloud);
        cloud_2.setPosition(1300,1200);
        cloud_2.setSize(340,140);
        escenaMenu.addActor(cloud_2);

        cloud_3 = new Image(cloud);
        cloud_3.setPosition(-200,1050);
        cloud_3.setSize(220,100);
        escenaMenu.addActor(cloud_3);

        cloud_4 = new Image(cloud);
        cloud_4.setPosition(1081,750);
        cloud_4.setSize(110,50);
        escenaMenu.addActor(cloud_4);

        cloud_5 = new Image(cloud);
        cloud_5.setPosition(1081,550);
        cloud_5.setSize(140,80);
        escenaMenu.addActor(cloud_5);




    //-----------------------------------------------------------------

    }

    private void drawGraphics() {

    //Logo ------------------------------------------------------------
        TextureRegionDrawable trdLogo =  new
                TextureRegionDrawable(new TextureRegion(logo));
        Image lg = new Image(trdLogo);
        lg.setPosition(151,965);
        escenaMenu.addActor(lg);
    //-----------------------------------------------------------------

    // Grass ----------------------------------------------------------
        TextureRegionDrawable trdGrass = new
                TextureRegionDrawable(new TextureRegion(grass));
        Image gr = new Image(trdGrass);
        gr.setPosition(0,0);
        escenaMenu.addActor(gr);
    // ----------------------------------------------------------------

        TextureRegionDrawable trdSheep = new
                TextureRegionDrawable(new TextureRegion(sheep));
        Image sh = new Image(trdSheep);
        sh.setPosition(120,145);
        escenaMenu.addActor(sh);

        sh.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                easterEgg++;
            }
        } );


    }

    private void cargarTexturas() {

    // Graphic texures ------------------------------------------------
        logo =  new Texture("logo.png");
        cloud = new Texture("cloud.png");
        bgMenu = new Texture("menuBg.png");
        grass = new Texture("grassMenu.png");
        sheep = new Texture("sheepMenu.png");
    //-----------------------------------------------------------------

    // Button textures ------------------------------------------------
        playButton = new Texture("Buttons/unpressed/playButton.png");
        aboutButton = new Texture("Buttons/unpressed/aboutButton.png");
        settingsButton = new Texture("Buttons/unpressed/settingsButton.png");
        pressedPlayButton = new Texture("Buttons/pressed/pressedPlayButton.png");
        pressedAboutButton = new Texture("Buttons/pressed/pressedAboutButton.png");
        pressedSettingsButton = new Texture("Buttons/pressed/pressedSettingsButton.png");
    //-----------------------------------------------------------------

    }


    @Override
    public void render(float delta) {
        clearScreen(1,1,1);
        batch.setProjectionMatrix(camera.combined);
        escenaMenu.draw();

        cloud_1.setX(cloud_1.getX()+100*delta);
        if (cloud_1.getX() >= WIDTH){
            cloud_1.setX(-cloud_1.getWidth());
        }
        cloud_1.setColor(1,1,1,0.7f);

        cloud_2.setX(cloud_2.getX()+70*delta);
        if (cloud_2.getX() >= WIDTH){
            cloud_2.setX(-cloud_2.getWidth());
        }
        cloud_2.setColor(1,1,1,0.6f);

        cloud_3.setX(cloud_3.getX()+50*delta);
        if (cloud_3.getX() >= WIDTH){
            cloud_3.setX(-cloud_3.getWidth());
        }
        cloud_3.setColor(1,1,1,0.5f);

        cloud_4.setX(cloud_4.getX()+30*delta);
        if (cloud_4.getX() >= WIDTH){
            cloud_4.setX(-cloud_4.getWidth());
        }
        cloud_4.setColor(1,1,1,0.4f);

        cloud_5.setX(cloud_5.getX()+15*delta);
        if (cloud_5.getX() >= WIDTH){
            cloud_5.setX(-cloud_5.getWidth());
        }
        cloud_5.setColor(1,1,1,0.3f);


        if(pref.getBoolean("musicOn")){
            sheepEm.startMenuMusic();

        }
        if(!pref.getBoolean("musicOn")){
            sheepEm.pauseMenuMusic();
        }

    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    // Liberar los recursos asignados
    @Override
    public void dispose() {
    }
}
