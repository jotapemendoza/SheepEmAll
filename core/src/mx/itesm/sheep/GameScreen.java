package mx.itesm.sheep;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by josepablo on 9/14/17.
 */

public class GameScreen extends MainScreen {

    private final Juego juego;
    private Texture pauseButton;
    private Texture sheep;
    private Texture bg;
    private Texture time;
    private Texture score;
    private Stage escenaJuego;

    private Sheep ovejaPru1;
    private Sheep ovejaPru2;
    private Sheep ovejaPru3;
    private Sheep ovejaPru4;
    private OrthogonalTiledMapRenderer render;
    private Texture oveArr;
    private Texture oveIzq;
    private Texture oveAb;
    private Texture oveDer;

    public GameScreen(Juego juego){
        this.juego = juego;
    }

    @Override
    public void show() {
        cargarTexturas();
        crearEscenaJuego();
        Gdx.input.setInputProcessor(escenaJuego);

    }

    private void crearEscenaJuego() {

        escenaJuego = new Stage(vista);


        TextureRegionDrawable trdBg =  new
                TextureRegionDrawable(new TextureRegion(bg));
        Image bg = new Image(trdBg);
        bg.setPosition(0,0);
        escenaJuego.addActor(bg);


        TextureRegionDrawable trdSheep =  new
                TextureRegionDrawable(new TextureRegion(sheep));
        Image imSheep = new Image(trdSheep);
        imSheep.setPosition(452,829);
        escenaJuego.addActor(imSheep);

        //modificación
        ovejaPru1 = new Sheep(oveArr, Sheep.Estado.ARRIBA);
        ovejaPru2 = new Sheep(oveIzq, Sheep.Estado.IZQUIERDA);
        ovejaPru3 = new Sheep(oveAb, Sheep.Estado.ABAJO);
        ovejaPru4 = new Sheep(oveDer, Sheep.Estado.DERECHA);



        TextureRegionDrawable trdTime = new
                TextureRegionDrawable(new TextureRegion(time));
        Image imTime = new Image(trdTime);
        imTime.setPosition(690, 1700);
        escenaJuego.addActor(imTime);

        TextureRegionDrawable trdScore = new
                TextureRegionDrawable(new TextureRegion(score));
        Image imScore = new Image(trdScore);
        imScore.setPosition(79, 1721);
        escenaJuego.addActor(imScore);

        TextureRegionDrawable trdPause = new
                TextureRegionDrawable(new TextureRegion(pauseButton));
        Image imPause = new Image(trdPause);
        imPause.setPosition(425, 1685);
        escenaJuego.addActor(imPause);

        imPause.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new MenuScreen(juego));
            }
        } );

    }

    private void cargarTexturas() {

        bg = new Texture("gBg.png");
        score = new Texture("score.png");
        time = new Texture("time.png");
        pauseButton = new Texture("pauseButton.png");
        sheep = new Texture("sheep.png");

        oveArr = new Texture("ovejaSprite.png");
        oveIzq = new Texture("ovejaSprite2.png");
        oveAb = new Texture("ovejaSprite3.png");
        oveDer = new Texture("ovejaSprite4.png");
    }

    @Override
    public void render(float delta) {
        borrarPantalla(0,0,0);
        batch.setProjectionMatrix(camara.combined);
        escenaJuego.draw();

        batch.begin();
        ovejaPru1.render(batch);
        ovejaPru2.render(batch);
        ovejaPru3.render(batch);
        ovejaPru4.render(batch);
        batch.end();
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