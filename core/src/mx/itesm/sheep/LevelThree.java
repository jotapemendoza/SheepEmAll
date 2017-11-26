package mx.itesm.sheep;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Andreu on 11/11/17.
 */

public class LevelThree extends ScreenTemplate {

    private final SheepEm sheepEm;

    private ImageButton noMusicBtn;
    private ImageButton musicBtn;
    private ImageButton fxBtn;
    private ImageButton noFxBtn;


    // Texturas/Parte Gráfica ----------------------------------------------------------------------
    private Texture continueButton;
    private Texture pauseButton;
    private Texture homeButton;
    private Texture background;
    private Stage escenaJuego;
    private Texture time;
    private Texture life;
    private Texture life_lost;
    private Texture alienShip;
    private BitmapFont font;

    // Texturas de colores
    private Texture oveArrBlue;
    private Texture oveArrRed;
    private Texture oveArrWhite;
    private Texture oveArrYellow;
    private Texture oveArrMovBlue;
    private Texture oveArrMovRed;
    private Texture oveArrMovWhite;
    private Texture oveArrMovYellow;

    private Texture oveAbBlue;
    private Texture oveAbMovBlue;
    private Texture oveAbRed;
    private Texture oveAbMovRed;
    private Texture oveAbWhite;
    private Texture oveAbMovWhite;
    private Texture oveAbYellow;
    private Texture oveAbMovYellow;

    private Texture oveIzqBlue;
    private Texture oveIzqMovBlue;
    private Texture oveIzqRed;
    private Texture oveIzqMovRed;
    private Texture oveIzqWhite;
    private Texture oveIzqMovWhite;
    private Texture oveIzqYellow;
    private Texture oveIzqMovYellow;

    private Texture oveEstaticBlue;
    private Texture oveEstaticRed;
    private Texture oveEstaticWhite;
    private Texture oveEstaticYellow;
    private Texture oveEstaticRainbow;

    private Texture oveDerBlue;
    private Texture oveDerMovBlue;
    private Texture oveDerRed;
    private Texture oveDerMovRed;
    private Texture oveDerWhite;
    private Texture oveDerMovWhite;
    private Texture oveDerYellow;
    private Texture oveDerMovYellow;

    private Texture oveArrRainbow;
    private Texture oveArrMovRainbow;
    private Texture oveAbRainbow;
    private Texture oveAbMovRainbow;
    private Texture oveIzqRainbow;
    private Texture oveIzqMovRainbow;
    private Texture oveDerRainbow;
    private Texture oveDerMovRainbow;

    private Texture barn;
    private Texture cr;
    private Texture barn_shadow;

    // Texturas alien colores
    private Texture oveAlienArrWhite;
    private Texture oveAlienArrMovWhite;
    private Texture oveAlienArrBlue;
    private Texture oveAlienArrMovBlue;
    private Texture oveAlienArrRed;
    private Texture oveAlienArrMovRed;
    private Texture oveAlienArrYellow;
    private Texture oveAlienArrMovYellow;
    private Texture oveAlienAbWhite;
    private Texture oveAlienAbMovWhite;
    private Texture oveAlienAbBlue;
    private Texture oveAlienAbMovBlue;
    private Texture oveAlienAbRed;
    private Texture oveAlienAbMovRed;
    private Texture oveAlienAbYellow;
    private Texture oveAlienAbMovYellow;
    private Texture oveAlienIzqWhite;
    private Texture oveAlienIzqMovWhite;
    private Texture oveAlienIzqBlue;
    private Texture oveAlienIzqMovBlue;
    private Texture oveAlienIzqRed;
    private Texture oveAlienIzqMovRed;
    private Texture oveAlienIzqYellow;
    private Texture oveAlienIzqMovYellow;
    private Texture oveAlienDerWhite;
    private Texture oveAlienDerMovWhite;
    private Texture oveAlienDerBlue;
    private Texture oveAlienDerMovBlue;
    private Texture oveAlienDerRed;
    private Texture oveAlienDerMovRed;
    private Texture oveAlienDerYellow;
    private Texture oveAlienDerMovYellow;

    private Boolean played = false;

    // Arreglo de ovejas ---------------------------------------------------------------------------
    private Array<Sheep> arrOvejas;
    private Sheep ovejaMoviendo = null;
    private int ovejaMovX;
    private int ovejaMovY;
    private final int cantOve = 21;
    private int contOvejas = 0;
    private Sheep OveAl;
    private String arrColores[] = {"WHITE","BLUE","RED","YELLOW","RAINBOW"};
    private String arrTipos[] = {"NORMAL","ALIEN","RAINBOW"};

    private float salida;
    private float velocidadOve = 1.0f;
    private int lifes;

    float totalTime = 1 * 60;

    private EstadoJuego estado;

    // AlienShip -----------------------------------------------------------------------------------
    private AlienShip aS;
    private float moverX = 0;
    private float moverY = 0;

    // Escenas -------------------------------------------------------------------------------------
    private lostScene lostScene;
    private winScene winScene;
    private pauseScene pauseScene;


    private float tiempo;
    private Music sheep;
    private float sheepTimer;
    private boolean estaEnNave = false;
    private boolean drawAS;

    private float undrawTimer;
    private boolean apperAS;


    public LevelThree(SheepEm sheepEm){
        this.sheepEm = sheepEm;
    }

    @Override
    public void show() {
        loadTextures();
        loadSheep();
        createGameScene();
        font = new BitmapFont(Gdx.files.internal("Intro.fnt"));
        lostScene = new lostScene(view,batch);
        winScene = new winScene(view,batch);
        estado = EstadoJuego.JUGANDO;
        Gdx.input.setInputProcessor(escenaJuego);
        sheep = Gdx.audio.newMusic(Gdx.files.internal("SFX/sheep_sound.mp3"));
        lifes = 3;
        sheepTimer=1.85f;
        drawAS = false;
        apperAS = true;
    }

