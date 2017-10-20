package mx.itesm.sheep;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Andreu on 15/10/17.
 */

public class Sheep {

    // Posición oveja Arriba
    private float xA = 550;
    private float yA = 1900;

    // Posición oveja Abajo
    private float xAB = 550;
    private float yAB = 0;

    // Posición oveja Derecha
    private float xD = 1080;
    private float yD = 950;

    // Posición oveja Izquierda
    private float xI = 0;
    private float yI = 950;

    // Animar oveja
    private Animation animacion;
    private float timer;

    // Estados para movimiento
    private Estado estado;     // abajo, arriba, izquierda, derecha
    private float timerEstado = 0;


    public Sheep(Texture textura, Estado estado) {
        TextureRegion region = new TextureRegion(textura);
        TextureRegion[][] frames;

        switch (estado){
            case ARRIBA:
                frames = region.split(32,64);
                animacion = new Animation(0.20f, frames[0][0], frames[0][1], frames[0][2]);
                animacion.setPlayMode(Animation.PlayMode.LOOP);
                timer = 0;
                this.estado = estado;
                break;
            case ABAJO:
                frames = region.split(32,64);
                animacion = new Animation(0.20f, frames[0][0], frames[0][1], frames[0][2]);
                animacion.setPlayMode(Animation.PlayMode.LOOP);
                timer = 0;
                this.estado = estado;
                break;
            case IZQUIERDA:
                frames = region.split(42,64);
                animacion = new Animation(0.20f, frames[0][0], frames[0][1], frames[0][2]);
                animacion.setPlayMode(Animation.PlayMode.LOOP);
                timer = 0;
                this.estado = estado;
                break;
            case DERECHA:
                frames = region.split(42,64);
                animacion = new Animation(0.20f, frames[0][0], frames[0][1], frames[0][2]);
                animacion.setPlayMode(Animation.PlayMode.LOOP);
                timer = 0;
                this.estado = estado;
                break;
        }
    }

    public void render (SpriteBatch batch) {
        timer += Gdx.graphics.getDeltaTime();
        TextureRegion region = (TextureRegion) animacion.getKeyFrame(timer);
        switch (estado){
            case ARRIBA:
                if(yA > 0){
                    batch.draw(region, xA, yA);
                    yA--;
                }else{
                    yAB = 0;
                    estado = Estado.ABAJO;
                }
                break;
            case ABAJO:
                if(yAB <= 1900){
                    batch.draw(region, xAB, yAB);
                    yAB++;
                }else{
                    yA = 1900;
                    estado = Estado.ARRIBA;
                }
                break;
            case IZQUIERDA:
                if (xI < 1080){
                    batch.draw(region, xI, yI);
                    xI++;
                }else{
                    xD = 1080;
                    estado = Estado.DERECHA;
                }
                break;
            case DERECHA:
                if (xD > 0){
                    batch.draw(region, xD, yD);
                    xD--;
                }else{
                    xI = 0;
                    estado = Estado.IZQUIERDA;
                }
                break;
        }
    }

    public enum Estado{
        ARRIBA,
        ABAJO,
        IZQUIERDA,
        DERECHA
    }

}
