package mx.itesm.sheep;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by josepablo on 9/9/17.
 */

class AboutUsScreen extends ScreenTemplate {

    private final SheepEm sheepEm;
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



    public AboutUsScreen(SheepEm sheepEm) {
        this.sheepEm = sheepEm;
    }

    @Override
    public void show() {
        cargarTexturas();
        crearEscenaAyuda();
        Gdx.input.setInputProcessor(escenaAyuda);
        Gdx.input.setCatchBackKey(true);
    }

    private void crearEscenaAyuda() {

        escenaAyuda = new Stage(view);

        escenaAyuda.addListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode== Input.Keys.BACK){
                    sheepEm.setScreen(new MenuScreen(sheepEm));
                    sheepEm.pauseGameMusic();
                }
                return true;
            }
        });

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
        btnBack.setPosition(461,90);
        escenaAyuda.addActor(btnBack);


        btnBack.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                sheepEm.setScreen(new MenuScreen(sheepEm));
            }
        } );

        // Botones about me
        TextureRegionDrawable moniyo = new TextureRegionDrawable(new TextureRegion(monito));
        // ANDY
        final ImageButton btnmonandy = new ImageButton(moniyo);
        btnmonandy.setPosition(615,1107);
        btnmonandy.setColor(0,0,0,0);
        escenaAyuda.addActor(btnmonandy);
        btnmonandy.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                TextureRegionDrawable botandy = new TextureRegionDrawable(new TextureRegion(bioandy));
                final ImageButton btnandy = new ImageButton(botandy);
                btnandy.setPosition(0,0);
                escenaAyuda.addActor(btnandy);
                btnandy.addListener( new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        sheepEm.setScreen(new AboutUsScreen(sheepEm));
                    }
                } );
            }
        } );

        // EDGAR
        final ImageButton btnmonedgar = new ImageButton(moniyo);
        btnmonedgar.setPosition(131,1107);
        escenaAyuda.addActor(btnmonedgar);
        btnmonedgar.setColor(0,0,0,0);
        btnmonedgar.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                TextureRegionDrawable botedgar = new TextureRegionDrawable(new TextureRegion(bioedgar));
                final ImageButton btnedgar = new ImageButton(botedgar);
                btnedgar.setPosition(72,351);
                escenaAyuda.addActor(btnedgar);
                btnedgar.addListener( new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        sheepEm.setScreen(new AboutUsScreen(sheepEm));
                    }
                } );
            }
        } );

        // JPABLO
        final ImageButton btnmonpablo = new ImageButton(moniyo);
        btnmonpablo.setPosition(615,550);
        escenaAyuda.addActor(btnmonpablo);
        btnmonpablo.setColor(0,0,0,0);
        btnmonpablo.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                TextureRegionDrawable botpablo = new TextureRegionDrawable(new TextureRegion(biopablo));
                final ImageButton btnpablo = new ImageButton(botpablo);
                btnpablo.setPosition(0,0);
                escenaAyuda.addActor(btnpablo);
                btnpablo.addListener( new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        sheepEm.setScreen(new AboutUsScreen(sheepEm));
                    }
                } );
            }
        } );

        // JOSE
        final ImageButton btnmonjose = new ImageButton(moniyo);
        btnmonjose.setPosition(131,550);
        escenaAyuda.addActor(btnmonjose);
        btnmonjose.setColor(0,0,0,0);
        btnmonjose.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                TextureRegionDrawable botjose = new TextureRegionDrawable(new TextureRegion(biojose));
                final ImageButton btnjose = new ImageButton(botjose);
                btnjose.setPosition(0,0);
                escenaAyuda.addActor(btnjose);
                btnjose.addListener( new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        sheepEm.setScreen(new AboutUsScreen(sheepEm));
                    }
                } );
            }
        } );


    }

    private void cargarTexturas() {
        bgMenu = new Texture("aSBg.png");
        backButton = new Texture("Buttons/unpressed/backButton.png");
        pressedBackButton = new Texture("Buttons/pressed/pressedBackButton.png");
        bioandy = new Texture("Stills/bioandy.png");
        bioedgar = new Texture("Stills/bioedgar.png");
        biopablo = new Texture("Stills/biopablo.png");
        biojose = new Texture("Stills/biojose.png");
        monito = new Texture("monito.png");
    }

    @Override
    public void render(float delta) {
        clearScreen(0,0,0);
        batch.setProjectionMatrix(camera.combined);
        escenaAyuda.draw();
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
        escenaAyuda.dispose();
    }
}
