package mx.itesm.sheep;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by josepablo on 11/4/17.
 */

public class MapScreen extends ScreenTemplate {

    private final SheepEm sheepEm;
    private Stage escenaLevels;

    private Texture grass,
            background,
            levelOne,
            lvlBtn,
            levelTwo,
            levelThree,
            cloud,
            text,
            backButton,
            pressedBackButton;

    private Image cloud_1,
            cloud_2,
            cloud_3,
            cloud_4,
            cloud_5;

    private ImageButton levelTwoBtn,
            levelThreeBtn;
    private Texture nightBackground;
    private Texture arcade;
    private Texture arcadeBtn;
    private ImageButton arcadeButton;


    public MapScreen(SheepEm sheepEm){
        this.sheepEm = sheepEm;
    }
    @Override
    public void show() {
        cargarTexturas();
        crearEscenaMapa();
        Gdx.input.setInputProcessor(escenaLevels);
        Gdx.input.setCatchBackKey(true);
    }

    private void cargarTexturas() {
        pressedBackButton = new Texture("Buttons/pressed/pressedBackButton.png");
        backButton = new Texture("Buttons/unpressed/backButton.png");
        arcade = new Texture("levelsScreen/arcade.png");
        levelThree = new Texture("levelsScreen/level3_arcade.png");
        levelOne = new Texture("levelsScreen/level1_arcade.png");
        levelTwo = new Texture("levelsScreen/level2_arcade.png");
        lvlBtn = new Texture("levelsScreen/btn.png");
        arcadeBtn = new Texture("arcade_button.png");
        background = new Texture("dayBG.png");
        nightBackground = new Texture("nightBG.png");
        grass = new Texture("mapsBGtest.png");
        cloud = new Texture("cloud.png");
        text = new Texture("chooseText.png");
    }

    private void crearEscenaMapa(){

        escenaLevels = new Stage(view);

        escenaLevels.addListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode== Input.Keys.BACK){
                    sheepEm.setScreen(new MenuScreen(sheepEm));
                }
                return true;
            }
        });

        TextureRegionDrawable nightBg = new TextureRegionDrawable(new TextureRegion(nightBackground));

        TextureRegionDrawable trdBg = new TextureRegionDrawable(new TextureRegion(background));
        Image bg =  new Image(trdBg);
        bg.setPosition(0,725);
        escenaLevels.addActor(bg);

        TextureRegionDrawable levelOneTrd = new TextureRegionDrawable(new TextureRegion(lvlBtn));
        ImageButton levelOneBtn = new ImageButton(levelOneTrd);
        levelOneBtn.setPosition(693,140);
        levelOneBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sheepEm.setScreen(new LevelOne(sheepEm));
                sheepEm.pauseMenuMusic();
                pref.flush();

            }
        });
        escenaLevels.addActor(levelOneBtn);

        TextureRegionDrawable levelTwoTrd = new TextureRegionDrawable(new TextureRegion(lvlBtn));
        levelTwoBtn = new ImageButton(levelTwoTrd);
        levelTwoBtn.setPosition(130,431);
        levelTwoBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sheepEm.setScreen(new LevelTwo(sheepEm));
                sheepEm.pauseMenuMusic();
                pref.flush();
            }
        });

        TextureRegionDrawable levelThreeTrd = new TextureRegionDrawable(new TextureRegion(lvlBtn));
        levelThreeBtn = new ImageButton(levelThreeTrd);
        levelThreeBtn.setPosition(546,706);
        levelThreeBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sheepEm.setScreen(new LevelThree(sheepEm));
                sheepEm.pauseMenuMusic();
                pref.flush();
            }
        });

        TextureRegionDrawable arcadeTrd = new TextureRegionDrawable(new TextureRegion(arcadeBtn));
        arcadeButton = new ImageButton(arcadeTrd);
        arcadeButton.setPosition(160,805);
        arcadeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sheepEm.setScreen(new ArcadeMode(sheepEm));
                sheepEm.pauseMenuMusic();
                pref.flush();
            }
        });


        drawClouds();

        TextureRegionDrawable trdBack = new TextureRegionDrawable(new TextureRegion(backButton));
        TextureRegionDrawable trdBackpr = new TextureRegionDrawable(new TextureRegion(pressedBackButton));
        ImageButton btnBack = new ImageButton(trdBack, trdBackpr);
        btnBack.setPosition(50,1720);
        btnBack.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                sheepEm.setScreen(new MenuScreen(sheepEm));
            }
        } );
        escenaLevels.addActor(btnBack);

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

        clearScreen(0,0,0);
        batch.setProjectionMatrix(camera.combined);

        escenaLevels.draw();
        moveClouds(delta);


        /*----------------------- TESTING ---------------------*/


        /*------------------------BATCH---------------------*/
        batch.begin();

        batch.draw(grass,0,0);
        batch.draw(text,227,1450);

        if(!pref.getBoolean("wonLevelOne")){
            batch.draw(levelOne,78,116);
        }
        if(pref.getBoolean("wonLevelOne")){
            batch.draw(levelTwo,78,116);
            escenaLevels.addActor(levelTwoBtn);
        }
        if(pref.getBoolean("wonLevelTwo")){
            batch.draw(levelThree,78,116);
            escenaLevels.addActor(levelThreeBtn);
        }
        if(!pref.getBoolean("arcade")){ // prueba
            batch.draw(arcade,78,116);
            escenaLevels.addActor(arcadeButton);
        }

        batch.end();

        if(pref.getBoolean("musicOn")){
            sheepEm.startMenuMusic();

        }
        if(!pref.getBoolean("musicOn")){
            sheepEm.pauseMenuMusic();
        }
    }

    private void moveClouds(float delta) {
        cloud_1.setX(cloud_1.getX()+120*delta);
        if (cloud_1.getX() >= WIDTH){
            cloud_1.setX(-cloud_1.getWidth());
        }
        cloud_1.setColor(1,1,1,0.7f);

        cloud_2.setX(cloud_2.getX()+60*delta);
        if (cloud_2.getX() >= WIDTH){
            cloud_2.setX(-cloud_2.getWidth());
        }
        cloud_2.setColor(1,1,1,0.6f);

        cloud_3.setX(cloud_3.getX()+40*delta);
        if (cloud_3.getX() >= WIDTH){
            cloud_3.setX(-cloud_3.getWidth());
        }
        cloud_3.setColor(1,1,1,0.3f);

        cloud_4.setX(cloud_4.getX()+30*delta);
        if (cloud_4.getX() >= WIDTH){
            cloud_4.setX(-cloud_4.getWidth());
        }
        cloud_4.setColor(1,1,1,0.4f);

        cloud_5.setX(cloud_5.getX()+15*delta);
        if (cloud_5.getX() >= WIDTH){
            cloud_5.setX(-cloud_5.getWidth());
        }
        cloud_5.setColor(1,1,1,0.3f);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
    }
}
