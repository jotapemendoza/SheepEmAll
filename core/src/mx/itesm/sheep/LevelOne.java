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
 * Created by josepablo on 9/14/17.
 */

public class LevelOne extends ScreenTemplate {

    private final SheepEm sheepEm;
    
    // Texturas/Parte Gráfica ----------------------------------------------------------------------
    private Texture continueButton;
    private Texture pauseButton;
    private Texture homeButton;
    private Texture background;
    private Stage escenaJuego;
    private Texture oveArr;
    private Texture oveIzq;
    private Texture oveDer;
    private Texture oveAb;
    private Texture time;
    private Texture life;
    private Texture life_lost;
    private Texture oveMovAb;
    private Texture oveMovArr;
    private Texture oveMovIzq;
    private Texture oveMovDer;
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

    private Boolean played = false;

    private Music sheep;

    // Arreglo de ovejas ---------------------------------------------------------------------------
    private Array<Oveja> arrOvejas;
    private Oveja ovejaMoviendo = null;
    private int ovejaMovX;
    private int ovejaMovY;
    private final int cantOve = 20;
    private int contOvejas = 0;
    private String arrColores[] = {"WHITE","BLUE","RED","YELLOW"};
    private String arrTipos[] = {"NORMAL","ALIEN","RAINBOW"};

    private float salida;
    private float velocidadOve = 1.0f;
    private int lifes;

    float totalTime = 1 * 60;

    private EstadoJuego estado;
    
    // Escenas -------------------------------------------------------------------------------------
    private EscenaPerder escenaPerder;
    private EscenaGanar escenaGanar;
    private EscenaPausa escenaPausa;


    private float tiempo;


    public LevelOne(SheepEm sheepEm){
        this.sheepEm = sheepEm;
    }

    @Override
    public void show() {
        cargarTexturas();
        cargarOvejas();
        crearEscenaJuego();
        font = new BitmapFont(Gdx.files.internal("Intro.fnt"));
        escenaPerder = new EscenaPerder(vista,batch);
        escenaGanar = new EscenaGanar(vista,batch);
        estado = EstadoJuego.JUGANDO;
        Gdx.input.setInputProcessor(escenaJuego);
        Gdx.input.setCatchBackKey(true);
        lifes = 3;
        sheep = Gdx.audio.newMusic(Gdx.files.internal("SFX/sheep_sound.mp3"));
    }

