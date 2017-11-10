package mx.itesm.sheep;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by josepablo on 11/10/17.
 */

public class LostScreen extends Stage {

    private Juego juego;

    public LostScreen(Viewport vista, SpriteBatch batch){

        super(vista,batch);

        Texture opaque = new Texture("opaque.png");
        TextureRegionDrawable trdOpaq = new TextureRegionDrawable(new TextureRegion(opaque));
        Image op = new Image(trdOpaq);
        op.setPosition(0,0);
        this.addActor(op);

        Texture lostRectangle = new Texture("lostRectangle.png");
        TextureRegionDrawable trdRect = new TextureRegionDrawable(new TextureRegion(lostRectangle));
        Image rect = new Image(trdRect);
        rect.setPosition(47,300);
        this.addActor(rect);

        Texture gameText = new Texture("gameOver.png");
        TextureRegionDrawable trdGame = new TextureRegionDrawable(new TextureRegion(gameText));
        Image gameOver = new Image(trdGame);
        gameOver.setPosition(321,1415);
        this.addActor(gameOver);

        Texture deadSheep = new Texture("deadSheep.png");
        TextureRegionDrawable trdSheep =  new TextureRegionDrawable(new TextureRegion(deadSheep));
        Image sheep = new Image(trdSheep);
        sheep.setPosition(269,274);
        this.addActor(sheep);

        Texture homeButtonLost = new Texture("buttons/unpressed/homeButtonLost.png");
        TextureRegionDrawable trdHome = new TextureRegionDrawable(new TextureRegion(homeButtonLost));
        TextureRegionDrawable trdHomePr = new TextureRegionDrawable(new TextureRegion(new Texture("buttons/pressed/PressedHomeButtonLost.png")));
        ImageButton homeButton = new ImageButton(trdHome, trdHomePr);
        homeButton.setPosition(586,700);
        homeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.stopGameMusic();
                juego.setScreen(new MenuScreen(juego));
                juego.stopLost();

            }
        });
        this.addActor(homeButton);

        Texture tryAgainButton = new Texture("buttons/unpressed/tryAgainButton.png");
        TextureRegionDrawable trdAgain = new TextureRegionDrawable(new TextureRegion(tryAgainButton));
        TextureRegionDrawable trdAgainpr = new TextureRegionDrawable(new TextureRegion(new Texture("buttons/pressed/PressedTryAgainButton.png")));
        ImageButton tryAgain = new ImageButton(trdAgain, trdAgainpr);
        tryAgain.setPosition(383,972);
        tryAgain.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.stopGameMusic();
                juego.setScreen(new GameScreen(juego));
                juego.playGameMusic();
                juego.stopLost();
            }
        });
        this.addActor(tryAgain);

        Texture levelsButton = new Texture("buttons/unpressed/levelsButtonLost.png");
        Texture levelsButtonpr = new Texture("buttons/pressed/PressedLevelsButtonLost.png");
        TextureRegionDrawable trdLevels = new TextureRegionDrawable(new TextureRegion(levelsButton));
        TextureRegionDrawable trdLevelspr = new TextureRegionDrawable(new TextureRegion(levelsButtonpr));
        ImageButton lvsButton = new ImageButton(trdLevels,trdLevelspr);
        lvsButton.setPosition(285,700);
        lvsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.stopGameMusic();
                juego.setScreen(new MenuScreen(juego));
                juego.stopLost();
            }
        });
        this.addActor(lvsButton);


    }
}
