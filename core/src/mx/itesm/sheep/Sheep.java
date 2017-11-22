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

    // Posición oveja
    private float x;
    private float y;

    // Multiplicador de Velocidad
    private float velocidad = 1;

    // Color de la oveja
    private String color;

    // Tamaño del sprite oveja
    private int ancho, alto;

    // Tamaño del sprite oveja levantada
    private int anchoM, altoM;

    // Tipo de oveja
    private String tipo;

    // Animar oveja
    private Animation animacion;
    private float timer;
    private Animation animacionMov;
    private Texture texturafuego;
    private Animation fuego;

    // Estados para movimiento
    private Estado estado;     // abajo, arriba, izquierda, derecha, stop
    private Estado estadoOriginal;
    private boolean seMovio = false;
    private boolean enLlamas = false;


    public Sheep(Texture textura, Texture textura2, Estado estado, String color, String tipo) {

        this.color = color;
        this.tipo = tipo;
        this.estadoOriginal = estado;
        this.estado = estado;

        TextureRegion region = new TextureRegion(textura);
        TextureRegion[][] frames;

        TextureRegion region2 = new TextureRegion(textura2);
        TextureRegion[][] frames2;

        texturafuego = new Texture("sheep_fire.png");
        TextureRegion region3 = new TextureRegion(texturafuego);
        TextureRegion[][] frames3= region3.split(100,170);
        fuego = new Animation(0.20f, frames3[0][0], frames3[0][1]);
        fuego.setPlayMode(Animation.PlayMode.LOOP);
        timer = 0;


        switch (estado){
            case ARRIBA:
                if (tipo.equals("RAINBOW")){
                    ancho = 108;
                    alto = 140;
                    frames = region.split(ancho,alto);
                    animacion = new Animation(0.20f, frames[0][0], frames[0][1], frames[0][2], frames[0][3]);
                    animacion.setPlayMode(Animation.PlayMode.LOOP);

                    anchoM = 114;
                    altoM = 127;
                    frames2 = region2.split(anchoM,altoM);
                    animacionMov = new Animation(0.20f, frames2[0][0], frames2[0][1]);
                    animacionMov.setPlayMode(Animation.PlayMode.LOOP);

                }else {
                    ancho = 108;
                    alto = 140;
                    frames = region.split(ancho,alto);
                    animacion = new Animation(0.20f, frames[0][0], frames[0][1]);
                    animacion.setPlayMode(Animation.PlayMode.LOOP);

                    anchoM = 114;
                    altoM = 127;
                    frames2 = region2.split(anchoM,altoM);
                    animacionMov = new Animation(0.20f, frames2[0][0], frames2[0][1]);
                    animacionMov.setPlayMode(Animation.PlayMode.LOOP);
                }

                timer = 0;
                x = 500 + ((int) (Math.random()*65)+1);
                y = 1921; //1900+ ((int) (Math.random()*4000));
                break;
            case ABAJO:
                ancho = 107;
                alto = 139;
                frames = region.split(ancho,alto);
                animacion = new Animation(0.20f, frames[0][0], frames[0][1]);
                animacion.setPlayMode(Animation.PlayMode.LOOP);

                anchoM = 114;
                altoM = 127;
                frames2 = region2.split(anchoM,altoM);
                animacionMov = new Animation(0.20f, frames2[0][0], frames2[0][1]);
                animacionMov.setPlayMode(Animation.PlayMode.LOOP);


                timer = 0;
                x = 500 + ((int) (Math.random()*65)+1);
                y = -140;//((int) (Math.random()*-4000));
                break;
            case IZQUIERDA:
                ancho = 156;
                alto = 116;
                frames = region.split(ancho,alto);
                animacion = new Animation(0.1f, frames[0][0], frames[0][1], frames[0][2]);
                animacion.setPlayMode(Animation.PlayMode.LOOP);

                anchoM = 127;
                altoM = 114;
                frames2 = region2.split(anchoM,altoM);
                animacionMov = new Animation(0.20f, frames2[0][0], frames2[1][0]);
                animacionMov.setPlayMode(Animation.PlayMode.LOOP);

                timer = 0;
                x = -this.ancho;//((int) (Math.random()*-4000));
                y = 800 + ((int) (Math.random()*65)+1);
                break;
            case DERECHA:
                ancho = 156;
                alto = 116;
                frames = region.split(ancho,alto);
                animacion = new Animation(0.1f, frames[0][0], frames[0][1], frames[0][2]);
                animacion.setPlayMode(Animation.PlayMode.LOOP);

                anchoM = 127;
                altoM = 114;
                frames2 = region2.split(anchoM,altoM);
                animacionMov = new Animation(0.20f, frames2[0][0], frames2[1][0]);
                animacionMov.setPlayMode(Animation.PlayMode.LOOP);

                timer = 0;
                x = 1081;//1080 + ((int) (Math.random()*4000));
                y = 800 + ((int) (Math.random()*65)+1);
                break;
            case MOVIENDO:
                break;
            case STOP:
                break;
            case CONTINUAR:
                break;
            case BOOM:
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
                    timer = 0;
                    batch.draw(region, x, y);
                }
                break;
            case ABAJO:
                if (!seMovio){
                    if(y <= 1920){
                        batch.draw(region, x, y);
                        y = y + velocidad;
                    }
                }else {
                    timer = 0;
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
                    timer = 0;
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
                    timer = 0;
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
            case BORRAR:
                break;
        }
    }

    public boolean comparar(float xt, float yt) {
        if (xt >= x && xt <= x+ancho && yt >= y && yt <= y+alto){
            return true;
        }
        return false;
    }

    // Validar corral nave alien -------------------------------------------------------------------
    public boolean cordenadasCorralAlien(float xP, float yP, String tipo, AlienShip ship){
        if (xP >= ship.getPosicionX() && xP <= ship.getDireccionX()+ship.getAncho()
                && yP >= ship.getPosicionY() && yP <= ship.getPosicionY()+ship.getAlto()
                && tipo.equals("ALIEN")){
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
        BOOM,
        BORRAR
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

    public String getTipo() { return tipo; }
}
