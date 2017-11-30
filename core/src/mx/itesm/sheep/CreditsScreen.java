package mx.itesm.sheep;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by josepablo on 11/25/17.
 */

public class CreditsScreen extends ScreenTemplate {

    private final SheepEm sheepEm;
    private Texture credits;
    private Stage creditsScene;
    private Image imgCredits;
    private float timer;
    private Texture black;

    public CreditsScreen(SheepEm sheepEm){
        this.sheepEm = sheepEm;
    }
    @Override
    public void show() {
        loadTextures();
        createScene();
    }

    private void loadTextures() {
        credits = new Texture("credits.png");
        black = new Texture("black.png");
    }

    private void createScene() {
        creditsScene = new Stage(view);

        TextureRegionDrawable trdCredits = new TextureRegionDrawable(new TextureRegion(credits));
        imgCredits = new Image(trdCredits);
        imgCredits.setPosition(0,-(1920*2));
        creditsScene.addActor(imgCredits);
    }

    @Override
    public void render(float delta) {



        batch.begin();

        batch.draw(black,0,0);

        batch.end();
        creditsScene.draw();
        batch.setProjectionMatrix(camera.combined);
        timer+= Gdx.graphics.getDeltaTime();

        if(timer>=0.013){
            imgCredits.setY(imgCredits.getY()+3);
            timer = 0;
        }

        if(imgCredits.getY()>=1330){
            sheepEm.finalMusic.stop();
            sheepEm.setScreen(new MenuScreen(sheepEm));
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
