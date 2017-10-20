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
 * El menú principal del juego.
 */

class MenuScreen extends MainScreen
{
    private Juego juego;

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



    private Image cloud_1;
    private Image cloud_2;
    private Image cloud_3;
    private Image cloud_4;




    public MenuScreen(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        cargarTexturas();   // Carga imágenes
        crearEscenaMenu();  // Crea la escena
        Gdx.input.setInputProcessor(escenaMenu);
    }

    private void crearEscenaMenu() {

        escenaMenu = new Stage(vista);

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
                juego.setScreen(new GameScreen(juego));
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
                juego.setScreen(new aboutScreen(juego));
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
                juego.setScreen(new SettingsScreen(juego));
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
        sh.setPosition(74,145);
        escenaMenu.addActor(sh);

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
        playButton = new Texture("playButton.png");
        aboutButton = new Texture("aboutButton.png");
        settingsButton = new Texture("settingsButton.png");
        pressedPlayButton = new Texture("pressedPlayButton.png");
        pressedAboutButton = new Texture("pressedAboutButton.png");
        pressedSettingsButton = new Texture("pressedSettingsButton.png");
    //-----------------------------------------------------------------

    }


    @Override
    public void render(float delta) {
        borrarPantalla(1,1,1);
        batch.setProjectionMatrix(camara.combined);
        escenaMenu.draw();

        cloud_1.setX(cloud_1.getX()+100*delta);
        if (cloud_1.getX() >= ANCHO){
            cloud_1.setX(-cloud_1.getWidth());
        }
        cloud_1.setColor(1,1,1,0.7f);

        cloud_2.setX(cloud_2.getX()+70*delta);
        if (cloud_2.getX() >= ANCHO){
            cloud_2.setX(-cloud_2.getWidth());
        }
        cloud_2.setColor(1,1,1,0.6f);

        cloud_3.setX(cloud_3.getX()+50*delta);
        if (cloud_3.getX() >= ANCHO){
            cloud_3.setX(-cloud_3.getWidth());
        }
        cloud_3.setColor(1,1,1,0.5f);

        cloud_4.setX(cloud_4.getX()+30*delta);
        if (cloud_4.getX() >= ANCHO){
            cloud_4.setX(-cloud_4.getWidth());
        }
        cloud_4.setColor(1,1,1,0.4f);

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
