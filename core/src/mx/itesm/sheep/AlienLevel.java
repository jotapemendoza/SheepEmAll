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

public class AlienLevel extends MainScreen {

    private final Juego juego;
    private Stage escenaAlien;
    private Texture nave;
    private Texture background;
    private Texture sheep;
    private Boolean played = false;

    private EscenaGanar escenaGanar;
    private EscenaPerder escenaPerder;
    private Stage escenaJuego;

    private int hpAlien;

    public AlienLevel(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        cargarTexturas();
        crearEscenaNave();
        escenaPerder = new EscenaPerder(vista,batch);
        escenaGanar = new EscenaGanar(vista,batch);
        //estado = LevelOne.EstadoJuego.JUGANDO;
        Gdx.input.setInputProcessor(escenaAlien);

        hpAlien = 10;
    }

    private void crearEscenaNave() {
        escenaAlien = new Stage(vista);

        // Background
        TextureRegionDrawable trdBg =  new TextureRegionDrawable(new TextureRegion(background));
        Image bg = new Image(trdBg);
        bg.setPosition(0,0);
        escenaAlien.addActor(bg);

        // Sheep
        TextureRegionDrawable trdSheep =  new TextureRegionDrawable(new TextureRegion(sheep));
        Image sheepimg = new Image(trdSheep);
        sheepimg.setPosition(50,500);
        escenaAlien.addActor(sheepimg);

        // Nave
        TextureRegionDrawable trdNave = new TextureRegionDrawable(new TextureRegion(nave));
        final ImageButton btnNave = new ImageButton(trdNave);
        btnNave.setPosition(374,1167);
        escenaAlien.addActor(btnNave);
        btnNave.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.println("holi");
                hpAlien--;
            }
        } );
    }

    private void cargarTexturas() {
        background = new Texture("alienLevelbg.png");
        nave = new Texture("alienShip.png");
        sheep = new Texture("sheepAlienlvl.png");
    }

    @Override
    public void render(float delta) {
        borrarPantalla(0,0,0);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        escenaAlien.draw();
        /*if (hpAlien==0){
            estado = LevelOne.EstadoJuego.GANADO;
        }

        if (estado == LevelOne.EstadoJuego.PERDIDO){
            Gdx.input.setInputProcessor(escenaPerder);
            escenaPerder.draw();
            if(!played) juego.playLost();
            played = true;
        }
        if(estado ==  LevelOne.EstadoJuego.GANADO){
            Gdx.input.setInputProcessor(escenaGanar);
            escenaGanar.draw();
        }*/
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


    private class EscenaGanar extends Stage{
        public EscenaGanar(Viewport vista, SpriteBatch batch){
            super(vista,batch);

            Texture opaque = new Texture("opaque.png");
            TextureRegionDrawable trdOpaq = new TextureRegionDrawable(new TextureRegion(opaque));
            Image op = new Image(trdOpaq);
            op.setPosition(0,0);
            this.addActor(op);


            /*Texture winText = new Texture("winText.png");
            TextureRegionDrawable winTrd = new TextureRegionDrawable(new TextureRegion(winText));
            Image winIm = new Image(winTrd);
            winIm.setPosition(126,1240);
            this.addActor(winIm);*/

            Texture winRectangle = new Texture("winRectangle.png");
            TextureRegionDrawable winRectTrd = new TextureRegionDrawable(new TextureRegion(winRectangle));
            Image winRect = new Image(winRectTrd);
            winRect.setPosition(40,292);
            this.addActor(winRect);

           /* Texture winSheep = new Texture("winSheep.png");
            TextureRegionDrawable winSheepTrd = new TextureRegionDrawable(new TextureRegion(winSheep));
            Image winSheepImg = new Image(winSheepTrd);
            winSheepImg.setPosition(326,281);
            this.addActor(winSheepImg);*/

            Texture nextLevel = new Texture("Buttons/unpressed/NextLevelButton.png");
            Texture nextLevelPr = new Texture("Buttons/pressed/PressedNextLevelButton.png");
            TextureRegionDrawable nextLevelTrd = new TextureRegionDrawable(new TextureRegion(nextLevel));
            TextureRegionDrawable nextLevelPrTrd = new TextureRegionDrawable(new TextureRegion(nextLevelPr));

            ImageButton nextLevelButton = new ImageButton(nextLevelTrd,nextLevelPrTrd);
            nextLevelButton.setPosition(383,972);
            nextLevelButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    juego.setScreen(new MenuScreen(juego));
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
                    juego.setScreen(new LevelOne(juego));
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
                    juego.setScreen(new LevelOne(juego));
                }
            });
            this.addActor(levelsButton);


        }


    }
    // Escena para la pantalla de Perder -----------------------------------------------------------


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

            Texture gameText = new Texture("gameOver.png");
            TextureRegionDrawable trdGame = new TextureRegionDrawable(new TextureRegion(gameText));
            Image gameOver = new Image(trdGame);
            gameOver.setPosition(321,1415);
            this.addActor(gameOver);

            Texture deadSheep = new Texture("deadSheep.png");
            TextureRegionDrawable trdSheep =  new TextureRegionDrawable(new TextureRegion(deadSheep));
            Image sheep = new Image(trdSheep);
            sheep.setPosition(269,274);
            this.addActor(sheep);

            Texture homeButtonLost = new Texture("Buttons/unpressed/homeButtonLost.png");
            TextureRegionDrawable trdHome = new TextureRegionDrawable(new TextureRegion(homeButtonLost));
            TextureRegionDrawable trdHomePr = new TextureRegionDrawable(new TextureRegion(new Texture("Buttons/pressed/PressedHomeButtonLost.png")));
            ImageButton homeButton = new ImageButton(trdHome, trdHomePr);
            homeButton.setPosition(586,700);
            homeButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    juego.stopGameMusic();
                    juego.setScreen(new MenuScreen(juego));
                    juego.stopLost();

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
                    juego.stopGameMusic();
                    juego.setScreen(new LevelOne(juego));
                    juego.playGameMusic();
                    juego.stopLost();
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
                    juego.stopGameMusic();
                    juego.setScreen(new MenuScreen(juego));
                    juego.stopLost();
                }
            });
            this.addActor(lvsButton);


        }
    }

}
