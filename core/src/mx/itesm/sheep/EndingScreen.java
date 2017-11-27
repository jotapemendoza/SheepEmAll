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

    private TextureRegion[] animationFrames;
    private TextureRegion[] animationFrames2;
    private Animation fadeInAnimation;
    private Animation fadeOutAnimation;
    private float elapsedTime;
    private float elapsedTime2;
    private Texture fadeIn;
    private ImageButton button;
    private Texture black;

    public EndingScreen(SheepEm sheepEm){
        this.sheepEm = sheepEm;
    }

    @Override
    public void show() {
        loadTextures();
        createScene();
        Gdx.input.setInputProcessor(storyStage);

        fadeIn = new Texture("fadeIn.png");


        TextureRegion[][] tmpFrames = TextureRegion.split(fadeIn,1080,1920);

        animationFrames = new TextureRegion[7];
        animationFrames2 = new TextureRegion[7];

        int index = 0;

        for (int i = 6; i >= 0 ; i--) {
            animationFrames[index++] = tmpFrames[0][i];
        }

        index = 0;
        for (int i = 0; i < 7; i++) {
            animationFrames2[index++] = tmpFrames[0][i];
        }


        fadeOutAnimation = new Animation(1f/15f,animationFrames2);
        fadeInAnimation = new Animation(1f/15f,animationFrames);
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

        batch.begin();

        elapsedTime += Gdx.graphics.getDeltaTime();
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
