package mx.itesm.sheep;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by josepablo on 11/28/17.
 */

public class Transition {

    private Texture black;
    private Image imgBlack;
    private Stage transitionStage;
    private float time;
    private float alpha;

    public Transition(){

        black = new Texture("black.png");
        TextureRegionDrawable trdBlack = new TextureRegionDrawable(new TextureRegion(black));
        imgBlack = new Image(trdBlack);
        imgBlack.setPosition(0,0);
        transitionStage.addActor(imgBlack);
    }

    public void render(SpriteBatch batch){
        transitionStage.draw();
        time+= Gdx.graphics.getDeltaTime();
        if(time>0.002){
            alpha-= 0.025;
            time = 0;
        }
        imgBlack.setColor(1,1,1,alpha);
    }
}
