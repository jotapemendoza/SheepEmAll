package mx.itesm.sheep;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public abstract class ScreenTemplate implements com.badlogic.gdx.Screen {

    public static final float WIDTH = 1080;
    public static final float HEIGHT = 1920;

    protected OrthographicCamera camera;
    protected Viewport view;
    protected Preferences pref;
    protected SpriteBatch batch;

    public ScreenTemplate() {
        pref = Gdx.app.getPreferences("My Preferences");
        camera = new OrthographicCamera(WIDTH, HEIGHT);
        camera.position.set(WIDTH / 2, HEIGHT / 2, 0);
        camera.update();
        view = new StretchViewport(WIDTH, HEIGHT, camera);
        batch = new SpriteBatch();
    }

    protected void clearScreen() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    protected void clearScreen(float r, float g, float b) {
        Gdx.gl.glClearColor(r,g,b,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {
        view.update(width, height);
    }

    @Override
    public void hide() {
        dispose();
    }
}
