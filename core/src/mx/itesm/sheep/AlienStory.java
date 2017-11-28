package mx.itesm.sheep;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


public class AlienStory extends ScreenTemplate {

    private final SheepEm sheepEm;
    private Stage storyStage;

    private Texture backgroundAlien;

    public AlienStory(SheepEm sheepEm){
        this.sheepEm = sheepEm;
    }

    @Override
    public void show() {
        loadTextures();
        createScene();
        Gdx.input.setInputProcessor(storyStage);
    }

    private void loadTextures() {
        backgroundAlien = new Texture("alien.png");
    }

    private void createScene() {

        storyStage = new Stage(view);
        TextureRegionDrawable trdBackground = new TextureRegionDrawable(new TextureRegion(backgroundAlien));
        Image imgBackground = new Image(trdBackground);
        imgBackground.setPosition(0,0);
        imgBackground.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                sheepEm.setScreen(new AlienLevel(sheepEm));
            }
        } );
        storyStage.addActor(imgBackground);
    }

    @Override
    public void render(float delta) {
        storyStage.draw();

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
