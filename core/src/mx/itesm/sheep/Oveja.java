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
    // Multiplicador de Velocidad
    private float velocidad = 1;

    private String color;

    private int ancho, alto;

    // Animar oveja
    private Animation animacion;
    private float timer;
    private Texture texturaMov;
    private Animation animacionMov;
    private Texture texturafuego;
    private Animation fuego;

    // Estados para movimiento
    private Estado estado;     // abajo, arriba, izquierda, derecha, stop
    private Estado estadoOriginal;
    private float timerEstado = 0;
    private boolean seMovio = false;
    private boolean enLlamas = false;


    public Oveja(Texture textura, Estado estado, String color) {
        this.color = color;
        TextureRegion region = new TextureRegion(textura);
        TextureRegion[][] frames;

        texturaMov = new Texture("sheep_moving.png");
        TextureRegion region2 = new TextureRegion(texturaMov);
        TextureRegion[][] frames2 = region2.split(114,127);
        animacionMov = new Animation(0.20f, frames2[0][0], frames2[0][1]);
        animacionMov.setPlayMode(Animation.PlayMode.LOOP);

        texturafuego = new Texture("fire.png");
        TextureRegion region3 = new TextureRegion(texturafuego);
        TextureRegion[][] frames3= region3.split(58,70);
        fuego = new Animation(0.20f, frames3[0][0], frames3[0][1], frames3[0][2]);
        fuego.setPlayMode(Animation.PlayMode.LOOP);
        timer = 0;


        switch (estado){
            case ARRIBA:
                ancho = 85;
                alto = 100;
                frames = region.split(ancho,alto);
                animacion = new Animation(0.20f, frames[0][0], frames[0][1]);
                animacion.setPlayMode(Animation.PlayMode.LOOP);
                timer = 0;
                x = 500 + ((int) (Math.random()*100)+1);
                y = 1900+ ((int) (Math.random()*4000));
                this.estadoOriginal = estado;
                this.estado = estado;
                break;
            case ABAJO:
                ancho = 64;
                alto = 75;
                frames = region.split(ancho,alto);
                animacion = new Animation(0.20f, frames[0][0], frames[0][1]);
                animacion.setPlayMode(Animation.PlayMode.LOOP);
                timer = 0;
                x = 500 + ((int) (Math.random()*100)+1);
                y = ((int) (Math.random()*-4000));
                this.estadoOriginal = estado;
                this.estado = estado;
                break;
            case IZQUIERDA:
                ancho = 76;
                alto = 70;
                frames = region.split(ancho,alto);
                animacion = new Animation(0.20f, frames[0][0], frames[0][1], frames[0][2]);
                animacion.setPlayMode(Animation.PlayMode.LOOP);
                timer = 0;
                x = ((int) (Math.random()*-4000));
                y = 800 + ((int) (Math.random()*100)+1);
                this.estadoOriginal = estado;
                this.estado = estado;
                break;
            case DERECHA:
                ancho = 76;
                alto = 70;
                frames = region.split(ancho,alto);
                animacion = new Animation(0.20f, frames[0][0], frames[0][1], frames[0][2]);
                animacion.setPlayMode(Animation.PlayMode.LOOP);
                timer = 0;
                x = 1080 + ((int) (Math.random()*4000));
                y = 800 + ((int) (Math.random()*100)+1);
                this.estadoOriginal = estado;
                this.estado = estado;
                break;
        }
    }

    public void render (SpriteBatch batch) {
        timer += Gdx.graphics.getDeltaTime();
        TextureRegion region = (TextureRegion) animacion.getKeyFrame(timer);
        TextureRegion region2 = (TextureRegion) animacionMov.getKeyFrame(timer);
        TextureRegion region3 = (TextureRegion) fuego.getKeyFrame(timer);
        switch (estado){
            case ARRIBA:
                if (!seMovio){
                    if(y > 0){
                        batch.draw(region, x, y);
                        y = y - velocidad;
                    }
                }else {
                    batch.draw(region, x, y);
                }
                break;
            case ABAJO:
                if (!seMovio){
                    if(y <= 1900){
                        batch.draw(region, x, y);
                        y = y + velocidad;
                    }
                }else {
                    batch.draw(region, x, y);
                }
                break;
            case IZQUIERDA:
                if (!seMovio){
                    if (x < 1080){
                        batch.draw(region, x, y);
                        x = x + velocidad;
                    }
                }else {
                    batch.draw(region, x, y);
                }
                break;
            case DERECHA:
                if (!seMovio){
                    if (x > 0){
                        batch.draw(region, x, y);
                        x = x - velocidad;
                    }
                }else {
                    batch.draw(region, x, y);
                }
                break;
            case MOVIENDO:
                batch.draw(region2, x, y);
                seMovio = true;
                break;
            case STOP:
                if (!enLlamas){
                    timer = 0;
                    batch.draw(region, x, y);
                }else{
                    timer = 0;
                    batch.draw(region3, x, y);
                }
                break;
            case CONTINUAR:
                if (!enLlamas){
                    this.estado = estadoOriginal;
                }else{
                    batch.draw(region3, x, y);
                }
                break;
            case BOOM:
                batch.draw(region3, x, y);
                enLlamas = true;
                break;
        }
    }

    public boolean comparar(float xt, float yt) {
        if (xt >= x && xt <= x+ancho && yt >= y && yt <= y+alto){
            return true;
        }
        return false;
    }


    public enum Estado{
        ARRIBA,
        ABAJO,
        IZQUIERDA,
        DERECHA,
        MOVIENDO,
        STOP,
        CONTINUAR,
        BOOM
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

    public Estado getEstadoOriginal() { return estadoOriginal; }

    public int getAncho() { return ancho; }

    public int getAlto() { return alto; }

    public String getColor() { return color; }

    public boolean isEnLlamas() { return enLlamas; }

    public void setVelocidad(float velocidad) { this.velocidad = velocidad; }

    public void setSeMovio(boolean seMovio) { this.seMovio = seMovio; }
}
