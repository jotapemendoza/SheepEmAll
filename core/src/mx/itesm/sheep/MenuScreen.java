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

    // Texturas de los botones

    private Texture playButton;
    private Texture aboutButton;
    private Texture logo;
    private Texture grass;
    private Texture settingsButton;

    private Texture bgMenu;

    private Texture cloud;

    // nubes
    private Image cloudP; // pequeña
    private Image cloudP2;
    private Image cloudM; // mediana
    private Image cloudG; // grande

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

        //Método privado que crea el background y el movimiento de las nubes
        bgandcloudGenerator();
        drawGraphics();

        // Botón jugar
        TextureRegionDrawable trdPlay = new
                TextureRegionDrawable(new TextureRegion(playButton));
        ImageButton btnPlay = new ImageButton(trdPlay);
        btnPlay.setPosition(376,681);
        escenaMenu.addActor(btnPlay);

        //Listener botón
        btnPlay.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.log("clicked","*********** TOUCH **********");
                juego.setScreen(new GameScreen(juego));
            }
        } );



        //Botón About (Parte gráfica)
        TextureRegionDrawable trdAyuda = new
                TextureRegionDrawable(new TextureRegion(aboutButton));
        ImageButton btnAyuda = new ImageButton(trdAyuda);
        btnAyuda.setPosition(195, 749);
        escenaMenu.addActor(btnAyuda);

        //Parte funcional (listener)

        btnAyuda.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new aboutScreen(juego));
            }
        } );



        //Boton Settings (Parte gráfica)
        TextureRegionDrawable trdSettings = new
                TextureRegionDrawable(new TextureRegion(settingsButton));
        ImageButton btnSettings = new ImageButton(trdSettings);
        btnSettings.setPosition(728,749);
        escenaMenu.addActor(btnSettings);

        //Parte funcional (listener)

        btnSettings.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new SettingsScreen(juego));
            }
        } );




    }

    private void bgandcloudGenerator() {
        TextureRegionDrawable trdBg =  new
                TextureRegionDrawable(new TextureRegion(bgMenu));
        Image bg = new Image(trdBg);
        bg.setPosition(0,0);
        escenaMenu.addActor(bg);


        // nubes
        TextureRegionDrawable cloud1 = new
                TextureRegionDrawable(new TextureRegion(cloud));
        // nube pequeña
        Image cloud2 = new Image(cloud1);
        cloud2.setPosition(1081,950);
        cloud2.setSize(220,100);
        cloudP = cloud2;
        escenaMenu.addActor(cloud2);
        // nube pequeña 2
        Image cloud5 = new Image(cloud1);
        cloud5.setPosition(-200,1150);
        cloud5.setSize(220,100);
        cloudP2 = cloud5;
        escenaMenu.addActor(cloud5);
        // nube mediana
        Image cloud3 = new Image(cloud1);
        cloud3.setPosition(1300,1500);
        cloud3.setSize(340,140);
        cloudM = cloud3;
        escenaMenu.addActor(cloud3);
        // nube grande
        Image cloud4 = new Image(cloud1);
        cloud4.setPosition(-450,1750);
        cloud4.setSize(380,160);
        cloudG = cloud4;
        escenaMenu.addActor(cloud4);


    }

    private void drawGraphics() {
        TextureRegionDrawable trdLogo =  new
                TextureRegionDrawable(new TextureRegion(logo));
        Image lg = new Image(trdLogo);
        lg.setPosition(151,965);
        escenaMenu.addActor(lg);

        //Grass

        TextureRegionDrawable trdGrass = new
                TextureRegionDrawable(new TextureRegion(grass));
        Image gr = new Image(trdGrass);
        gr.setPosition(0,0);
        escenaMenu.addActor(gr);
    }

    private void cargarTexturas() {

        bgMenu = new Texture("menuBg.png");
        logo =  new Texture("logo.png");
        playButton = new Texture("playButton.png");
        aboutButton = new Texture("aboutButton.png");
        grass = new Texture("grassMenu.png");
        settingsButton = new Texture("settingsButton.png");
        cloud = new Texture("cloud.png");

    }


    @Override
    public void render(float delta) {
        borrarPantalla(1,1,1);
        batch.setProjectionMatrix(camara.combined);
        escenaMenu.draw();


        cloudP.setX(cloudP.getX()-24*delta);
        if (cloudP.getX() <= -100){
            cloudP.setX(ANCHO+1);
        }
        cloudP.setColor(1,1,1,0.4f);

        cloudP2.setX(cloudP2.getX()+26*delta);
        if (cloudP2.getX() <= -ANCHO){
            cloudP2.setX(ANCHO+1);
        }
        cloudP2.setColor(1,1,1,0.5f);

        cloudM.setX(cloudM.getX()-28*delta);
        if (cloudM.getX() <= -100){
            cloudM.setX(ANCHO+1);
        }
        cloudM.setColor(1,1,1,0.6f);

        cloudG.setX(cloudG.getX()+22*delta);
        if (cloudG.getX() <= -ANCHO){
            cloudG.setX(ANCHO+1);
        }
        cloudG.setColor(1,1,1,0.7f);

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
