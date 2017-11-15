package mx.itesm.sheep;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by josepablo on 11/4/17.
 */

public class MapScreen extends MainScreen {

    private final Juego juego;
    private Stage escenaLevels;
    private Texture topBG;
    private Texture BG;
    private Texture lv1;
    private Texture lvlBtn;
    private Texture lv2;
    private Texture lv3;
    private Texture cloud;

    private Image cloud_1;
    private Image cloud_2;
    private Image cloud_3;
    private Image cloud_4;
    private Image cloud_5;


    private ImageButton levelTwoBtn, levelThreeBtn;


    public MapScreen(Juego juego){
        this.juego = juego;
    }
    @Override
    public void show() {
        cargarTexturas();
        crearEscenaSettings();
        Gdx.input.setInputProcessor(escenaLevels);

    }

    private void cargarTexturas() {
        BG = new Texture("menuBg.png");
        topBG = new Texture("mapsBG.png");
        lv1 = new Texture("levelsScreen/level1.png");
        lv2 = new Texture("levelsScreen/level2.png");
        lv3 = new Texture("levelsScreen/level3.png");
        lvlBtn = new Texture("levelsScreen/btn.png");
        cloud = new Texture("cloud.png");
    }

    private void crearEscenaSettings(){
        escenaLevels = new Stage(vista);


        TextureRegionDrawable trdBg = new TextureRegionDrawable(new TextureRegion(BG));
        Image bg =  new Image(trdBg);
        bg.setPosition(0,0);
        escenaLevels.addActor(bg);

        TextureRegionDrawable levelOneTrd = new TextureRegionDrawable(new TextureRegion(lvlBtn));
        ImageButton levelOneBtn = new ImageButton(levelOneTrd);
        levelOneBtn.setPosition(693,140);
        levelOneBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new GameScreen(juego));
                juego.pauseMenuMusic();
            }
        });
        escenaLevels.addActor(levelOneBtn);




        TextureRegionDrawable levelTwoTrd = new TextureRegionDrawable(new TextureRegion(lvlBtn));
        levelTwoBtn = new ImageButton(levelTwoTrd);
        levelTwoBtn.setPosition(130,431);
        levelTwoBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new LevelTwo(juego));
                juego.pauseMenuMusic();

            }
        });



        TextureRegionDrawable levelThreeTrd = new TextureRegionDrawable(new TextureRegion(lvlBtn));
        levelThreeBtn = new ImageButton(levelThreeTrd);
        levelThreeBtn.setPosition(546,706);
        levelThreeBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new LevelTwo(juego));
                juego.pauseMenuMusic();

            }
        });


        drawClouds();

    }

    private void drawClouds() {

        TextureRegionDrawable cloudTrd = new
                TextureRegionDrawable(new TextureRegion(cloud));

        // Moving clouds (from top to bottom) -----------------------------
        cloud_1 = new Image(cloud);
        cloud_1.setPosition(-450,1700);
        cloud_1.setSize(570,240);
        escenaLevels.addActor(cloud_1);

        cloud_2 = new Image(cloud);
        cloud_2.setPosition(1300,1500);
        cloud_2.setSize(340,140);
        escenaLevels.addActor(cloud_2);

        cloud_3 = new Image(cloud);
        cloud_3.setPosition(-200,1350);
        cloud_3.setSize(220,100);
        escenaLevels.addActor(cloud_3);

        cloud_4 = new Image(cloud);
        cloud_4.setPosition(1081,750);
        cloud_4.setSize(110,50);
        escenaLevels.addActor(cloud_4);

        cloud_5 = new Image(cloud);
        cloud_5.setPosition(1081,550);
        cloud_5.setSize(140,80);
        escenaLevels.addActor(cloud_5);
    }

    @Override
    public void render(float delta) {

        /*-----------------------------ESTAS LÍNEAS SOLAMENTE SON PARA PRUEBAS----------------------------------------------*/
        //pref.putBoolean("lv1",true);
        //pref.putBoolean("lv2",false);
        /*------------------------------------------------------------------------------------------------------------------*/

        Boolean levelOneWon = pref.getBoolean("lv1");
        Boolean levelTwoWon = pref.getBoolean("lv2");


        borrarPantalla(0,0,0);
        batch.setProjectionMatrix(camara.combined);

        escenaLevels.draw();
        moveClouds(delta);

        batch.begin();
        batch.draw(topBG,0,0);

        if(!pref.getBoolean("lv1")){
            batch.draw(lv1,78,116);
        }
        if(levelOneWon){
            batch.draw(lv2,78,116);
            escenaLevels.addActor(levelTwoBtn);
        }
        if(levelTwoWon){
            batch.draw(lv3,78,116);
            escenaLevels.addActor(levelThreeBtn);
        }

        batch.end();

        pref.flush();

        if(pref.getBoolean("musicOn")){
            juego.startMenuMusic();
        }
        if(!pref.getBoolean("musicOn")){
            juego.pauseMenuMusic();
        }



    }

    private void moveClouds(float delta) {
        cloud_1.setX(cloud_1.getX()+120*delta);
        if (cloud_1.getX() >= ANCHO){
            cloud_1.setX(-cloud_1.getWidth());
        }
        cloud_1.setColor(1,1,1,0.7f);

        cloud_2.setX(cloud_2.getX()+60*delta);
        if (cloud_2.getX() >= ANCHO){
            cloud_2.setX(-cloud_2.getWidth());
        }
        cloud_2.setColor(1,1,1,0.6f);

        cloud_3.setX(cloud_3.getX()+40*delta);
        if (cloud_3.getX() >= ANCHO){
            cloud_3.setX(-cloud_3.getWidth());
        }
        cloud_3.setColor(1,1,1,0.3f);

        cloud_4.setX(cloud_4.getX()+30*delta);
        if (cloud_4.getX() >= ANCHO){
            cloud_4.setX(-cloud_4.getWidth());
        }
        cloud_4.setColor(1,1,1,0.4f);

        cloud_5.setX(cloud_5.getX()+15*delta);
        if (cloud_5.getX() >= ANCHO){
            cloud_5.setX(-cloud_5.getWidth());
        }
        cloud_5.setColor(1,1,1,0.3f);
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
