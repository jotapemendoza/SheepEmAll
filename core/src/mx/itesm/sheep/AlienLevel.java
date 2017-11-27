package mx.itesm.sheep;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
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

    private final SheepEm sheepEm;
    private Stage escenaAlien;

    private Texture nave,
            background,
            hp_outter,
            hp_inner,
            whitesheep,
            instructions,
            fadeIn;

    private Boolean played = false;

    private lostScene lostScene;

    private float hpAlien,
            sheepTimer,
            elapsedTime,
            startTime;

    private EstadoJuego estado;

    private SheepAbducted sheepAbd;
    private AlienShip alienShip;

    private TextureRegion[] animationFrames;
    private Animation animation;

    private final float LIFE = 90;

    public AlienLevel(SheepEm sheepEm) {
        this.sheepEm = sheepEm;
    }

    @Override
    public void show() {
        cargarTexturas();
        crearEscenaNave();
        sheepAbd = new SheepAbducted(whitesheep, "WHITE");
        alienShip = new AlienShip(nave, AlienShip.Estado.BOSS);
        lostScene = new lostScene(view,batch);
        estado = EstadoJuego.JUGANDO;
        hpAlien = LIFE;
        fadeIn = new Texture("fadeIn.png");
        sheepTimer = 60;

        TextureRegion[][] tmpFrames = TextureRegion.split(fadeIn,1080,1920);
        animationFrames = new TextureRegion[7];
        int index = 0;

        for (int i = 0; i < 7 ; i++) {
            animationFrames[index++] = tmpFrames[0][i];
        }

        animation = new Animation(1f/15f,animationFrames);
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
        innerimg.setPosition(226,1364);
        escenaAlien.addActor(innerimg);

        // HP bar outter
        TextureRegionDrawable trd_hp_out =  new TextureRegionDrawable(new TextureRegion(hp_outter));
        final Image outterimg = new Image(trd_hp_out);
        outterimg.setPosition(213,1350);
        escenaAlien.addActor(outterimg);


        // Nave
        TextureRegionDrawable trdNave = new TextureRegionDrawable(new TextureRegion(nave));
        final ImageButton btnNave = new ImageButton(trdNave);
        btnNave.setPosition(200,700);
        escenaAlien.addActor(btnNave);
        btnNave.setZIndex(15);
        btnNave.setColor(0,0,0,0);
        btnNave.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(pref.getBoolean("fxOn")){
                    sheepEm.hit.play();
                }
                hpAlien--;
                outterimg.setZIndex(10);
                sheepAbd.setY(sheepAbd.gety()-9);
                innerimg.setScale(hpAlien/LIFE,1);

            }
        } );

    }

    private void cargarTexturas() {
        background = new Texture("alienLevelbg.png");
        nave = new Texture("alienShip.png");
        hp_inner = new Texture("innerhp.png");
        hp_outter = new Texture("outerhp.png");
        whitesheep = new Texture("sheepAlienlvl.png");
        instructions = new Texture("alienTutorial.png");
    }

    @Override
    public void render(float delta) {

        startTime+=Gdx.graphics.getDeltaTime();
        Gdx.input.setCatchBackKey(true);
        clearScreen(0,0,0);
        batch.setProjectionMatrix(camera.combined);


        if (estado == EstadoJuego.JUGANDO) {
            sheepTimer -= Gdx.graphics.getDeltaTime();
        }

        escenaAlien.draw();


        batch.begin();

        if(startTime<3.5){
            batch.draw(instructions,0,0);
        }

        if(startTime>3.5){
            Gdx.input.setInputProcessor(escenaAlien);
            if (hpAlien==0){
                this.estado = EstadoJuego.GANADO;
            }else{
                sheepAbd.setX(440);
                sheepAbd.render(batch);
            }
            alienShip.setPosicionX(265);
            alienShip.setPosicionY(745);
            alienShip.render(batch);
        }

        if(estado == EstadoJuego.GANADO){
            elapsedTime += Gdx.graphics.getDeltaTime();
            TextureRegion sheeptr = (TextureRegion) animation.getKeyFrame(elapsedTime,false);
            batch.draw(sheeptr,0,0);
            sheepAbd.hideSheep();
            pref.putBoolean("arcade",true);
            pref.flush();

        }
        batch.end();

        if(sheepAbd.gety()>=700){
            this.estado = EstadoJuego.PERDIDO;
        }

        if (estado == EstadoJuego.PERDIDO){
            Gdx.input.setInputProcessor(lostScene);
            lostScene.draw();
            alienShip.hideShip();
            sheepAbd.hideSheep();
            if(!played) sheepEm.playLost();
            played = true;
        }

        if(elapsedTime>=1.75){
            sheepEm.setScreen(new EndingScreen(sheepEm));
        }

        if(pref.getBoolean("musicOn")){

            if(estado == EstadoJuego.JUGANDO && startTime>3.5){
                sheepEm.alienLevel.play();
                sheepEm.alienLevel.setLooping(true);
            }else{
                sheepEm.alienLevel.pause();
            }

        }
        if(!pref.getBoolean("musicOn")){
            sheepEm.alienLevel.pause();
        }
        pref.flush();
    }

    enum EstadoJuego {
        JUGANDO,
        PERDIDO,
        GANADO,
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


    // Losing scene -----------------------------------------------------------
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
                    sheepEm.alienLevel.stop();
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
                    sheepEm.alienLevel.stop();
                    sheepEm.setScreen(new AlienLevel(sheepEm));
                    sheepEm.alienLevel.play();
                    sheepEm.alienLevel.setLooping(true);
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
                    sheepEm.alienLevel.stop();
                    sheepEm.setScreen(new MapScreen(sheepEm));
                    sheepEm.stopLost();
                }
            });
            this.addActor(lvsButton);


        }
    }
}
