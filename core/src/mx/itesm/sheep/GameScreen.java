package mx.itesm.sheep;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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


    float totalTime = 1 * 60;

    private Texture continueButton;

    private Texture oveArr;
    private Texture oveIzq;
    private Texture oveAb;
    private Texture oveDer;

    private BitmapFont font;

    private EstadoJuego estado;

    // Escena "pop-up" cuándo se presiona el botón de pausa
    private EscenaPausa escenaPausa;
    private Texture time;

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
        font = new BitmapFont(Gdx.files.internal("Intro.fnt"));
    }

    private void crearEscenaJuego() {

        escenaJuego = new Stage(vista);

        // Botón de pausa --------------------------------------------------------------------------

        Texture pressedPauseButton = new Texture("buttons/pressed/pressedPauseButton.png");
        TextureRegionDrawable trdPausepr = new TextureRegionDrawable(new TextureRegion(pressedPauseButton));

        TextureRegionDrawable trdPause = new
                TextureRegionDrawable(new TextureRegion(pauseButton));
        ImageButton imPause = new ImageButton(trdPause, trdPausepr);
        imPause.setPosition(461, 1734);
        escenaJuego.addActor(imPause);

        imPause.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                estado = EstadoJuego.PAUSADO;
                escenaPausa = new EscenaPausa(vista,batch);
                detenerOveja(true);
                Gdx.input.setInputProcessor(escenaPausa);

            }
        } );

        // Tomar y arrastrar ovejas ----------------------------------------------------------------

        escenaJuego.addListener(new DragListener(){
            @Override
            public void dragStart(InputEvent event, float x, float y, int pointer) {
                super.dragStart(event, x, y, pointer);
                for (Oveja oveja: arrOvejas){
                    if (!cordenadasCorral(x,y,oveja.getColor()) && !oveja.isEnLlamas()){
                        if (oveja.comparar(x,y)){
                            ovejaMoviendo = oveja;
                            ovejaMoviendo.setEstado(Oveja.Estado.MOVIENDO);
                            Gdx.app.log("dragStart", "Inicia movimeinto");
                            break;
                        }
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
                Gdx.app.log("drag", "x = " +x + ", y = " +y);
            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer) {
                super.dragStop(event, x, y, pointer);
                if(ovejaMoviendo == null){
                    return;
                }else{
                    // verificar si está en el corral
                    if(cordenadasCorral(x,y,ovejaMoviendo.getColor())){
                        ovejaMoviendo.setEstado(ovejaMoviendo.getEstadoOriginal());
                        ovejaMoviendo = null;
                    }else{
                        Gdx.app.log("corral", "Corral incorrecto");
                        ovejaMoviendo.setEstado(Oveja.Estado.BOOM);
                        ovejaMoviendo = null;
                    }
                }
            }

        });

    }

    public boolean cordenadasCorral(float xP, float yP, String color) {
        if ((xP >= 0 && xP <= 410 && yP >= 110 && yP <= 730 && color.equals("ROJO")) ||
                (xP >= 670 && xP <= 1080 && yP >= 110 && yP <= 730 && color.equals("AZUL")) ||
                (xP >= 0 && xP <= 410 && yP >= 1104 && yP <= 1730 && color.equals("MORADO")) ||
                (xP >= 670 && xP <= 1080 && yP >= 1104 && yP <= 1730 && color.equals("AMARILLO"))){
            return true;
        }
        return false;
    }

    private void detenerOveja(boolean stop) {
        if (stop){
            for (Oveja oveja: arrOvejas){
                oveja.setEstado(Oveja.Estado.STOP);
            }
        }else{
            for (Oveja oveja: arrOvejas){
                oveja.setEstado(Oveja.Estado.CONTINUAR);
            }
        }
    }

    // Método que carga las ovejas en el juego -----------------------------------------------------
    private void cargarOvejas(){
        //Llenar arreglo Ovejas
        arrOvejas = new Array<Oveja>(cantOve);
        Oveja ove;

        for (int i = 0; i < cantOve; i++){
            int random = (int) (Math.random()*4)+1;

            if (random == 1){
                ove = new Oveja(oveArr, Oveja.Estado.ARRIBA, "ROJO");
                arrOvejas.add(ove);
            }else if (random == 2){
                ove = new Oveja(oveAb, Oveja.Estado.ABAJO, "AZUL");
                arrOvejas.add(ove);
            }else if (random == 3){
                ove = new Oveja(oveIzq, Oveja.Estado.IZQUIERDA, "MORADO");
                arrOvejas.add(ove);
            }else{
                ove = new Oveja(oveDer, Oveja.Estado.DERECHA, "AMARILLO");
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
        oveArr = new Texture("sheep_down.png");
        oveIzq = new Texture("sheep_right.png");
        oveAb = new Texture("sheep_up.png");
        oveDer = new Texture("sheep_left.png");
        time = new Texture("time.png");
    }

    @Override
    public void render(float delta) {

        borrarPantalla(0, 0, 0);
        batch.setProjectionMatrix(camara.combined);

        float deltaTime = Gdx.graphics.getDeltaTime(); //You might prefer getRawDeltaTime()

        if(estado == EstadoJuego.JUGANDO){
            if(totalTime>=1) totalTime -= deltaTime;
        }


        int minutes = ((int)totalTime) / 60;
        int seconds = ((int)totalTime) % 60;


        if (estado == EstadoJuego.JUGANDO) {
            salida += Gdx.graphics.getDeltaTime();
            tiempo += Gdx.graphics.getDeltaTime();
        }

            batch.begin();
            batch.draw(bg, 0, 0);

            batch.draw(time,680,1814);
            if(seconds>=10){
                font.draw(batch,Integer.toString(minutes)+ ":"+ Integer.toString(seconds),755,1888);
            }else{
                font.draw(batch,Integer.toString(minutes)+ ":0"+ Integer.toString(seconds),755,1888);
            }
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
        PAUSADO,
        PERDIDO,
        GANADO
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
            rectangle.setPosition(47,489);
            this.addActor(rectangle);

            Texture pauseText = new Texture("pauseText.png");
            TextureRegionDrawable trdPText = new TextureRegionDrawable(
                    new TextureRegion(pauseText));
            Image pauseT = new Image(trdPText);
            pauseT.setPosition(270,1399);
            this.addActor(pauseT);


            Texture pressedContinueButton = new Texture("buttons/pressed/pressedContinueButton.png");
            TextureRegionDrawable trdContinuepr = new TextureRegionDrawable(
                    new TextureRegion(pressedContinueButton));
            continueButton = new Texture("buttons/unpressed/continueButton.png");
            TextureRegionDrawable trdContinue = new TextureRegionDrawable(
                    new TextureRegion(continueButton));
            ImageButton btnContinue = new ImageButton(trdContinue,trdContinuepr);
            btnContinue.setPosition(383,968);
            btnContinue.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //Cambio el estado de juego a JUGANDO y regreso el poder a la escenaJuego
                    estado = EstadoJuego.JUGANDO;
                    detenerOveja(false);
                    juego.playGameMusic();
                    Gdx.input.setInputProcessor(escenaJuego);
                }
            });
            this.addActor(btnContinue);


            Texture pressedHomeButton = new Texture("buttons/pressed/pressedLevelsButton.png");
            TextureRegionDrawable trdHomepr = new TextureRegionDrawable(new
                    TextureRegion(pressedHomeButton));
            homeButton = new Texture("buttons/unpressed/levelsButton.png");
            TextureRegionDrawable trdHome = new TextureRegionDrawable(
                    new TextureRegion(homeButton));
            ImageButton homeBtn = new ImageButton(trdHome, trdHomepr);
            homeBtn.setPosition(285,695);
            homeBtn.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al menú
                    juego.setScreen(new MenuScreen(juego));
                    juego.stopGameMusic();
                }
            });
            this.addActor(homeBtn);

            Texture pressedRestartButton = new Texture("buttons/pressed/pressedRestartButton.png");
            TextureRegionDrawable trdRestartpr =  new TextureRegionDrawable(new
                    TextureRegion(pressedRestartButton));
            Texture restartButton = new Texture("buttons/unpressed/restartButton.png");
            TextureRegionDrawable trdRestart = new TextureRegionDrawable(new
                    TextureRegion(restartButton));
            ImageButton restartBtn = new ImageButton(trdRestart, trdRestartpr);
            restartBtn.setPosition(586,695);
            restartBtn.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al menú
                    juego.setScreen(new GameScreen(juego));
                    juego.stopGameMusic();
                    juego.playGameMusic();
                }
            });
            this.addActor(restartBtn);


        }
    }

    //----------------------------------------------------------------------------------------------
}