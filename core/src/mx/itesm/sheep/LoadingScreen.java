package mx.itesm.sheep;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Muestra una pantalla inicial durante cierto tiempo.
 */

class LoadingScreen extends ScreenTemplate
{
    private SheepEm sheepEm;
    private Texture sheep;
    private Texture background;
    private TextureRegion[] animationFrames;
    private Animation animation;
    float elapsedTime;


    public LoadingScreen(SheepEm sheepEm) {
        this.sheepEm = sheepEm;
    }
    @Override
    public void show() {
        sheep = new Texture("loadingScreenSprite.png");
        background = new Texture("loadingBG.png");
        TextureRegion[][] tmpFrames = TextureRegion.split(sheep,693,695);
        animationFrames = new TextureRegion[5];
        int index = 0;

        for (int i = 0; i < 5 ; i++) {
            animationFrames[index++] = tmpFrames[0][i];
        }

        animation = new Animation(1f/5f,animationFrames);
    }

    @Override
    public void render(float delta) {
        clearScreen(1, 1, 1);
        elapsedTime += Gdx.graphics.getDeltaTime();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background,0,0);
        TextureRegion sheeptr = (TextureRegion) animation.getKeyFrame(elapsedTime,true);
        batch.draw(sheeptr,207,829);
        batch.end();

        elapsedTime += Gdx.graphics.getDeltaTime();
        if (elapsedTime>=7) {
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