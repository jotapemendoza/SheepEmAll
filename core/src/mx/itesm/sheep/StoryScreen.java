package mx.itesm.sheep;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by josepablo on 11/25/17.
 */

public class StoryScreen extends ScreenTemplate {

    private final SheepEm sheepEm;
    private Stage storyStage;

    public StoryScreen(SheepEm sheepEm){
        this.sheepEm = sheepEm;
    }

    @Override
    public void show() {
        loadTextures();
        createScene();
        Gdx.input.setInputProcessor(storyStage);
    }

    private void loadTextures() {
    }

    private void createScene() {
        storyStage = new Stage(view);
    }

    @Override
    public void render(float delta) {

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