    private void crearEscenaJuego() {

        escenaJuego = new Stage(vista);

        escenaJuego.addListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode==Input.Keys.BACK){
                    sheepEm.setScreen(new MapScreen(sheepEm));
                    sheepEm.pauseGameMusic();
                }
                return true;
            }
        });

        // Botón de pausa --------------------------------------------------------------------------

        Texture pressedPauseButton = new Texture("Buttons/pressed/pressedPauseButton.png");
        TextureRegionDrawable trdPausepr = new TextureRegionDrawable(new TextureRegion(pressedPauseButton));
        TextureRegionDrawable trdPause = new TextureRegionDrawable(new TextureRegion(pauseButton));
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
                            ovejaMovX = (int) x;
                            ovejaMovY = (int) y;
                            ovejaMoviendo.setEstado(Oveja.Estado.MOVIENDO);

                            break;
                        }
                    }
                }
            }

            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                super.drag(event, x, y, pointer);
                if (ovejaMoviendo == null){ return; }
                sheep.play();
                ovejaMoviendo.setX(x - ovejaMoviendo.getAncho()/2);
                ovejaMoviendo.setY(y - ovejaMoviendo.getAlto()/2);
            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer) {
                super.dragStop(event, x, y, pointer);
                sheep.stop();
                if(ovejaMoviendo != null){
                    // verificar si está en el corral
                    if(cordenadasCorral(x,y,ovejaMoviendo.getColor())){
                        ovejaMoviendo.setEstado(ovejaMoviendo.getEstadoOriginal());
                        contOvejas++;
                        ovejaMoviendo = null;
                    }else{
                        if(!cordenadasLineales(x,y)){
                            ovejaMoviendo.setEstado(Oveja.Estado.BOOM);
                            lifes--;
                            ovejaMoviendo = null;
                        }else{
                            ovejaMoviendo.setSeMovio(false);
                            ovejaMoviendo.setEstado(ovejaMoviendo.getEstadoOriginal());
                            ovejaMoviendo.setX(ovejaMovX);
                            ovejaMoviendo.setY(ovejaMovY);
                            ovejaMoviendo = null;
                        }
                    }
                }
            }

        });

    }

    // Validar corral correcto ---------------------------------------------------------------------
    public boolean cordenadasCorral(float xP, float yP, String color) {
        if ((xP >= 0 && xP <= 410 && yP >= 110 && yP <= 730 && color.equals("RED")) ||
                (xP >= 670 && xP <= 1080 && yP >= 110 && yP <= 730 && color.equals("BLUE")) ||
                (xP >= 0 && xP <= 410 && yP >= 1104 && yP <= 1730 && color.equals("WHITE")) ||
                (xP >= 670 && xP <= 1080 && yP >= 1104 && yP <= 1730 && color.equals("YELLOW"))){
            return true;
        }
        return false;
    }

    // Validar camino de ovejas --------------------------------------------------------------------
    public boolean cordenadasLineales(float xP, float yP){
        if ( (xP >= 410 && xP <= 670 && yP >= 0 && yP <= 1920) ||
                (xP >= 0 && xP <= 1080 && yP >= 730 && yP <= 1104) ){
            return true;
        }
        return false;
    }
    
    // Detener ovejas en el sheepEm ------------------------------------------------------------------
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

    // Método que carga las ovejas en el sheepEm -----------------------------------------------------
    private void cargarOvejas(){
        //Llenar arreglo Ovejas
        arrOvejas = new Array<Oveja>(cantOve);
        Oveja ove;

        for (int i = 0; i < cantOve; i++){
            int random = (int) (Math.random()*4)+1;
            int randomColor = (int) (Math.random()*4)+1;

            if (random == 1){
                switch (randomColor){
                    case 1:
                        ove = new Oveja(oveArrWhite, oveArrMovWhite,
                                Oveja.Estado.ARRIBA, arrColores[0], arrTipos[0]);
                        arrOvejas.add(ove);
                        break;
                    case 2:
                        ove = new Oveja(oveArrBlue, oveArrMovBlue,
                                Oveja.Estado.ARRIBA, arrColores[1], arrTipos[0]);
                        arrOvejas.add(ove);
                        break;
                    case 3:
                        ove = new Oveja(oveArrRed, oveArrMovRed,
                                Oveja.Estado.ARRIBA, arrColores[2], arrTipos[0]);
                        arrOvejas.add(ove);
                        break;
                    case 4:
                        ove = new Oveja(oveArrYellow, oveArrMovYellow,
                                Oveja.Estado.ARRIBA, arrColores[3], arrTipos[0]);
                        arrOvejas.add(ove);
                        break;
                }
            }else if (random == 2){
                ove = new Oveja(oveAb, oveMovAb, Oveja.Estado.ABAJO, "BLUE", arrTipos[0]);
                arrOvejas.add(ove);
            }else if (random == 3){
                ove = new Oveja(oveIzq, oveMovIzq, Oveja.Estado.IZQUIERDA, "WHITE", arrTipos[0]);
                arrOvejas.add(ove);
            }else{
                ove = new Oveja(oveDer, oveMovDer, Oveja.Estado.DERECHA, "YELLOW", arrTipos[0]);
                arrOvejas.add(ove);
            }
        }
    }

    // Método que elimina las ovejas en el sheepEm ---------------------------------------------------
    private void eliminarOveja(){
        for (int i = 0; i < arrOvejas.size; i++){
            if (arrOvejas.get(i).getEstado().equals(Oveja.Estado.ARRIBA)){
                if (arrOvejas.get(i).gety() <= 0){
                    arrOvejas.removeIndex(i);
                    System.out.println("ovejas disponibles: " + arrOvejas.size);
                    lifes--;
                    break;
                }
            }
            if (arrOvejas.get(i).getEstado().equals(Oveja.Estado.ABAJO)){
                if (arrOvejas.get(i).gety() >= 1920){
                    arrOvejas.removeIndex(i);
                    System.out.println("ovejas disponibles: " + arrOvejas.size);
                    lifes--;
                    break;
                }
            }
            if (arrOvejas.get(i).getEstado().equals(Oveja.Estado.IZQUIERDA)){
                if (arrOvejas.get(i).getx() >= 1080){
                    arrOvejas.removeIndex(i);
                    System.out.println("ovejas disponibles: " + arrOvejas.size);
                    lifes--;
                    break;
                }
            }
            if (arrOvejas.get(i).getEstado().equals(Oveja.Estado.DERECHA)){
                if (arrOvejas.get(i).getx() <= 0){
                    arrOvejas.removeIndex(i);
                    System.out.println("ovejas disponibles: " + arrOvejas.size);
                    lifes--;
                    break;
                }
            }
        }
    }

    // Método que carga todas las texturas del sheepEm -----------------------------------------------
    private void cargarTexturas() {
        background = new Texture("gBg.png");
        pauseButton = new Texture("Buttons/unpressed/pauseButton.png");
        oveArr = new Texture("sheep_down.png");
        oveIzq = new Texture("sheep_right.png");
        oveAb = new Texture("sheep_up.png");
        oveDer = new Texture("sheep_left.png");
        time = new Texture("time.png");
        life = new Texture("life.png");
        life_lost = new Texture("life_lost.png");
        oveMovArr = new Texture("sheep_moving1.png");
        oveMovAb = new Texture("sheep_moving.png");
        oveMovIzq = new Texture("sheep_moving3.png");
        oveMovDer = new Texture("sheep_moving2.png");

        //ovejas de colores
        oveArrBlue = new Texture("Sheep/Blue/sheep_down_blue.png");
        oveArrMovBlue = new Texture("Sheep/Blue/sheep_moving_down_blue.png");
        oveArrRed = new Texture("Sheep/Red/sheep_down_red.png");
        oveArrMovRed = new Texture("Sheep/Red/sheep_moving_down_red.png");
        oveArrWhite = new Texture("Sheep/White/sheep_down_white.png");
        oveArrMovWhite = new Texture("Sheep/White/sheep_moving_down_white.png");
        oveArrYellow = new Texture("Sheep/Yellow/sheep_down_yellow.png");
        oveArrMovYellow = new Texture("Sheep/Yellow/sheep_moving_down_yellow.png");
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
        batch.draw(background, 0, 0);
        
        // Dibujar el asset de vidas dependiendo al número de vidas --------------------------------

        if(lifes==3) {
            batch.draw(life, 277, 1796);
            batch.draw(life, 177, 1796);
            batch.draw(life,77,1796);
        }
        if(lifes==2){
            batch.draw(life, 177, 1796);
            batch.draw(life,77,1796);
        }
        if(lifes==1){
            batch.draw(life,77,1796);
        }
        
        // -----------------------------------------------------------------------------------------

        if(lifes<=0){
            estado = EstadoJuego.PERDIDO;
        }

        if(contOvejas >= cantOve && lifes == 3){
            estado = EstadoJuego.GANADO;
        }
        if(contOvejas >= (cantOve-1) && lifes == 2){
            estado = EstadoJuego.GANADO;
        }
        if(contOvejas >= (cantOve-2) && lifes == 1){
            estado = EstadoJuego.GANADO;
        }

        batch.draw(life_lost, 277,1796);
        batch.draw(life_lost, 177,1796);
        batch.draw(life_lost, 77,1796);

        batch.draw(time,680,1814);

        // Se dibuja el tiempo restante que tiene el usuario ---------------------------------------

        if(seconds>=10){
            font.draw(batch,Integer.toString(minutes)+ ":"+ Integer.toString(seconds),755,1888);
        }else{
            font.draw(batch,Integer.toString(minutes)+ ":0"+ Integer.toString(seconds),755,1888);
        }
        // -----------------------------------------------------------------------------------------


        for (int i = 0; i < arrOvejas.size; i++) {
            if (salida <= 10) {
                arrOvejas.get(i).setVelocidad(velocidadOve);
                arrOvejas.get(i).render(batch);
            }
            else{
                velocidadOve += 0.5f;
                salida = 0;
            }

        }
        batch.end();

        escenaJuego.draw();

        if (estado == EstadoJuego.PAUSADO) {
            escenaPausa.draw();
        }

        if (estado == EstadoJuego.PERDIDO){
            detenerOveja(false);
            Gdx.input.setInputProcessor(escenaPerder);
            escenaPerder.draw();
            if(!played) sheepEm.playLost();
            played = true;
            sheep.stop();
        }

        if(estado ==  EstadoJuego.GANADO){
            Gdx.input.setInputProcessor(escenaGanar);
            pref.putBoolean("wonLevelOne",true);
            escenaGanar.draw();
        }

        if(pref.getBoolean("musicOn")){
            if(estado == EstadoJuego.JUGANDO){
                sheepEm.playGameMusic();
            }else{
                sheepEm.pauseGameMusic();
            }

        }
        if(!pref.getBoolean("musicOn")){
            sheepEm.pauseGameMusic();
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
    // Escena para el menú de pausa ----------------------------------------------------------------
    private class EscenaPausa extends Stage {
        public EscenaPausa(Viewport vista, SpriteBatch batch) {
            super(vista,batch);


            Texture opaque = new Texture("opaque.png");
            TextureRegionDrawable trdOpaq = new TextureRegionDrawable(new TextureRegion(opaque));
            Image op = new Image(trdOpaq);
            op.setPosition(0,0);
            this.addActor(op);


            Texture pauseRectangle = new Texture("pauseRectangle.png");
            TextureRegionDrawable trdRect = new TextureRegionDrawable(new TextureRegion(pauseRectangle));
            Image rectangle = new Image(trdRect);
            rectangle.setPosition(47,489);
            this.addActor(rectangle);

            Texture pauseText = new Texture("pauseText.png");
            TextureRegionDrawable trdPText = new TextureRegionDrawable(new TextureRegion(pauseText));
            Image pauseT = new Image(trdPText);
            pauseT.setPosition(270,1399);
            this.addActor(pauseT);


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
                    sheepEm.playGameMusic();
                    Gdx.input.setInputProcessor(escenaJuego);
                }
            });
            this.addActor(btnContinue);


            Texture pressedHomeButton = new Texture("Buttons/pressed/PressedLevelsButton.png");
            TextureRegionDrawable trdHomepr = new TextureRegionDrawable(new
                    TextureRegion(pressedHomeButton));
            homeButton = new Texture("Buttons/unpressed/levelsButton.png");
            TextureRegionDrawable trdHome = new TextureRegionDrawable(
                    new TextureRegion(homeButton));
            ImageButton homeBtn = new ImageButton(trdHome, trdHomepr);
            homeBtn.setPosition(285,695);
            homeBtn.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al menú
                    sheepEm.setScreen(new MenuScreen(sheepEm));
                    sheepEm.stopGameMusic();
                }
            });
            this.addActor(homeBtn);

            Texture pressedRestartButton = new Texture("Buttons/pressed/pressedRestartButton.png");
            TextureRegionDrawable trdRestartpr =  new TextureRegionDrawable(new
                    TextureRegion(pressedRestartButton));
            Texture restartButton = new Texture("Buttons/unpressed/restartButton.png");
            TextureRegionDrawable trdRestart = new TextureRegionDrawable(new
                    TextureRegion(restartButton));
            ImageButton restartBtn = new ImageButton(trdRestart, trdRestartpr);
            restartBtn.setPosition(586,695);
            restartBtn.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al menú
                    sheepEm.stopGameMusic();
                    sheepEm.setScreen(new LevelOne(sheepEm));
                    sheepEm.playGameMusic();
                }
            });
            this.addActor(restartBtn);



        }
    }

    // Escena para la pantalla de ganar ------------------------------------------------------------
    private class EscenaGanar extends Stage{
        public EscenaGanar(Viewport vista, SpriteBatch batch){
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
                    sheepEm.setScreen(new MenuScreen(sheepEm));
                }
            });
            this.addActor(nextLevelButton);

            Texture retryLevel = new Texture("Buttons/unpressed/RetryLevelButton.png");
            Texture retryLevelPr = new Texture("Buttons/pressed/PressedRetryLevelButton.png");
            TextureRegionDrawable retryLevelTrd = new TextureRegionDrawable(new TextureRegion(retryLevel));
            TextureRegionDrawable retryLevelPrTrd = new TextureRegionDrawable(new TextureRegion(retryLevelPr));

            ImageButton retryLevelButton = new ImageButton(retryLevelTrd, retryLevelPrTrd);
            retryLevelButton.setPosition(586,699);
            retryLevelButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    sheepEm.setScreen(new LevelOne(sheepEm));
                }
            });
            this.addActor(retryLevelButton);


            Texture levelsMenu = new Texture("Buttons/unpressed/LevelsMenuButton.png");
            Texture levelsMenuPr = new Texture("Buttons/pressed/PressedLevelsMenuButton.png");
            TextureRegionDrawable levelsMenuTrd = new TextureRegionDrawable(new TextureRegion(levelsMenu));
            TextureRegionDrawable levelsMenuPrTrd = new TextureRegionDrawable(new TextureRegion(levelsMenuPr));

            ImageButton levelsButton = new ImageButton(levelsMenuTrd,levelsMenuPrTrd);
            levelsButton.setPosition(285,699);
            levelsButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    sheepEm.setScreen(new LevelOne(sheepEm));
                }
            });
            this.addActor(levelsButton);


        }


    }

    // Escena para la pantalla de perder -----------------------------------------------------------
    private class EscenaPerder extends Stage{
        public EscenaPerder(Viewport vista, SpriteBatch batch){

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
                    sheepEm.stopGameMusic();
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
                    sheepEm.stopGameMusic();
                    sheepEm.setScreen(new LevelOne(sheepEm));
                    sheepEm.playGameMusic();
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
                    sheepEm.stopGameMusic();
                    sheepEm.setScreen(new MapScreen(sheepEm));
                    sheepEm.stopLost();
                }
            });
            this.addActor(lvsButton);


        }
    }
    
}