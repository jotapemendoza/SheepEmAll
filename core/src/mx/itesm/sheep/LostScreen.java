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
 * Created by josepablo on 11/20/17.
 */
public class LostScreen extends Stage {

    SheepEm sheepEm;

    public LostScreen(Viewport vista, SpriteBatch batch, final Integer level){

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


        Texture homeButtonLost = new Texture("Buttons/unpressed/homeButtonLost.png");
        TextureRegionDrawable trdHome = new TextureRegionDrawable(new TextureRegion(homeButtonLost));
        TextureRegionDrawable trdHomePr = new TextureRegionDrawable(new TextureRegion(new Texture("Buttons/pressed/PressedHomeButtonLost.png")));
        ImageButton homeButton = new ImageButton(trdHome, trdHomePr);
        homeButton.setPosition(586,700);
        homeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sheepEm.stopGameMusic();
                sheepEm.setScreen(new MenuScreen(sheepEm));
                sheepEm.stopLost();

            }
        });
        this.addActor(homeButton);

        Texture tryAgainButton = new Texture("Buttons/unpressed/tryAgainButton.png");
        TextureRegionDrawable trdAgain = new TextureRegionDrawable(new TextureRegion(tryAgainButton));
        TextureRegionDrawable trdAgainpr = new TextureRegionDrawable(new TextureRegion(new Texture("Buttons/pressed/PressedTryAgainButton.png")));
        ImageButton tryAgain = new ImageButton(trdAgain, trdAgainpr);
        tryAgain.setPosition(383,972);
        tryAgain.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sheepEm.stopGameMusic();

                    sheepEm.setScreen(new LevelOne(sheepEm));

               /* if (level==2){
                    sheepEm.setScreen(new LevelTwo(sheepEm));
                }
                if (level==3){
                    sheepEm.setScreen(new AlienLevel(sheepEm));
                }*/

                sheepEm.playGameMusic();
                sheepEm.stopLost();
            }
        });
        this.addActor(tryAgain);

        Texture levelsButton = new Texture("Buttons/unpressed/levelsButtonLost.png");
        Texture levelsButtonpr = new Texture("Buttons/pressed/PressedLevelsButtonLost.png");
        TextureRegionDrawable trdLevels = new TextureRegionDrawable(new TextureRegion(levelsButton));
        TextureRegionDrawable trdLevelspr = new TextureRegionDrawable(new TextureRegion(levelsButtonpr));
        ImageButton lvsButton = new ImageButton(trdLevels,trdLevelspr);
        lvsButton.setPosition(285,700);
        lvsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sheepEm.stopGameMusic();
                sheepEm.setScreen(new MapScreen(sheepEm));
                sheepEm.stopLost();
            }
        });
        this.addActor(lvsButton);


    }
}
