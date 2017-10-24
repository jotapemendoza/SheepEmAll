package mx.itesm.sheep;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

import java.sql.Time;
import java.util.ArrayList;

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

    // Arreglo de ovejas
    private Array<Oveja> arrOvejas;
    private final int cantOve = 30;

    // Tiempo de salida y de partida del juego
    private float tiempo;
    private float salida;

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
        cargarOvejas();
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

    private void cargarOvejas(){
        //Llenar arreglo Ovejas
        arrOvejas = new Array<Oveja>(cantOve);
        Oveja ove;

        for (int i = 0; i < cantOve; i++){
            int random = (int) (Math.random()*4)+1;

            if (random == 1){
                ove = new Oveja(oveArr, Oveja.Estado.ARRIBA);
                arrOvejas.add(ove);
            }else if (random == 2){
                ove = new Oveja(oveAb, Oveja.Estado.ABAJO);
                arrOvejas.add(ove);
            }else if (random == 3){
                ove = new Oveja(oveIzq, Oveja.Estado.IZQUIERDA);
                arrOvejas.add(ove);
            }else{
                ove = new Oveja(oveDer, Oveja.Estado.DERECHA);
                arrOvejas.add(ove);
            }
        }
    }

    private void eliminarOveja(){
        for (int i = 0; i < arrOvejas.size; i++){
            if (arrOvejas.get(i).getEstado().equals(Oveja.Estado.ARRIBA)){
                if (arrOvejas.get(i).getyA() <= 0){
                    arrOvejas.removeIndex(i);
                    System.out.println("ovejas disponibles: " + arrOvejas.size);
                    break;
                }
            }
            if (arrOvejas.get(i).getEstado().equals(Oveja.Estado.ABAJO)){
                if (arrOvejas.get(i).getyAB() >= 1900){
                    arrOvejas.removeIndex(i);
                    System.out.println("ovejas disponibles: " + arrOvejas.size);
                    break;
                }
            }
            if (arrOvejas.get(i).getEstado().equals(Oveja.Estado.IZQUIERDA)){
                if (arrOvejas.get(i).getxI() >= 1080){
                    arrOvejas.removeIndex(i);
                    System.out.println("ovejas disponibles: " + arrOvejas.size);
                    break;
                }
            }
            if (arrOvejas.get(i).getEstado().equals(Oveja.Estado.DERECHA)){
                if (arrOvejas.get(i).getxD() <= 0){
                    arrOvejas.removeIndex(i);
                    System.out.println("ovejas disponibles: " + arrOvejas.size);
                    break;
                }
            }
        }
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
        salida += Gdx.graphics.getDeltaTime();
        tiempo += Gdx.graphics.getDeltaTime();
        //System.out.println("tiempo: " + tiempo + " " + "tiempoSalida: " + salida);

        // eliminar ovejas
        eliminarOveja();
        borrarPantalla(0,0,0);
        batch.setProjectionMatrix(camara.combined);
        escenaJuego.draw();

        //Salen ovejas cada 5 segundos
        batch.begin();
        for (int i = 0; i < arrOvejas.size; i++){
            if (tiempo <= 60.0){
                if (salida <= 5){
                    arrOvejas.get(i).render(batch);
                }else {
                    salida = 0;
                }
            }else {
                arrOvejas.get(i).render(batch);
            }
        }
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