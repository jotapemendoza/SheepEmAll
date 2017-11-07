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

class AboutUsScreen extends MainScreen {

    private final Juego juego;
    private Texture bgMenu;
    private Texture backButton;
    private Stage escenaAyuda;
    private Texture pressedBackButton;

    // About me

    private Texture bioandy;
    private Texture bioedgar;
    private Texture biojose;
    private Texture biopablo;
    private Texture monito;



    public AboutUsScreen(Juego juego) {
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

        // Botón regresar
        TextureRegionDrawable trdBack = new TextureRegionDrawable(new TextureRegion(backButton));
        TextureRegionDrawable trdBackpr = new TextureRegionDrawable(new TextureRegion(pressedBackButton));
        final ImageButton btnBack = new ImageButton(trdBack, trdBackpr);
        btnBack.setPosition(461,120);
        escenaAyuda.addActor(btnBack);


        btnBack.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                juego.setScreen(new MenuScreen(juego));
            }
        } );

        // Botones about me
        TextureRegionDrawable moniyo = new TextureRegionDrawable(new TextureRegion(monito));
        // ANDY
        final ImageButton btnmonandy = new ImageButton(moniyo);
        btnmonandy.setPosition(615,1107);
        escenaAyuda.addActor(btnmonandy);
        btnmonandy.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                TextureRegionDrawable botandy = new TextureRegionDrawable(new TextureRegion(bioandy));
                final ImageButton btnandy = new ImageButton(botandy);
                btnandy.setPosition(80,350);
                escenaAyuda.addActor(btnandy);
                btnandy.addListener( new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        juego.setScreen(new aboutScreen(juego));
                    }
                } );
            }
        } );

        // EDGAR
        final ImageButton btnmonedgar = new ImageButton(moniyo);
        btnmonedgar.setPosition(131,1107);
        escenaAyuda.addActor(btnmonedgar);
        btnmonedgar.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                TextureRegionDrawable botedgar = new TextureRegionDrawable(new TextureRegion(bioedgar));
                final ImageButton btnedgar = new ImageButton(botedgar);
                btnedgar.setPosition(70,350);
                escenaAyuda.addActor(btnedgar);
                btnedgar.addListener( new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        juego.setScreen(new aboutScreen(juego));
                    }
                } );
            }
        } );

        // JPABLO
        final ImageButton btnmonpablo = new ImageButton(moniyo);
        btnmonpablo.setPosition(615,550);
        escenaAyuda.addActor(btnmonpablo);
        btnmonpablo.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                TextureRegionDrawable botpablo = new TextureRegionDrawable(new TextureRegion(biopablo));
                final ImageButton btnpablo = new ImageButton(botpablo);
                btnpablo.setPosition(70,350);
                escenaAyuda.addActor(btnpablo);
                btnpablo.addListener( new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        juego.setScreen(new aboutScreen(juego));
                    }
                } );
            }
        } );

        // JOSE
        final ImageButton btnmonjose = new ImageButton(moniyo);
        btnmonjose.setPosition(131,550);
        escenaAyuda.addActor(btnmonjose);
        btnmonjose.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                TextureRegionDrawable botjose = new TextureRegionDrawable(new TextureRegion(biojose));
                final ImageButton btnjose = new ImageButton(botjose);
                btnjose.setPosition(70,350);
                escenaAyuda.addActor(btnjose);
                btnjose.addListener( new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        juego.setScreen(new aboutScreen(juego));
                    }
                } );
            }
        } );


    }

    private void cargarTexturas() {
        bgMenu = new Texture("aSBg.png");
        backButton = new Texture("buttons/unpressed/backButton.png");
        pressedBackButton = new Texture("buttons/pressed/pressedBackButton.png");
        bioandy = new Texture("bioandy.png");
        bioedgar = new Texture("bioedgar.png");
        biopablo = new Texture("biopablo.png");
        biojose = new Texture("biojose.png");
        monito = new Texture("monito.png");
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