    private void createGameScene() {

        escenaJuego = new Stage(view);

        // Crear nave
        aS = new AlienShip(alienShip, AlienShip.Estado.INICIO);

        escenaJuego.addListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode==Input.Keys.BACK){
                    //setScreen
                }
                return true;
            }
        });

        // Botón de pausa --------------------------------------------------------------------------

        Texture pressedPauseButton = new Texture("Buttons/pressed/pressedPauseButton.png");
        TextureRegionDrawable trdPausepr = new TextureRegionDrawable(new TextureRegion(pressedPauseButton));
        TextureRegionDrawable trdPause = new TextureRegionDrawable(new TextureRegion(pauseButton));
        ImageButton imPause = new ImageButton(trdPause, trdPausepr);
        imPause.setPosition(887, 1743);
        escenaJuego.addActor(imPause);

        imPause.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                estado = EstadoJuego.PAUSADO;
                pauseScene = new pauseScene(view,batch);
                detenerOveja(true);
                aS.setEstado(AlienShip.Estado.PAUSADO);
                Gdx.input.setInputProcessor(pauseScene);
            }
        } );

        // Tomar y arrastrar ovejas ----------------------------------------------------------------

        escenaJuego.addListener(new DragListener(){
            @Override
            public void dragStart(InputEvent event, float x, float y, int pointer) {
                super.dragStart(event, x, y, pointer);
                for (Sheep sheep : arrOvejas){
                    if (!cordenadasCorral(x,y, sheep.getColor()) && !sheep.isEnLlamas()){
                        if (sheep.comparar(x,y)){
                            ovejaMoviendo = sheep;
                            ovejaMovX = (int) ovejaMoviendo.getx();
                            ovejaMovY = (int) ovejaMoviendo.gety();
                            ovejaMoviendo.setEstado(Sheep.Estado.MOVIENDO);
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
                ovejaMoviendo.setX(x - ovejaMoviendo.getAncho()/2);
                ovejaMoviendo.setY(y - ovejaMoviendo.getAlto()/2);

                if(pref.getBoolean("fxOn")){
                    sheep.setVolume(0.8f);
                    sheep.play();
                }
                Gdx.app.log("drag", "x = " +x + ", y = " +y);
            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer) {
                super.dragStop(event, x, y, pointer);
                if(ovejaMoviendo != null){
                    // verificar si esta en el corral nave alien
                    if (ovejaMoviendo.cordenadasCorralAlien(x,y,ovejaMoviendo.getTipo(),aS)){
                        ovejaMoviendo.setEstado(Sheep.Estado.BORRAR);
                        contOvejas++;
                        aS.setEstado(AlienShip.Estado.DERROTA);
                        Gdx.app.log("oveja","en corral: " + contOvejas);
                        ovejaMoviendo = null;
                        drawAS = true;
                    }
                    // verificar si esta en el corral
                    else{
                        if(cordenadasCorral(x,y,ovejaMoviendo.getColor())){
                            if (ovejaMoviendo.getColor().equals("RAINBOW")){
                                ovejaMoviendo.setEstado(ovejaMoviendo.getEstadoOriginal());
                                lifes++;
                                contOvejas++;
                                ovejaMoviendo = null;
                            }
                            else if (ovejaMoviendo.getTipo().equals("ALIEN")){
                                //ovejaMoviendo.setEstado(Sheep.Estado.BOOM);
                                lifes--;
                                aS.setEstado(AlienShip.Estado.DERROTA);
                                Gdx.app.log("oveja","en corral: " + contOvejas);
                                ovejaMoviendo = null;
                            }else {
                                ovejaMoviendo.setEstado(ovejaMoviendo.getEstadoOriginal());
                                contOvejas++;
                                Gdx.app.log("oveja","en corral: " + contOvejas);
                                ovejaMoviendo = null;
                            }
                        }else {
                            if (!cordenadasLineales(x, y) && ovejaMoviendo.getTipo().equals("ALIEN")){
                                Gdx.app.log("corral", "Corral incorrecto");
                                //ovejaMoviendo.setEstado(Sheep.Estado.BOOM);
                                lifes--;
                                aS.setEstado(AlienShip.Estado.DERROTA);
                                ovejaMoviendo = null;
                            }
                            else if (!cordenadasLineales(x, y)) {
                                Gdx.app.log("corral", "Corral incorrecto");
                                ovejaMoviendo.setEstado(Sheep.Estado.BOOM);
                                lifes--;
                                ovejaMoviendo = null;
                            } else {
                                ovejaMoviendo.setSeMovio(false);
                                ovejaMoviendo.setEstado(ovejaMoviendo.getEstadoOriginal());
                                ovejaMoviendo.setX(ovejaMovX);
                                ovejaMoviendo.setY(ovejaMovY);
                                ovejaMoviendo = null;
                            }
                        }
                    }
                }
            }
        });
    }

    // Validar corral correcto ---------------------------------------------------------------------
    public boolean cordenadasCorral(float xP, float yP, String color) {
        if ((xP >= 0 && xP <= 405 && yP >= 110 && yP <= 720
                && (color.equals("RED") || color.equals("RAINBOW"))) ||
                (xP >= 677 && xP <= 1080 && yP >= 110 && yP <= 720
                        && (color.equals("BLUE") || color.equals("RAINBOW"))) ||
                (xP >= 0 && xP <= 405 && yP >= 1105 && yP <= 1662
                        && (color.equals("WHITE") || color.equals("RAINBOW"))) ||
                (xP >= 677 && xP <= 1080 && yP >= 1105 && yP <= 1662
                        && (color.equals("YELLOW") || color.equals("RAINBOW")))){
            return true;
        }
        return false;
    }

    // Validar camino de ovejas --------------------------------------------------------------------
    public boolean cordenadasLineales(float xP, float yP){
        if ( (xP >= 406 && xP <= 676 && yP >= 0 && yP <= 1920) ||
                (xP >= 0 && xP <= 1080 && yP >= 721 && yP <= 1104) ){
            return true;
        }
        return false;
    }

    // Detener ovejas en el sheepEm ------------------------------------------------------------------
    private void detenerOveja(boolean stop) {
        if (stop){
            for (Sheep sheep : arrOvejas){
                sheep.setEstado(Sheep.Estado.STOP);
            }
        }else{
            for (Sheep sheep : arrOvejas){
                sheep.setEstado(Sheep.Estado.CONTINUAR);
            }
        }
    }

    // Método que carga las ovejas en el sheepEm -----------------------------------------------------
    private void loadSheep() {
        //Llenar arreglo Ovejas
        if (arrOvejas == null) {
            arrOvejas = new Array<Sheep>(cantOve);

            int randomAlien = (int) (Math.random() * 4) + 1;
            int randomAlienColor = (int) (Math.random() * 4) + 1;
            Sheep Alien;

            if (randomAlien == 1){
                switch (randomAlienColor){
                    case 1:
                        Alien = new Sheep(oveAlienArrWhite, oveAlienArrMovWhite, oveAlienArrWhite,
                                Sheep.Estado.ARRIBA, arrColores[0], arrTipos[1]);
                        arrOvejas.add(Alien);
                        break;
                    case 2:
                        Alien = new Sheep(oveAlienArrBlue, oveAlienArrMovBlue, oveAlienArrBlue,
                                Sheep.Estado.ARRIBA, arrColores[1], arrTipos[1]);
                        arrOvejas.add(Alien);
                        break;
                    case 3:
                        Alien = new Sheep(oveAlienArrRed, oveAlienArrMovRed, oveAlienArrRed,
                                Sheep.Estado.ARRIBA, arrColores[2], arrTipos[1]);
                        arrOvejas.add(Alien);
                        break;
                    case 4:
                        Alien = new Sheep(oveAlienArrYellow, oveAlienArrMovYellow, oveAlienArrYellow,
                                Sheep.Estado.ARRIBA, arrColores[3], arrTipos[1]);
                        arrOvejas.add(Alien);
                        break;
                }
            }else if (randomAlien == 2){
                switch (randomAlienColor){
                    case 1:
                        Alien = new Sheep(oveAlienAbWhite, oveAlienAbMovWhite, oveAlienArrWhite,
                                Sheep.Estado.ABAJO, arrColores[0], arrTipos[1]);
                        arrOvejas.add(Alien);
                        break;
                    case 2:
                        Alien = new Sheep(oveAlienAbBlue, oveAlienAbMovBlue, oveAlienArrBlue,
                                Sheep.Estado.ABAJO, arrColores[1], arrTipos[1]);
                        arrOvejas.add(Alien);
                        break;
                    case 3:
                        Alien = new Sheep(oveAlienAbRed, oveAlienAbMovRed, oveAlienArrRed,
                                Sheep.Estado.ABAJO, arrColores[2], arrTipos[1]);
                        arrOvejas.add(Alien);
                        break;
                    case 4:
                        Alien = new Sheep(oveAlienAbYellow, oveAlienAbMovYellow, oveAlienArrYellow,
                                Sheep.Estado.ABAJO, arrColores[3], arrTipos[1]);
                        arrOvejas.add(Alien);
                        break;
                }
            }else if (randomAlien == 3){
                switch (randomAlienColor){
                    case 1:
                        Alien = new Sheep(oveAlienIzqWhite, oveAlienIzqMovWhite, oveAlienArrWhite,
                                Sheep.Estado.IZQUIERDA, arrColores[0], arrTipos[1]);
                        arrOvejas.add(Alien);
                        break;
                    case 2:
                        Alien = new Sheep(oveAlienIzqBlue, oveAlienIzqMovBlue, oveAlienArrBlue,
                                Sheep.Estado.IZQUIERDA, arrColores[1], arrTipos[1]);
                        arrOvejas.add(Alien);
                        break;
                    case 3:
                        Alien = new Sheep(oveAlienIzqRed, oveAlienIzqMovRed, oveAlienArrRed,
                                Sheep.Estado.IZQUIERDA, arrColores[2], arrTipos[1]);
                        arrOvejas.add(Alien);
                        break;
                    case 4:
                        Alien = new Sheep(oveAlienIzqYellow, oveAlienIzqMovYellow, oveAlienArrYellow,
                                Sheep.Estado.IZQUIERDA, arrColores[3], arrTipos[1]);
                        arrOvejas.add(Alien);
                        break;
                }
            }else if (randomAlien == 4){
                switch (randomAlienColor){
                    case 1:
                        Alien = new Sheep(oveAlienDerWhite, oveAlienDerMovWhite, oveAlienArrWhite,
                                Sheep.Estado.DERECHA, arrColores[0], arrTipos[1]);
                        arrOvejas.add(Alien);
                        break;
                    case 2:
                        Alien = new Sheep(oveAlienDerBlue, oveAlienDerMovBlue, oveAlienArrBlue,
                                Sheep.Estado.DERECHA, arrColores[1], arrTipos[1]);
                        arrOvejas.add(Alien);
                        break;
                    case 3:
                        Alien = new Sheep(oveAlienDerRed, oveAlienDerMovRed, oveAlienArrRed,
                                Sheep.Estado.DERECHA, arrColores[2], arrTipos[1]);
                        arrOvejas.add(Alien);
                        break;
                    case 4:
                        Alien = new Sheep(oveAlienDerYellow, oveAlienDerMovYellow, oveAlienArrYellow,
                                Sheep.Estado.DERECHA, arrColores[3], arrTipos[1]);
                        arrOvejas.add(Alien);
                        break;
                }
            }

            int randomRainbow = (int) (Math.random() * 4) + 1;
            Sheep rainbow;

            switch (randomRainbow){
                case 1:
                    rainbow = new Sheep(oveArrRainbow, oveArrMovRainbow, oveEstaticRainbow,
                            Sheep.Estado.ARRIBA, arrColores[4], arrTipos[2]);
                    arrOvejas.add(rainbow);
                    break;
                case 2:
                    rainbow = new Sheep(oveAbRainbow, oveAbMovRainbow, oveEstaticRainbow,
                            Sheep.Estado.ABAJO, arrColores[4], arrTipos[2]);
                    arrOvejas.add(rainbow);
                    break;
                case 3:
                    rainbow = new Sheep(oveIzqRainbow, oveIzqMovRainbow, oveEstaticRainbow,
                            Sheep.Estado.IZQUIERDA, arrColores[4], arrTipos[2]);
                    arrOvejas.add(rainbow);
                    break;
                case 4:
                    rainbow = new Sheep(oveDerRainbow, oveDerMovRainbow, oveEstaticRainbow,
                            Sheep.Estado.DERECHA, arrColores[4], arrTipos[2]);
                    arrOvejas.add(rainbow);
                    break;
            }

        }
        // Llenar arreglo de ovejas por tiempo
        if (arrOvejas.size < cantOve) {

            Sheep ove;

            for (int i = 2; i < 3; i++) {
                int random = (int) (Math.random() * 4) + 1;
                int randomColor = (int) (Math.random() * 4) + 1;

                if (random == 1){
                    switch (randomColor){
                        case 1:
                            ove = new Sheep(oveArrWhite, oveArrMovWhite, oveEstaticWhite,
                                    Sheep.Estado.ARRIBA, arrColores[0], arrTipos[0]);
                            arrOvejas.add(ove);
                            break;
                        case 2:
                            ove = new Sheep(oveArrBlue, oveArrMovBlue, oveEstaticBlue,
                                    Sheep.Estado.ARRIBA, arrColores[1], arrTipos[0]);
                            arrOvejas.add(ove);
                            break;
                        case 3:
                            ove = new Sheep(oveArrRed, oveArrMovRed, oveEstaticRed,
                                    Sheep.Estado.ARRIBA, arrColores[2], arrTipos[0]);
                            arrOvejas.add(ove);
                            break;
                        case 4:
                            ove = new Sheep(oveArrYellow, oveArrMovYellow, oveEstaticYellow,
                                    Sheep.Estado.ARRIBA, arrColores[3], arrTipos[0]);
                            arrOvejas.add(ove);
                            break;
                    }
                }else if (random == 2){
                    switch (randomColor){
                        case 1:
                            ove = new Sheep(oveAbWhite, oveAbMovWhite, oveEstaticWhite,
                                    Sheep.Estado.ABAJO, arrColores[0], arrTipos[0]);
                            arrOvejas.add(ove);
                            break;
                        case 2:
                            ove = new Sheep(oveAbBlue, oveAbMovBlue, oveEstaticBlue,
                                    Sheep.Estado.ABAJO, arrColores[1], arrTipos[0]);
                            arrOvejas.add(ove);
                            break;
                        case 3:
                            ove = new Sheep(oveAbRed, oveAbMovRed, oveEstaticRed,
                                    Sheep.Estado.ABAJO, arrColores[2], arrTipos[0]);
                            arrOvejas.add(ove);
                            break;
                        case 4:
                            ove = new Sheep(oveAbYellow, oveAbMovYellow, oveEstaticYellow,
                                    Sheep.Estado.ABAJO, arrColores[3], arrTipos[0]);
                            arrOvejas.add(ove);
                            break;
                    }
                }else if (random == 3){
                    switch (randomColor){
                        case 1:
                            ove = new Sheep(oveIzqWhite, oveIzqMovWhite, oveEstaticWhite,
                                    Sheep.Estado.IZQUIERDA, arrColores[0], arrTipos[0]);
                            arrOvejas.add(ove);
                            break;
                        case 2:
                            ove = new Sheep(oveIzqBlue, oveIzqMovBlue, oveEstaticBlue,
                                    Sheep.Estado.IZQUIERDA, arrColores[1], arrTipos[0]);
                            arrOvejas.add(ove);
                            break;
                        case 3:
                            ove = new Sheep(oveIzqRed, oveIzqMovRed, oveEstaticRed,
                                    Sheep.Estado.IZQUIERDA, arrColores[2], arrTipos[0]);
                            arrOvejas.add(ove);
                            break;
                        case 4:
                            ove = new Sheep(oveIzqYellow, oveIzqMovYellow, oveEstaticYellow,
                                    Sheep.Estado.IZQUIERDA, arrColores[3], arrTipos[0]);
                            arrOvejas.add(ove);
                            break;
                    }
                }else if (random == 4){
                    switch (randomColor){
                        case 1:
                            ove = new Sheep(oveDerWhite, oveDerMovWhite, oveEstaticWhite,
                                    Sheep.Estado.DERECHA, arrColores[0], arrTipos[0]);
                            arrOvejas.add(ove);
                            break;
                        case 2:
                            ove = new Sheep(oveDerBlue, oveDerMovBlue, oveEstaticBlue,
                                    Sheep.Estado.DERECHA, arrColores[1], arrTipos[0]);
                            arrOvejas.add(ove);
                            break;
                        case 3:
                            ove = new Sheep(oveDerRed, oveDerMovRed, oveEstaticRed,
                                    Sheep.Estado.DERECHA, arrColores[2], arrTipos[0]);
                            arrOvejas.add(ove);
                            break;
                        case 4:
                            ove = new Sheep(oveDerYellow, oveDerMovYellow, oveEstaticYellow,
                                    Sheep.Estado.DERECHA, arrColores[3], arrTipos[0]);
                            arrOvejas.add(ove);
                            break;
                    }
                }
            }
        }
    }

    // Método que elimina las ovejas en el sheepEm ---------------------------------------------------
    private void eliminarOveja(){
        for (int i = 0; i < arrOvejas.size; i++){
            if (arrOvejas.get(i).getTipo().equals("ALIEN")){
                if (estaEnNave){
                    arrOvejas.removeIndex(i);
                    System.out.println("ovejas disponibles: " + arrOvejas.size);
                    break;
                }

                if (arrOvejas.get(i).getTipo().equals("ALIEN")
                        && arrOvejas.get(i).getEstado().equals(Sheep.Estado.ARRIBA)
                        && arrOvejas.get(i).gety() <= 0) {
                    arrOvejas.removeIndex(i);
                    System.out.println("ovejas disponibles: " + arrOvejas.size);
                    velocidadOve += 1;
                    aS.setEstado(AlienShip.Estado.DERROTA);
                    break;
                }

                if (arrOvejas.get(i).getTipo().equals("ALIEN")
                        && arrOvejas.get(i).getEstado().equals(Sheep.Estado.ABAJO)
                        && arrOvejas.get(i).gety() >= 1920) {
                    arrOvejas.removeIndex(i);
                    System.out.println("ovejas disponibles: " + arrOvejas.size);
                    velocidadOve += 1;
                    aS.setEstado(AlienShip.Estado.DERROTA);
                    break;
                }

                if (arrOvejas.get(i).getTipo().equals("ALIEN")
                        && arrOvejas.get(i).getEstado().equals(Sheep.Estado.IZQUIERDA)
                        && arrOvejas.get(i).gety() >= 1080) {
                    arrOvejas.removeIndex(i);
                    System.out.println("ovejas disponibles: " + arrOvejas.size);
                    velocidadOve += 1;
                    aS.setEstado(AlienShip.Estado.DERROTA);
                    break;
                }

                if (arrOvejas.get(i).getTipo().equals("ALIEN")
                        && arrOvejas.get(i).getEstado().equals(Sheep.Estado.DERECHA)
                        && arrOvejas.get(i).gety() <= 0) {
                    arrOvejas.removeIndex(i);
                    System.out.println("ovejas disponibles: " + arrOvejas.size);
                    velocidadOve += 1;
                    aS.setEstado(AlienShip.Estado.DERROTA);
                    break;
                }
            }
            if (arrOvejas.get(i).getEstado().equals(Sheep.Estado.ARRIBA)){
                if (arrOvejas.get(i).gety() <= 0){
                    arrOvejas.removeIndex(i);
                    System.out.println("ovejas disponibles: " + arrOvejas.size);
                    playLost();
                    lifes--;
                    break;
                }
            }
            if (arrOvejas.get(i).getEstado().equals(Sheep.Estado.ABAJO)){
                if (arrOvejas.get(i).gety() >= 1920){
                    arrOvejas.removeIndex(i);
                    System.out.println("ovejas disponibles: " + arrOvejas.size);
                    playLost();
                    lifes--;
                    break;
                }
            }
            if (arrOvejas.get(i).getEstado().equals(Sheep.Estado.IZQUIERDA)){
                if (arrOvejas.get(i).getx() >= 1080){
                    arrOvejas.removeIndex(i);
                    System.out.println("ovejas disponibles: " + arrOvejas.size);
                    playLost();
                    lifes--;
                    break;
                }
            }
            if (arrOvejas.get(i).getEstado().equals(Sheep.Estado.DERECHA)){
                if (arrOvejas.get(i).getx() <= 0){
                    arrOvejas.removeIndex(i);
                    System.out.println("ovejas disponibles: " + arrOvejas.size);
                    playLost();
                    lifes--;
                    break;
                }
            }
        }
    }

    private void playLost() {
        if(pref.getBoolean("fxOn")){
            sheepEm.lostSheep.play();
        }
    }

    // Método que carga todas las texturas del sheepEm ---------------------------------------------
    private void loadTextures() {
        background = new Texture("gBg_anocher.png");
        pauseButton = new Texture("Buttons/unpressed/pauseButton.png");
        time = new Texture("time.png");
        life = new Texture("life.png");
        life_lost = new Texture("life_lost.png");
        alienShip = new Texture("alienShip.png");

        barn = new Texture("night_barn.png");
        cr = new Texture("cr_night.png");
        barn_shadow = new Texture("shadow.png");

        //ovejas de colores
        oveArrBlue = new Texture("Sheep/Level 3/Blue/sheep_down_blue.png");
        oveArrMovBlue = new Texture("Sheep/Level 3/Blue/sheep_moving_down_blue.png");
        oveArrRed = new Texture("Sheep/Level 3/Red/sheep_down_red.png");
        oveArrMovRed = new Texture("Sheep/Level 3/Red/sheep_moving_down_red.png");
        oveArrWhite = new Texture("Sheep/Level 3/White/sheep_down_white.png");
        oveArrMovWhite = new Texture("Sheep/Level 3/White/sheep_moving_down_white.png");
        oveArrYellow = new Texture("Sheep/Level 3/Yellow/sheep_down_yellow.png");
        oveArrMovYellow = new Texture("Sheep/Level 3/Yellow/sheep_moving_down_yellow.png");

        oveAbBlue = new Texture("Sheep/Level 3/Blue/sheep_up_blue.png");
        oveAbMovBlue = new Texture("Sheep/Level 3/Blue/sheep_moving_up_blue.png");
        oveAbRed = new Texture("Sheep/Level 3/Red/sheep_up_red.png");
        oveAbMovRed = new Texture("Sheep/Level 3/Red/sheep_moving_up_red.png");
        oveAbWhite = new Texture("Sheep/Level 3/White/sheep_up_white.png");
        oveAbMovWhite = new Texture("Sheep/Level 3/White/sheep_moving_up_white.png");
        oveAbYellow = new Texture("Sheep/Level 3/Yellow/sheep_up_yellow.png");
        oveAbMovYellow = new Texture("Sheep/Level 3/Yellow/sheep_moving_up_yellow.png");

        oveIzqBlue = new Texture("Sheep/Level 3/Blue/sheep_left_blue.png");
        oveIzqMovBlue = new Texture("Sheep/Level 3/Blue/sheep_moving_left_blue.png");
        oveIzqRed = new Texture("Sheep/Level 3/Red/sheep_left_red.png");
        oveIzqMovRed = new Texture("Sheep/Level 3/Red/sheep_moving_left_red.png");
        oveIzqWhite = new Texture("Sheep/Level 3/White/sheep_left_white.png");
        oveIzqMovWhite = new Texture("Sheep/Level 3/White/sheep_moving_left_white.png");
        oveIzqYellow = new Texture("Sheep/Level 3/Yellow/sheep_left_yellow.png");
        oveIzqMovYellow = new Texture("Sheep/Level 3/Yellow/sheep_moving_left_yellow.png");

        oveDerBlue = new Texture("Sheep/Level 3/Blue/sheep_right_blue.png");
        oveDerMovBlue = new Texture("Sheep/Level 3/Blue/sheep_moving_right_blue.png");
        oveDerRed = new Texture("Sheep/Level 3/Red/sheep_right_red.png");
        oveDerMovRed = new Texture("Sheep/Level 3/Red/sheep_moving_right_red.png");
        oveDerWhite = new Texture("Sheep/Level 3/White/sheep_right_white.png");
        oveDerMovWhite = new  Texture("Sheep/Level 3/White/sheep_moving_right_white.png");
        oveDerYellow = new Texture("Sheep/Level 3/Yellow/sheep_right_yellow.png");
        oveDerMovYellow = new Texture("Sheep/Level 3/Yellow/sheep_moving_right_yellow.png");

        //ovejas alien de colores
        oveAlienArrWhite = new Texture("Sheep/Level 3/Alien/White/sheep_down_white.png");
        oveAlienArrMovWhite = new Texture("Sheep/Level 3/Alien/White/sheep_moving_down_white.png");
        oveAlienArrBlue = new Texture("Sheep/Level 3/Alien/Blue/sheep_down_blue.png");
        oveAlienArrMovBlue = new Texture("Sheep/Level 3/Alien/Blue/sheep_moving_down_blue.png");
        oveAlienArrRed = new Texture("Sheep/Level 3/Alien/Red/sheep_down_red.png");
        oveAlienArrMovRed = new Texture("Sheep/Level 3/Alien/Red/sheep_moving_down_red.png");
        oveAlienArrYellow = new Texture("Sheep/Level 3/Alien/Yellow/sheep_down_yellow.png");
        oveAlienArrMovYellow = new Texture("Sheep/Level 3/Alien/Yellow/sheep_moving_down_yellow.png");

        oveAlienAbWhite = new Texture("Sheep/Level 3/Alien/White/sheep_up_white.png");
        oveAlienAbMovWhite = new Texture("Sheep/Level 3/Alien/White/sheep_moving_up_white.png");
        oveAlienAbBlue = new Texture("Sheep/Level 3/Alien/Blue/sheep_up_blue.png");
        oveAlienAbMovBlue = new Texture("Sheep/Level 3/Alien/Blue/sheep_moving_up_blue.png");
        oveAlienAbRed = new Texture("Sheep/Level 3/Alien/Red/sheep_up_red.png");
        oveAlienAbMovRed = new Texture("Sheep/Level 3/Alien/Red/sheep_moving_up_red.png");
        oveAlienAbYellow = new Texture("Sheep/Level 3/Alien/Yellow/sheep_up_yellow.png");
        oveAlienAbMovYellow = new Texture("Sheep/Level 3/Alien/Yellow/sheep_moving_up_yellow.png");

        oveAlienIzqWhite = new Texture("Sheep/Level 3/Alien/White/sheep_left_white.png");
        oveAlienIzqMovWhite = new Texture("Sheep/Level 3/Alien/White/sheep_moving_left_white.png");
        oveAlienIzqBlue = new Texture("Sheep/Level 3/Alien/Blue/sheep_left_blue.png");
        oveAlienIzqMovBlue = new Texture("Sheep/Level 3/Alien/Blue/sheep_moving_left_blue.png");
        oveAlienIzqRed = new Texture("Sheep/Level 3/Alien/Red/sheep_left_red.png");
        oveAlienIzqMovRed = new Texture("Sheep/Level 3/Alien/Red/sheep_moving_left_red.png");
        oveAlienIzqYellow = new Texture("Sheep/Level 3/Alien/Yellow/sheep_left_yellow.png");
        oveAlienIzqMovYellow = new Texture("Sheep/Level 3/Alien/Yellow/sheep_moving_left_yellow.png");

        oveAlienDerWhite = new Texture("Sheep/Level 3/Alien/White/sheep_right_white.png");
        oveAlienDerMovWhite = new Texture("Sheep/Level 3/Alien/White/sheep_moving_right_white.png");
        oveAlienDerBlue = new Texture("Sheep/Level 3/Alien/Blue/sheep_right_blue.png");
        oveAlienDerMovBlue = new Texture("Sheep/Level 3/Alien/Blue/sheep_moving_right_blue.png");
        oveAlienDerRed = new Texture("Sheep/Level 3/Alien/Red/sheep_right_red.png");
        oveAlienDerMovRed = new Texture("Sheep/Level 3/Alien/Red/sheep_moving_right_red.png");
        oveAlienDerYellow = new Texture("Sheep/Level 3/Alien/Yellow/sheep_right_yellow.png");
        oveAlienDerMovYellow = new Texture("Sheep/Level 3/Alien/Yellow/sheep_moving_right_yellow.png");

        // oveja Rainbow
        oveArrRainbow = new Texture("Sheep/Level 2/Rainbow/rainbow_down.png");
        oveArrMovRainbow = new Texture("Sheep/Level 2/Rainbow/rainbow_moving_down.png");
        oveAbRainbow = new Texture("Sheep/Level 2/Rainbow/rainbow_up.png");
        oveAbMovRainbow = new Texture("Sheep/Level 2/Rainbow/rainbow_moving_up.png");
        oveIzqRainbow = new Texture("Sheep/Level 2/Rainbow/rainbow_left.png");
        oveIzqMovRainbow = new Texture("Sheep/Level 2/Rainbow/rainbow_moving_left.png");
        oveDerRainbow = new Texture("Sheep/Level 2/Rainbow/rainbow_right.png");
        oveDerMovRainbow = new Texture("Sheep/Level 2/Rainbow/rainbow_moving_right.png");

        // oveja estática
        oveEstaticBlue = new Texture("Sheep/Level 3/Blue/sheep_grazing.png");
        oveEstaticRed = new Texture("Sheep/Level 3/Red/sheep_grazing.png");
        oveEstaticWhite = new Texture("Sheep/Level 3/White/sheep_grazing.png");
        oveEstaticYellow = new Texture("Sheep/Level 3/Yellow/sheep_grazing.png");
        oveEstaticRainbow = new Texture("Sheep/Level 2/Rainbow/sheep_grazing.png");
    }



    @Override
    public void render(float delta) {

        Gdx.input.setCatchBackKey(true);


        clearScreen(0, 0, 0);
        batch.setProjectionMatrix(camera.combined);

        float deltaTime = Gdx.graphics.getDeltaTime(); //You might prefer getRawDeltaTime()

        if(estado == EstadoJuego.JUGANDO){
            if(totalTime>=1) totalTime -= deltaTime;
        }




        if (estado == EstadoJuego.JUGANDO) {
            salida += Gdx.graphics.getDeltaTime();
            tiempo += Gdx.graphics.getDeltaTime();
            sheepTimer -= Gdx.graphics.getDeltaTime();
            if(drawAS){
                undrawTimer+=Gdx.graphics.getDeltaTime();
            }
        }


        if(undrawTimer >= 3.5){
            apperAS = false;
        }

        if (sheepTimer<=0){
            loadSheep();
            sheepTimer = 2;
        }

        batch.begin();

        batch.draw(background,0,0);

        batch.draw(barn_shadow,466,1709);

        for (int i = 2; i < arrOvejas.size; i++) {
            if (salida <= 5.0f) {
                arrOvejas.get(i).setVelocidad(velocidadOve);
                arrOvejas.get(i).render(batch);
            }
            else{
                velocidadOve += 0.5f;
                salida = 0;
            }

        }

        if (tiempo >= 50.0){ // a los 50 seg sale la oveja arcoiris arriba
            arrOvejas.get(1).setVelocidad(velocidadOve);
            arrOvejas.get(1).render(batch);
        }

        batch.draw(barn,0,1709);

        batch.draw(cr,0,1617);

        batch.draw(life_lost, 60,1774);

        if(lifes>=3) {
            batch.draw(life, 266, 1778);
            batch.draw(life, 166, 1778);
            batch.draw(life,66,1778);
        }
        if(lifes==2){
            batch.draw(life, 166, 1778);
            batch.draw(life,66,1778);
        }
        if(lifes==1){
            batch.draw(life,66,1778);
        }


        if(lifes<=0){
            estado = EstadoJuego.PERDIDO;
        }


        if(contOvejas >= cantOve && lifes >= 3){
            estado = EstadoJuego.GANADO;
        }
        if(contOvejas >= (cantOve-1) && ((lifes == 2) || (lifes == 3))){
            estado = EstadoJuego.GANADO;
        }
        if(contOvejas >= (cantOve-2) && ((lifes == 1) || (lifes == 2))){
            estado = EstadoJuego.GANADO;
        }



        if (tiempo >= 30.0f) {
            if (!aS.getYaSalio()){
                if (aS.getEstado() != AlienShip.Estado.PAUSADO) {
                    if (aS.getEstado() != AlienShip.Estado.DERROTA) {
                        moverX += 5f * aS.getDireccionX();
                        moverY += 5f * aS.getDireccionY();
                        aS.spaceShipMove(moverX, moverY);
                        aS.setEstado(AlienShip.Estado.MOVIENDO);

                        if (aS.saliendoPor() == AlienShip.Estado.SALIENDOX) {
                            aS.cambiarDireccionX();
                            //moverX = 1080;
                        } else if (aS.saliendoPor() == AlienShip.Estado.SALIENDOY) {
                            aS.cambiarDireccionY();
                            Gdx.app.log("Condición Y", "se cumplió *****************");
                            //moverY = 1920;
                        }
                    } else {
                        if (!arrOvejas.get(0).isEnLlamas()) {
                            estaEnNave = true;
                            aS.setYaSalio(true);
                        }
                    }
                }
            }
        }

        if(apperAS){
            aS.render(batch);
        }


        // Sacar oveja alien
        if (tiempo >= 30.0f) {
            arrOvejas.get(0).setVelocidad(velocidadOve);
            arrOvejas.get(0).render(batch);
        }


        batch.end();

        escenaJuego.draw();

        if (estado == EstadoJuego.PAUSADO) {
            pauseScene.draw();
            if(pref.getBoolean("musicOn")){
                musicBtn.setPosition(373,431);
                pauseScene.addActor(musicBtn);
                noMusicBtn.remove();

            }
            if(!pref.getBoolean("musicOn")){
                musicBtn.setPosition(373,431);
                pauseScene.addActor(noMusicBtn);
                musicBtn.remove();
            }

            if(pref.getBoolean("fxOn")){
                fxBtn.setPosition(561,431);
                pauseScene.addActor(fxBtn);
                noFxBtn.remove();

            }
            if(!pref.getBoolean("fxOn")){
                fxBtn.setPosition(561,431);
                pauseScene.addActor(noFxBtn);
                fxBtn.remove();
            }

        }

        if (estado == EstadoJuego.PERDIDO){
            detenerOveja(false);
            Gdx.input.setInputProcessor(lostScene);
            if(pref.getBoolean("musicOn")) {
                lostScene.draw();
                if (!played) sheepEm.playLost();
                played = true;
            }
        }

        if(estado ==  EstadoJuego.GANADO){
            Gdx.input.setInputProcessor(winScene);
            winScene.draw();
            detenerOveja(true);
            pref.putBoolean("wonLevelTwo",true);
            if(pref.getBoolean("musicOn")){
                sheepEm.win.play();
                sheepEm.win.setLooping(true);
            }
            pref.flush();
        }

        if(pref.getBoolean("musicOn")){
            if(estado == EstadoJuego.JUGANDO){
                sheepEm.playLevelThreeMusic();
            }else{
                sheepEm.pauseLevelThreeMusic();
            }

        }


        if(!pref.getBoolean("musicOn")){
            sheepEm.pauseLevelThreeMusic();
        }
        eliminarOveja();

        pref.flush();
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
        PAUSADO,
        PERDIDO,
        GANADO


    }
    private class pauseScene extends Stage {
        public pauseScene(Viewport vista, SpriteBatch batch) {
            super(vista,batch);


            Texture opaque = new Texture("opaque.png");
            TextureRegionDrawable trdOpaq = new TextureRegionDrawable(new TextureRegion(opaque));
            Image op = new Image(trdOpaq);
            op.setPosition(0,0);
            this.addActor(op);

            Texture pauseRectangle = new Texture("pauseRectangle.png");
            TextureRegionDrawable trdRect = new TextureRegionDrawable(new TextureRegion(pauseRectangle));
            Image rectangle = new Image(trdRect);
            rectangle.setPosition(71,253);
            this.addActor(rectangle);


            Texture pressedContinueButton = new Texture("Buttons/pressed/pressedContinueButton.png");
            TextureRegionDrawable trdContinuepr = new TextureRegionDrawable(new TextureRegion(pressedContinueButton));
            continueButton = new Texture("Buttons/unpressed/continueButton.png");
            TextureRegionDrawable trdContinue = new TextureRegionDrawable(new TextureRegion(continueButton));
            ImageButton btnContinue = new ImageButton(trdContinue,trdContinuepr);
            btnContinue.setPosition(383,968);
            btnContinue.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //Cambio el estado de sheepEm a JUGANDO y regreso el poder a la escenaJuego
                    estado = EstadoJuego.JUGANDO;
                    detenerOveja(false);
                    aS.setEstado(AlienShip.Estado.INICIO);
                    sheepEm.playLevelThreeMusic();
                    Gdx.input.setInputProcessor(escenaJuego);
                }
            });
            this.addActor(btnContinue);


            Texture pressedHomeButton = new Texture("Buttons/pressed/PressedLevelsMenuButton.png");
            TextureRegionDrawable trdHomepr = new TextureRegionDrawable(new
                    TextureRegion(pressedHomeButton));
            homeButton = new Texture("Buttons/unpressed/LevelsMenuButton.png");
            TextureRegionDrawable trdHome = new TextureRegionDrawable(
                    new TextureRegion(homeButton));
            ImageButton homeBtn = new ImageButton(trdHome, trdHomepr);
            homeBtn.setPosition(285,695);
            homeBtn.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al menú
                    sheepEm.setScreen(new MapScreen(sheepEm));
                    sheepEm.stopLevelThreeMusic();
                }
            });
            this.addActor(homeBtn);

            Texture pressedRestartButton = new Texture("Buttons/pressed/PressedRetryLevelButton.png");
            TextureRegionDrawable trdRestartpr =  new TextureRegionDrawable(new TextureRegion(pressedRestartButton));
            Texture restartButton = new Texture("Buttons/unpressed/RetryLevelButton.png");
            TextureRegionDrawable trdRestart = new TextureRegionDrawable(new TextureRegion(restartButton));
            ImageButton restartBtn = new ImageButton(trdRestart, trdRestartpr);
            restartBtn.setPosition(586,695);
            restartBtn.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al menú
                    sheepEm.stopLevelThreeMusic();
                    sheepEm.setScreen(new LevelThree(sheepEm));
                    sheepEm.playLevelThreeMusic();
                }
            });
            this.addActor(restartBtn);

            Texture pauseMusicButton = new Texture("Buttons/unpressed/MusicPause.png");
            TextureRegionDrawable pauseMusicButtonTrd = new TextureRegionDrawable(new TextureRegion(pauseMusicButton));
            Texture pauseMusicButtonPr = new Texture("Buttons/pressed/PressedMusicPause.png");
            TextureRegionDrawable pauseMusicButtonPrTrd = new TextureRegionDrawable(new TextureRegion(pauseMusicButtonPr));

            Texture pauseNoMusicButton = new Texture("Buttons/unpressed/noMusicPause.png");
            TextureRegionDrawable pauseNoMusicButtonTrd = new TextureRegionDrawable(new TextureRegion(pauseNoMusicButton));
            Texture pauseNoMusicButtonPr = new Texture("Buttons/pressed/PressedNoMusicPause.png");
            TextureRegionDrawable pauseNoMusicButtonPrTrd = new TextureRegionDrawable(new TextureRegion(pauseNoMusicButtonPr));

            musicBtn = new ImageButton(pauseMusicButtonTrd,pauseMusicButtonPrTrd);
            musicBtn.setPosition(373,431);
            musicBtn.addListener( new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    pref.putBoolean("musicOn",false);
                    pref.flush();
                }
            } );
            noMusicBtn = new ImageButton(pauseNoMusicButtonTrd,pauseNoMusicButtonPrTrd);
            noMusicBtn.setPosition(373,431);
            noMusicBtn.addListener( new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    pref.putBoolean("musicOn",true);
                    pref.flush();
                }
            } );

            Texture fxPause =  new Texture("Buttons/unpressed/fxPause.png");
            TextureRegionDrawable fxPauseTr = new TextureRegionDrawable(new TextureRegion(fxPause));
            Texture fxPausePr = new Texture("Buttons/pressed/PressedFxPause.png");
            TextureRegionDrawable fxPausePrTr = new TextureRegionDrawable(new TextureRegion(fxPausePr));

            Texture noFxPause = new Texture("Buttons/unpressed/NoFxPause.png");
            TextureRegionDrawable noFxPauseTr = new TextureRegionDrawable(new TextureRegion(noFxPause));
            Texture noFxPausePr = new Texture("Buttons/pressed/PressedNoFxPause.png");
            TextureRegionDrawable noFxPausePrTr = new TextureRegionDrawable(new TextureRegion(noFxPausePr));

            fxBtn = new ImageButton(fxPauseTr,fxPausePrTr);
            fxBtn.setPosition(561,431);
            fxBtn.addListener( new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    pref.putBoolean("fxOn",false);
                    pref.flush();
                }
            } );

            noFxBtn = new ImageButton(noFxPauseTr,noFxPausePrTr);
            noFxBtn.setPosition(561,431);
            noFxBtn.addListener( new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    pref.putBoolean("fxOn",true);
                    pref.flush();
                }
            } );


        }
    }

    // Escena para la pantalla de ganar ------------------------------------------------------------

    /************************
     * ********************
     * *************
     * **********
     * CAMBIAR SET SCREEN DEL SIGUIENTE NIVEL
     */
    private class winScene extends Stage{
        public winScene(Viewport vista, SpriteBatch batch){
            super(vista,batch);

            Texture opaque = new Texture("opaque.png");
            TextureRegionDrawable trdOpaq = new TextureRegionDrawable(new TextureRegion(opaque));
            Image op = new Image(trdOpaq);
            op.setPosition(0,0);
            this.addActor(op);

            Texture winRectangle = new Texture("winRectangle.png");
            TextureRegionDrawable winRectTrd = new TextureRegionDrawable(new TextureRegion(winRectangle));
            Image winRect = new Image(winRectTrd);
            winRect.setPosition(40,292);
            this.addActor(winRect);


            Texture nextLevel = new Texture("Buttons/unpressed/NextLevelButton.png");
            Texture nextLevelPr = new Texture("Buttons/pressed/PressedNextLevelButton.png");
            TextureRegionDrawable nextLevelTrd = new TextureRegionDrawable(new TextureRegion(nextLevel));
            TextureRegionDrawable nextLevelPrTrd = new TextureRegionDrawable(new TextureRegion(nextLevelPr));

            ImageButton nextLevelButton = new ImageButton(nextLevelTrd,nextLevelPrTrd);
            nextLevelButton.setPosition(383,972);
            nextLevelButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    sheepEm.setScreen(new AlienLevel(sheepEm));
                    if(pref.getBoolean("musicOn")){
                        sheepEm.win.stop();
                    }
                }
            });
            this.addActor(nextLevelButton);

            Texture retryLevel = new Texture("Buttons/unpressed/restartButton.png");
            Texture retryLevelPr = new Texture("Buttons/pressed/pressedRestartButton.png");
            TextureRegionDrawable retryLevelTrd = new TextureRegionDrawable(new TextureRegion(retryLevel));
            TextureRegionDrawable retryLevelPrTrd = new TextureRegionDrawable(new TextureRegion(retryLevelPr));

            ImageButton retryLevelButton = new ImageButton(retryLevelTrd, retryLevelPrTrd);
            retryLevelButton.setPosition(586,699);
            retryLevelButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    sheepEm.setScreen(new LevelThree(sheepEm));
                    sheepEm.stopLevelThreeMusic();
                    sheepEm.playLevelThreeMusic();
                    if(pref.getBoolean("musicOn")){
                        sheepEm.win.stop();
                    }
                }
            });
            this.addActor(retryLevelButton);


            Texture levelsMenu = new Texture("Buttons/unpressed/levelsButton.png");
            Texture levelsMenuPr = new Texture("Buttons/pressed/PressedLevelsButton.png");
            TextureRegionDrawable levelsMenuTrd = new TextureRegionDrawable(new TextureRegion(levelsMenu));
            TextureRegionDrawable levelsMenuPrTrd = new TextureRegionDrawable(new TextureRegion(levelsMenuPr));

            ImageButton levelsButton = new ImageButton(levelsMenuTrd,levelsMenuPrTrd);
            levelsButton.setPosition(285,699);
            levelsButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    sheepEm.setScreen(new MapScreen(sheepEm));
                    if(pref.getBoolean("musicOn")){
                        sheepEm.win.stop();
                    }
                }
            });
            this.addActor(levelsButton);


        }


    }

    // Escena para la pantalla de perder -----------------------------------------------------------
    private class lostScene extends Stage{
        public lostScene(Viewport vista, SpriteBatch batch){

            super(vista,batch);

            Texture opaque = new Texture("opaque.png");
            TextureRegionDrawable trdOpaq = new TextureRegionDrawable(new TextureRegion(opaque));
            Image op = new Image(trdOpaq);
            op.setPosition(0,0);
            this.addActor(op);

            Texture lostRectangle = new Texture("lostRectangle.png");
            TextureRegionDrawable trdRect = new TextureRegionDrawable(new TextureRegion(lostRectangle));
            Image rect = new Image(trdRect);
            rect.setPosition(47,300);
            this.addActor(rect);


            Texture homeButtonLost = new Texture("Buttons/unpressed/homeButtonLost.png");
            TextureRegionDrawable trdHome = new TextureRegionDrawable(new TextureRegion(homeButtonLost));
            TextureRegionDrawable trdHomePr = new TextureRegionDrawable(new TextureRegion(new Texture("Buttons/pressed/PressedHomeButtonLost.png")));
            ImageButton homeButton = new ImageButton(trdHome, trdHomePr);
            homeButton.setPosition(586,700);
            homeButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    sheepEm.stopLevelThreeMusic();
                    sheepEm.setScreen(new MenuScreen(sheepEm));
                    sheepEm.stopLost();

                }
            });
            this.addActor(homeButton);

            Texture tryAgainButton = new Texture("Buttons/unpressed/tryAgainButton.png");
            TextureRegionDrawable trdAgain = new TextureRegionDrawable(new TextureRegion(tryAgainButton));
            TextureRegionDrawable trdAgainpr = new TextureRegionDrawable(new TextureRegion(new Texture("Buttons/pressed/PressedTryAgainButton.png")));
            ImageButton tryAgain = new ImageButton(trdAgain, trdAgainpr);
            tryAgain.setPosition(383,972);
            tryAgain.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    sheepEm.stopLevelThreeMusic();
                    sheepEm.setScreen(new LevelThree(sheepEm));
                    sheepEm.playLevelThreeMusic();
                    sheepEm.stopLost();
                }
            });
            this.addActor(tryAgain);

            Texture levelsButton = new Texture("Buttons/unpressed/levelsButtonLost.png");
            Texture levelsButtonpr = new Texture("Buttons/pressed/PressedLevelsButtonLost.png");
            TextureRegionDrawable trdLevels = new TextureRegionDrawable(new TextureRegion(levelsButton));
            TextureRegionDrawable trdLevelspr = new TextureRegionDrawable(new TextureRegion(levelsButtonpr));
            ImageButton lvsButton = new ImageButton(trdLevels,trdLevelspr);
            lvsButton.setPosition(285,700);
            lvsButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    sheepEm.stopLevelThreeMusic();
                    sheepEm.setScreen(new MapScreen(sheepEm));
                    sheepEm.stopLost();
                }
            });
            this.addActor(lvsButton);


        }
    }


}
