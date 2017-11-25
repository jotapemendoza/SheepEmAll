package mx.itesm.sheep;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


public class EndingScreen extends ScreenTemplate {

    private final SheepEm sheepEm;
    private Stage storyStage;

    private Texture background;

    private TextureRegion[] animationFrames;
    private Animation animation;
    float elapsedTime;
    private Texture fadeIn;
    private ImageButton button;

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
        int index = 0;

        for (int i = 6; i >= 0 ; i--) {
            animationFrames[index++] = tmpFrames[0][i];
        }

        animation = new Animation(1f/10f,animationFrames);
    }

    private void loadTextures() {
        background = new Texture("finalScreen.png");
    }

    private void createScene() {

        storyStage = new Stage(view);

        TextureRegionDrawable trdBackground = new TextureRegionDrawable(new TextureRegion(background));
        Image imgBackground = new Image(trdBackground);
        imgBackground.setPosition(0,0);
        storyStage.addActor(imgBackground);

        TextureRegionDrawable trdHome = new TextureRegionDrawable(new TextureRegion(new Texture("Buttons/unpressed/homeButton.png")));
        TextureRegionDrawable trdPressedHome = new TextureRegionDrawable(new TextureRegion(new Texture("Buttons/pressed/pressedHomeButton.png")));

        button = new ImageButton(trdHome,trdPressedHome);
        button.setPosition(WIDTH/2,HEIGHT/2);
        storyStage.addActor(button);
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sheepEm.setScreen(new CreditsScreen(sheepEm));
            }
        });
    }

    @Override
    public void render(float delta) {
        storyStage.draw();

        batch.begin();

        elapsedTime += Gdx.graphics.getDeltaTime();
        TextureRegion sheeptr = (TextureRegion) animation.getKeyFrame(elapsedTime,false);
        batch.draw(sheeptr,0,0);

        batch.end();


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
