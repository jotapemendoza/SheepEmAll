package mx.itesm.sheep;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
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
    private Texture bg;
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

    // Escena "pop-up" cuándo se presiona el botón de pausa
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

        // Background ------------------------------------------------------------------------------

        TextureRegionDrawable trdBg =  new
                TextureRegionDrawable(new TextureRegion(bg));
        Image bg = new Image(trdBg);
        bg.setPosition(0,0);
        //escenaJuego.addActor(bg);

        // Botón de pausa --------------------------------------------------------------------------

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
                escenaPausa = new EscenaPausa(vista,batch);
                Gdx.input.setInputProcessor(escenaPausa);

            }
        } );

        // Tomar y arrastrar ovejas ----------------------------------------------------------------

        escenaJuego.addListener(new DragListener(){
            @Override
            public void dragStart(InputEvent event, float x, float y, int pointer) {
                super.dragStart(event, x, y, pointer);
                for (Oveja oveja: arrOvejas){
                    if (oveja.comparar(x,y)){
                        ovejaMoviendo = oveja;
                        Gdx.app.log("dragStart", "Inicia movimeinto");
                        break;
                    }
                }
            }

            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                super.drag(event, x, y, pointer);
                if (ovejaMoviendo == null){ return; }
                //(cursor) x-ANCHO/2,y-ALTO/2 segun proporción dela imagen final
                ovejaMoviendo.setX(x - ovejaMoviendo.getAncho()/2);
                ovejaMoviendo.setY(y - ovejaMoviendo.getAlto()/2);
                ovejaMoviendo.setEstado(Oveja.Estado.MOVIENDO);
                Gdx.app.log("drag", "x = " +x + ", y = " +y);
            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer) {
                super.dragStop(event, x, y, pointer);
                // verificar si está en el corral
                ovejaMoviendo = null;
            }

        });

    }

    // Método que carga las ovejas en el juego -----------------------------------------------------
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

    // Método que elimina las ovejas en el juego ---------------------------------------------------
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

    // Método que carga todas las texturas del juego
    private void cargarTexturas() {
        bg = new Texture("gBg.png");
        pauseButton = new Texture("buttons/unpressed/pauseButton.png");
        oveArr = new Texture("ovejaSprite.png");
        oveIzq = new Texture("ovejaSprite2.png");
        oveAb = new Texture("ovejaSprite3.png");
        oveDer = new Texture("ovejaSprite4.png");
    }

    @Override
    public void render(float delta) {

        borrarPantalla(0, 0, 0);
        batch.setProjectionMatrix(camara.combined);


        //----------------------------------------------------------------------------------------------

        //----------------------------------------------------------------------------------------------

        if (estado == EstadoJuego.JUGANDO) {
            salida += Gdx.graphics.getDeltaTime();
            tiempo += Gdx.graphics.getDeltaTime();
        }

            batch.begin();
            batch.draw(bg, 0, 0);

            for (int i = 0; i < arrOvejas.size; i++) {
                if (tiempo <= 60.0) {
                    if (salida <= 5) {
                        arrOvejas.get(i).render(batch);
                    } else {
                        salida = 0;
                    }
                }
                else{
                    arrOvejas.get(i).render(batch);
                    }

            }


            batch.end();



        escenaJuego.draw();


        if (estado == EstadoJuego.PAUSADO) {
            escenaPausa.draw();
        }

        if(pref.getBoolean("musicOn")){
            if(estado == EstadoJuego.JUGANDO){
                juego.playGameMusic();
            }else{
                juego.pauseGameMusic();
            }

        }
        if(!pref.getBoolean("musicOn")){
            juego.pauseGameMusic();
        }
            eliminarOveja();
    }



    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        escenaJuego.dispose();
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


            Texture opaque = new Texture("opaque.png");
            TextureRegionDrawable trdOpaq = new TextureRegionDrawable(
                    new TextureRegion(opaque));
            Image op = new Image(trdOpaq);
            op.setPosition(0,0);
            this.addActor(op);


            Texture pauseRectangle = new Texture("pauseRectangle.png");
            TextureRegionDrawable trdRect = new TextureRegionDrawable(
                    new TextureRegion(pauseRectangle));
            Image rectangle = new Image(trdRect);
            rectangle.setPosition(175,769);
            this.addActor(rectangle);

            Texture pauseText = new Texture("pauseText.png");
            TextureRegionDrawable trdPText = new TextureRegionDrawable(
                    new TextureRegion(pauseText));
            Image pauseT = new Image(trdPText);
            pauseT.setPosition(338,1319);
            this.addActor(pauseT);

            Texture continueText = new Texture("continueText.png");
            TextureRegionDrawable trdCText = new TextureRegionDrawable(
                    new TextureRegion(continueText)
            );
            Image continueT = new Image(trdCText);
            continueT.setPosition(327,879);
            this.addActor(continueT);

            Texture homeText = new Texture("homeText.png");
            TextureRegionDrawable trdHText = new TextureRegionDrawable(
                    new TextureRegion(homeText)
            );
            Image continueH = new Image(trdHText);
            continueH.setPosition(590,879);
            this.addActor(continueH);

            continueButton = new Texture("buttons/unpressed/continueButton.png");
            TextureRegionDrawable trdContinue = new TextureRegionDrawable(
                    new TextureRegion(continueButton));
            ImageButton btnContinue = new ImageButton(trdContinue);
            btnContinue.setPosition(294,963);
            btnContinue.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //Cambio el estado de juego a JUGANDO y regreso el poder a la escenaJuego
                    estado = EstadoJuego.JUGANDO;
                    juego.playGameMusic();
                    Gdx.input.setInputProcessor(escenaJuego);
                }
            });
            this.addActor(btnContinue);

            homeButton = new Texture("buttons/unpressed/homeButton.png");
            TextureRegionDrawable trdHome = new TextureRegionDrawable(
                    new TextureRegion(homeButton));
            ImageButton homeBtn = new ImageButton(trdHome);
            homeBtn.setPosition(583,963);
            homeBtn.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al menú
                    juego.setScreen(new MenuScreen(juego));
                    juego.stopGameMusic();
                }
            });
            this.addActor(homeBtn);


        }
    }

    //----------------------------------------------------------------------------------------------
}