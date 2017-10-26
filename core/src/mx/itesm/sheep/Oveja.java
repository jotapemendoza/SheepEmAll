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

    // Posición oveja
    private float x;
    private float y;

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
                x = 500 + ((int) (Math.random()*100)+1);
                y = 1900+ ((int) (Math.random()*4000));
                this.estado = estado;
                break;
            case ABAJO:
                frames = region.split(32,64);
                animacion = new Animation(0.20f, frames[0][0], frames[0][1], frames[0][2]);
                animacion.setPlayMode(Animation.PlayMode.LOOP);
                timer = 0;
                x = 500 + ((int) (Math.random()*100)+1);
                y = ((int) (Math.random()*-4000));
                this.estado = estado;
                break;
            case IZQUIERDA:
                frames = region.split(42,64);
                animacion = new Animation(0.20f, frames[0][0], frames[0][1], frames[0][2]);
                animacion.setPlayMode(Animation.PlayMode.LOOP);
                timer = 0;
                x = ((int) (Math.random()*-4000));
                y = 800 + ((int) (Math.random()*100)+1);
                this.estado = estado;
                break;
            case DERECHA:
                frames = region.split(140,116);
                animacion = new Animation(0.20f, frames[0][0], frames[0][1], frames[0][2]);
                animacion.setPlayMode(Animation.PlayMode.LOOP);
                timer = 0;
                x = 1080 + ((int) (Math.random()*4000));
                y = 800 + ((int) (Math.random()*100)+1);
                this.estado = estado;
                break;
        }
    }

    public void render (SpriteBatch batch) {
        timer += Gdx.graphics.getDeltaTime();
        TextureRegion region = (TextureRegion) animacion.getKeyFrame(timer);
        switch (estado){
            case ARRIBA:
                if(y > 0){
                    batch.draw(region, x, y);
                    y--;
                }else{
                    /*yAB = 0;
                    estado = Estado.ABAJO;*/
                }
                break;
            case ABAJO:
                if(y <= 1900){
                    batch.draw(region, x, y);
                    y++;
                }else{
                    /*yA = 1900;
                    estado = Estado.ARRIBA;*/
                }
                break;
            case IZQUIERDA:
                if (x < 1080){
                    batch.draw(region, x, y);
                    x++;
                }else{
                    /*xD = 1080;
                    estado = Estado.DERECHA;*/
                }
                break;
            case DERECHA:
                if (x > 0){
                    batch.draw(region, x, y);
                    x--;
                }else{
                   /* xI = 0;
                    estado = Estado.IZQUIERDA;*/
                }
                break;
            case MOVIENDO:
                batch.draw(region, x, y);
        }
    }

    public enum Estado{
        ARRIBA,
        ABAJO,
        IZQUIERDA,
        DERECHA,
        MOVIENDO
    }

    public float getx() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float gety() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado){
        this.estado = estado;
    }

}
