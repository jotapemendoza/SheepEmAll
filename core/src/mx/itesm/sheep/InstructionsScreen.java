package mx.itesm.sheep;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by josekotasek on 27/10/17.
 */

public class InstructionsScreen extends MainScreen {

    private final Juego juego;

    private Texture instrucciones;
    private Stage escenaInstrucciones;

    public InstructionsScreen(Juego juego){
        this.juego = juego;
    }
    @Override
    public void show() {
        cargarTexturas();
        crearEscenaInstructions();
        Gdx.input.setInputProcessor(escenaInstrucciones);
    }

    private void crearEscenaInstructions() {
        escenaInstrucciones = new Stage(vista);
        TextureRegionDrawable instr = new TextureRegionDrawable(new TextureRegion(instrucciones));
        final ImageButton botinstr = new ImageButton(instr);
        botinstr.setPosition(0,0);
        escenaInstrucciones.addActor(botinstr);
        botinstr.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new LevelOne(juego));
            }
        } );
    }

    private void cargarTexturas() {
        instrucciones = new Texture("empty.png");
    }

    @Override
    public void render(float delta) {
        borrarPantalla(0,0,0);
        batch.setProjectionMatrix(camara.combined);
        escenaInstrucciones.draw();
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
