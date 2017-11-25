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


public class EndingScreen extends ScreenTemplate {

    private final SheepEm sheepEm;
    private Stage storyStage;

    private Texture background;

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
    }

    private void createScene() {

        storyStage = new Stage(view);

        TextureRegionDrawable trdBackground = new TextureRegionDrawable(new TextureRegion(background));
        Image imgBackground = new Image(trdBackground);
        imgBackground.setPosition(0,0);
        storyStage.addActor(imgBackground);

        TextureRegionDrawable trdHome = new TextureRegionDrawable(new TextureRegion(new Texture("Buttons/unpressed/homeButton.png")));
        TextureRegionDrawable trdPressedHome = new TextureRegionDrawable(new TextureRegion(new Texture("Buttons/pressed/pressedHomeButton.png")));

        ImageButton button = new ImageButton(trdHome,trdPressedHome);
        button.setPosition(WIDTH/2,HEIGHT/2);
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sheepEm.setScreen(new MenuScreen(sheepEm));

            }
        });
        storyStage.addActor(button);
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
