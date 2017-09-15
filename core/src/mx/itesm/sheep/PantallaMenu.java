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

class PantallaMenu extends Pantalla
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

    public PantallaMenu(Juego juego) {
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
                juego.setScreen(new PantallaJuego(juego));
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
                juego.setScreen(new PantallaADe(juego));
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
                juego.setScreen(new PantallaSettings(juego));
            }
        } );




    }

    private void bgandcloudGenerator() {
        TextureRegionDrawable trdBg =  new
                TextureRegionDrawable(new TextureRegion(bgMenu));
        Image bg = new Image(trdBg);
        bg.setPosition(0,0);
        escenaMenu.addActor(bg);
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

    }


    @Override
    public void render(float delta) {
        borrarPantalla(1,1,1);
        batch.setProjectionMatrix(camara.combined);
        escenaMenu.draw();

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
