package mx.itesm.sheep;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by jekot on 09/11/2017.
 */

public class AlienLevel extends ScreenTemplate {

    private Texture homeButton;
    private Texture continueButton;
    private ImageButton noMusicBtn;
    private ImageButton musicBtn;
    private ImageButton fxBtn;
    private ImageButton noFxBtn;

    private final SheepEm sheepEm;
    private Stage escenaAlien;
    private Texture nave;
    private Texture background;
    private Texture hp_outter;
    private Texture hp_inner;
    private Boolean played = false;

    private winScene winScene;
    private lostScene lostScene;
    private pauseScene pauseScene;

    private int hpAlien;

    private EstadoJuego estado;
    private float scale_factor;
    private Texture pauseButton;
    //private float totalTime = 60;
    private float sheepTimer = 60;
    private SheepAbducted sheepAbd;
    private Texture whitesheep;

    public AlienLevel(SheepEm sheepEm) {

        this.sheepEm = sheepEm;
    }

    @Override
    public void show() {
        cargarTexturas();
        crearEscenaNave();
        sheepAbd = new SheepAbducted(whitesheep, "WHITE");
        lostScene = new lostScene(view,batch);
        winScene = new winScene(view,batch);
        estado = EstadoJuego.JUGANDO;
        Gdx.input.setInputProcessor(escenaAlien);
        hpAlien = 50;
    }

    private void crearEscenaNave() {
        escenaAlien = new Stage(view);

        // Background
        TextureRegionDrawable trdBg =  new TextureRegionDrawable(new TextureRegion(background));
        Image bg = new Image(trdBg);
        bg.setPosition(0,0);
        escenaAlien.addActor(bg);

        // HP bar inner
        TextureRegionDrawable trd_hp_in =  new TextureRegionDrawable(new TextureRegion(hp_inner));
        final Image innerimg = new Image(trd_hp_in);
        innerimg.setPosition(39,28);
        escenaAlien.addActor(innerimg);

        // HP bar outter
        TextureRegionDrawable trd_hp_out =  new TextureRegionDrawable(new TextureRegion(hp_outter));
        final Image outterimg = new Image(trd_hp_out);
        outterimg.setPosition(29,14);
        escenaAlien.addActor(outterimg);

        //sheepAbd.setX(150);
        //sheepAbd.setY(150);

        // Nave
        TextureRegionDrawable trdNave = new TextureRegionDrawable(new TextureRegion(nave));
        final ImageButton btnNave = new ImageButton(trdNave);
        btnNave.setPosition(200,700);
        escenaAlien.addActor(btnNave);
        btnNave.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.println(hpAlien);
                scale_factor += 0.02;
                hpAlien--;
                outterimg.setZIndex(10);
                innerimg.setScale(1-scale_factor,1);

            }
        } );

        // Botón de pausa --------------------------------------------------------------------------

        Texture pressedPauseButton = new Texture("Buttons/pressed/pressedPauseButton.png");
        TextureRegionDrawable trdPausepr = new TextureRegionDrawable(new TextureRegion(pressedPauseButton));
        TextureRegionDrawable trdPause = new TextureRegionDrawable(new TextureRegion(pauseButton));
        ImageButton imPause = new ImageButton(trdPause, trdPausepr);
        imPause.setPosition(150, 1734);
        escenaAlien.addActor(imPause);

        imPause.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                estado = EstadoJuego.PAUSADO;
                pauseScene = new pauseScene(view,batch);
                Gdx.input.setInputProcessor(pauseScene);
            }
        } );
    }

    private void cargarTexturas() {
        background = new Texture("alienLevelbg.png");
        nave = new Texture("alienShip.png");
        hp_inner = new Texture("innerhp.png");
        hp_outter = new Texture("outerhp.png");
        pauseButton = new Texture("Buttons/unpressed/pauseButton.png");
        whitesheep = new Texture("sheepAlienlvl.png");
    }

    @Override
    public void render(float delta) {
        Gdx.input.setCatchBackKey(true);
        clearScreen(0,0,0);
        batch.setProjectionMatrix(camera.combined);


        if (estado == EstadoJuego.JUGANDO) {
            sheepTimer -= Gdx.graphics.getDeltaTime();
        }


        batch.begin();
        if (hpAlien==0){
            this.estado = EstadoJuego.GANADO;
        }else{
            escenaAlien.draw();
            sheepAbd.render(batch);
        }
        batch.end();



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
            Gdx.input.setInputProcessor(lostScene);
            lostScene.draw();
            if(!played) sheepEm.playLost();
            played = true;
        }

        if(estado ==  EstadoJuego.GANADO){
            Gdx.input.setInputProcessor(winScene);
            winScene.draw();
            pref.putBoolean("wonAlien",true);
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
        pref.flush();
    }

    enum EstadoJuego {
        JUGANDO,
        PAUSADO,
        PERDIDO,
        GANADO
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

    // Pause scene *********************
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
                    sheepEm.playGameMusic();
                    Gdx.input.setInputProcessor(escenaAlien);
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
                    sheepEm.stopGameMusic();
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
                    sheepEm.stopGameMusic();
                    sheepEm.setScreen(new AlienLevel(sheepEm));
                    sheepEm.playGameMusic();
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

                }
            } );
            noMusicBtn = new ImageButton(pauseNoMusicButtonTrd,pauseNoMusicButtonPrTrd);
            noMusicBtn.setPosition(373,431);
            noMusicBtn.addListener( new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    pref.putBoolean("musicOn",true);
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
    /************************
     * ********************
     * *************
     * **********
     * CAMBIAR SET SCREEN DEL SIGUIENTE NIVEL
     */
    // Winning scene **************************************
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
                    sheepEm.setScreen(new AlienLevel(sheepEm));
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
                }
            });
            this.addActor(levelsButton);


        }
    }

    //  scene -----------------------------------------------------------
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
                    sheepEm.stopLevelTwoMusic();
                    sheepEm.setScreen(new LevelThree(sheepEm));
                    sheepEm.playLevelTwoMusic();
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
                    sheepEm.stopLevelTwoMusic();
                    sheepEm.setScreen(new MapScreen(sheepEm));
                    sheepEm.stopLost();
                }
            });
            this.addActor(lvsButton);


        }
    }
}
