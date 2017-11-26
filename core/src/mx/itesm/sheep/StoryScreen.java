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

import java.util.concurrent.TimeoutException;

/**
 * Created by josepablo on 11/25/17.
 */

public class StoryScreen extends ScreenTemplate{

    private SheepEm sheepEm;
    private Texture background;
    private Stage storyScene;
    private Texture clear;
    private ImageButton imgBackground;

    public StoryScreen(SheepEm sheepEm){
        this.sheepEm = sheepEm;
    }

    @Override
    public void show() {
        loadGraphics();
        createScene();
    }

    private void loadGraphics() {
        background = new Texture("story.png");
        clear = new Texture("clear.png");
    }

    private void createScene() {
        storyScene = new Stage(view);

        TextureRegionDrawable trdBack = new TextureRegionDrawable(new TextureRegion(background));
        imgBackground = new ImageButton(trdBack);
        imgBackground.setPosition(0,0);
        storyScene.addActor(imgBackground);
        imgBackground.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //camera.position.set(1200,1000,0);
                Gdx.app.log("Camera Position","Changed");
            }
        } );


    }



    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(camera.combined);
        storyScene.draw();
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
