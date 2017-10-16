package mx.itesm.sheep;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by josepablo on 9/14/17.
 */

public class GameScreen extends Screen {

    private final Juego juego;
    private Texture pauseButton;
    private Texture sheep;
    private Texture bg;
    private Texture time;
    private Texture score;
    private Stage escenaJuego;

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



    }

    @Override
    public void render(float delta) {
        borrarPantalla(0,0,0);
        batch.setProjectionMatrix(camara.combined);
        escenaJuego.draw();
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
