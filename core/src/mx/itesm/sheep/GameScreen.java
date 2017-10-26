package mx.itesm.sheep;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.sql.Time;
import java.util.ArrayList;

/**
 * Created by josepablo on 9/14/17.
 */

public class GameScreen extends MainScreen {

    private final Juego juego;
    private Texture homeButton;
    private Texture pauseButton;
    private Texture sheep;
    private Texture bg;
    private Texture time;
    private Texture score;
    private Stage escenaJuego;

    // Arreglo de ovejas
    private Array<Oveja> arrOvejas;
    private Oveja ovejaMoviendo = null;
    private final int cantOve = 30;

    // Tiempo de salida y de partida del juego
    private float tiempo;
    private float salida;

    private Texture continueButton;

    private Texture oveArr;
    private Texture oveIzq;
    private Texture oveAb;
    private Texture oveDer;

    private EstadoJuego estado;

    private EscenaPausa escenaPausa;

    public GameScreen(Juego juego){
        this.juego = juego;
    }

    @Override
    public void show() {
        cargarTexturas();
        cargarOvejas();
        crearEscenaJuego();
        Gdx.input.setInputProcessor(escenaJuego);
        estado = EstadoJuego.JUGANDO;
    }

    private void crearEscenaJuego() {

        escenaJuego = new Stage(vista);


        TextureRegionDrawable trdBg =  new
                TextureRegionDrawable(new TextureRegion(bg));
        Image bg = new Image(trdBg);
        bg.setPosition(0,0);
        escenaJuego.addActor(bg);


        TextureRegionDrawable trdPause = new
                TextureRegionDrawable(new TextureRegion(pauseButton));
        ImageButton imPause = new ImageButton(trdPause);
        imPause.setPosition(425, 1685);
        escenaJuego.addActor(imPause);

        imPause.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                estado = EstadoJuego.PAUSADO;
                Gdx.input.setInputProcessor(escenaPausa);
            }
        } );

        escenaJuego.addListener(new DragListener(){
            @Override
            public void dragStart(InputEvent event, float x, float y, int pointer) {
                super.dragStart(event, x, y, pointer);
                Vector3 v = new Vector3(x,y,0);
                camara.unproject(v);
                ovejaMoviendo = arrOvejas.get(0);
                Gdx.app.log("dragStart", "Inicia movimeinto");
            }

            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                super.drag(event, x, y, pointer);
                Vector3 v = new Vector3(x,y,0);
                camara.unproject(v);
                ovejaMoviendo.setEstado(Oveja.Estado.MOVIENDO);
                //(cursor) x+ANCHO/2,y+ALTO/2 segun proporción dela imagen final
                ovejaMoviendo.setX(x-16); // no se porque tiene que estar en negativo
                ovejaMoviendo.setY(y-32); // no se porque tiene que estar en negativo
                Gdx.app.log("drag", "x = " + v.x + ", y = " + v.y);
            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer) {
                super.dragStop(event, x, y, pointer);
                // verificar si está en el corral
            }

        });
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
                if (arrOvejas.get(i).gety() <= 0){
                    arrOvejas.removeIndex(i);
                    System.out.println("ovejas disponibles: " + arrOvejas.size);
                    break;
                }
            }
            if (arrOvejas.get(i).getEstado().equals(Oveja.Estado.ABAJO)){
                if (arrOvejas.get(i).gety() >= 1900){
                    arrOvejas.removeIndex(i);
                    System.out.println("ovejas disponibles: " + arrOvejas.size);
                    break;
                }
            }
            if (arrOvejas.get(i).getEstado().equals(Oveja.Estado.IZQUIERDA)){
                if (arrOvejas.get(i).getx() >= 1080){
                    arrOvejas.removeIndex(i);
                    System.out.println("ovejas disponibles: " + arrOvejas.size);
                    break;
                }
            }
            if (arrOvejas.get(i).getEstado().equals(Oveja.Estado.DERECHA)){
                if (arrOvejas.get(i).getx() <= 0){
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
        oveDer = new Texture("sheep_left.png");
    }

    @Override
    public void render(float delta) {


        borrarPantalla(0,0,0);
        batch.setProjectionMatrix(camara.combined);
        escenaJuego.draw();


    //----------------------------------------------------------------------------------------------
        if (estado==EstadoJuego.PAUSADO) {
            System.out.println("hola");
            escenaPausa.draw();
        }
    //----------------------------------------------------------------------------------------------

        salida += Gdx.graphics.getDeltaTime();
        tiempo += Gdx.graphics.getDeltaTime();
        eliminarOveja();

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

    enum EstadoJuego {
        JUGANDO,
        PAUSADO
    }
    // Escena pausa --------------------------------------------------------------------------------

    private class EscenaPausa extends Stage
    {
        public EscenaPausa(Viewport vista, SpriteBatch batch) {
            super(vista,batch);

            continueButton = new Texture("continueButton.png");
            TextureRegionDrawable trdContinue = new TextureRegionDrawable(
                    new TextureRegion(continueButton));
            ImageButton btnContinue = new ImageButton(trdContinue);
            btnContinue.setPosition(294,963);
            btnContinue.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //Cambio el estado de juego a JUGANDO y regreso el poder a la escenaJuego
                    estado = EstadoJuego.JUGANDO;
                    Gdx.input.setInputProcessor(escenaJuego);
                }
            });
            this.addActor(btnContinue);

            homeButton = new Texture("homeButton.png");
            TextureRegionDrawable trdHome = new TextureRegionDrawable(
                    new TextureRegion(homeButton));
            ImageButton homeBtn = new ImageButton(trdHome);
            homeBtn.setPosition(583,963);
            homeBtn.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al menú
                    juego.setScreen(new MenuScreen(juego));
                }
            });
            this.addActor(homeBtn);

        }
    }

    //----------------------------------------------------------------------------------------------
}