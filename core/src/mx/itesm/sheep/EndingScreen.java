package mx.itesm.sheep;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


public class EndingScreen extends ScreenTemplate {

    private final SheepEm sheepEm;
    private Stage storyStage;

    private Texture background;

    private float elapsedTime;

    private Texture black;

    public EndingScreen(SheepEm sheepEm){
        this.sheepEm = sheepEm;
    }

    @Override
    public void show() {
        loadTextures();
        createScene();
        Gdx.input.setInputProcessor(storyStage);

    }

    private void loadTextures() {
        background = new Texture("finalScreen.png");
        black = new Texture("black.png");
    }

    private void createScene() {

        storyStage = new Stage(view);
        TextureRegionDrawable trdBackground = new TextureRegionDrawable(new TextureRegion(background));
        Image imgBackground = new Image(trdBackground);
        imgBackground.setPosition(0,0);
        storyStage.addActor(imgBackground);

    }

    @Override
    public void render(float delta) {

        elapsedTime += Gdx.graphics.getDeltaTime();

        batch.begin();

        batch.draw(black,0,0);

        batch.end();

        storyStage.draw();
        if(elapsedTime>=3.0){
            sheepEm.setScreen(new CreditsScreen(sheepEm));
        }


        if(pref.getBoolean("musicOn")){
            sheepEm.finalMusic.play();
            sheepEm.finalMusic.setLooping(true);
        }else{
            sheepEm.finalMusic.stop();
        }
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
