package mx.itesm.sheep;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Andreu on 15/10/17.
 */

public class Oveja {

    // Posición oveja Arriba
    private float xA;
    private float yA;

    // Posición oveja Abajo
    private float xAB;
    private float yAB;

    // Posición oveja Derecha
    private float xD;
    private float yD;

    // Posición oveja Izquierda
    private float xI;
    private float yI;

    // Animar oveja
    private Animation animacion;
    private float timer;

    // Estados para movimiento
    private Estado estado;     // abajo, arriba, izquierda, derecha
    private float timerEstado = 0;


    public Oveja(Texture textura, Estado estado) {
        TextureRegion region = new TextureRegion(textura);
        TextureRegion[][] frames;

        switch (estado){
            case ARRIBA:
                frames = region.split(32,64);
                animacion = new Animation(0.20f, frames[0][0], frames[0][1], frames[0][2]);
                animacion.setPlayMode(Animation.PlayMode.LOOP);
                timer = 0;
                xA = 500 + ((int) (Math.random()*100)+1);
                yA = 1900 + ((int) (Math.random()*4000));
                this.estado = estado;
                break;
            case ABAJO:
                frames = region.split(32,64);
                animacion = new Animation(0.20f, frames[0][0], frames[0][1], frames[0][2]);
                animacion.setPlayMode(Animation.PlayMode.LOOP);
                timer = 0;
                xAB = 500 + ((int) (Math.random()*100)+1);
                yAB = 1 + ((int) (Math.random()*-4000));
                this.estado = estado;
                break;
            case IZQUIERDA:
                frames = region.split(42,64);
                animacion = new Animation(0.20f, frames[0][0], frames[0][1], frames[0][2]);
                animacion.setPlayMode(Animation.PlayMode.LOOP);
                timer = 0;
                xI = 1 + ((int) (Math.random()*-4000));
                yI = 800 + ((int) (Math.random()*100)+1);
                this.estado = estado;
                break;
            case DERECHA:
                frames = region.split(42,64);
                animacion = new Animation(0.20f, frames[0][0], frames[0][1], frames[0][2]);
                animacion.setPlayMode(Animation.PlayMode.LOOP);
                timer = 0;
                xD = 1080 + ((int) (Math.random()*4000));
                yD = 800 + ((int) (Math.random()*100)+1);
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
                    /*yAB = 0;
                    estado = Estado.ABAJO;*/
                }
                break;
            case ABAJO:
                if(yAB <= 1900){
                    batch.draw(region, xAB, yAB);
                    yAB++;
                }else{
                    /*yA = 1900;
                    estado = Estado.ARRIBA;*/
                }
                break;
            case IZQUIERDA:
                if (xI < 1080){
                    batch.draw(region, xI, yI);
                    xI++;
                }else{
                    /*xD = 1080;
                    estado = Estado.DERECHA;*/
                }
                break;
            case DERECHA:
                if (xD > 0){
                    batch.draw(region, xD, yD);
                    xD--;
                }else{
                   /* xI = 0;
                    estado = Estado.IZQUIERDA;*/
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

    public float getyA() {
        return yA;
    }

    public float getyAB() {
        return yAB;
    }

    public float getxD() {
        return xD;
    }

    public float getxI() {
        return xI;
    }

    public Estado getEstado() {
        return estado;
    }
}
