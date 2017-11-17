package mx.itesm.sheep;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Andreu on 13/11/17.
 */

public class AlienShip {

    // Mover direcciones
    private float posicionX = 0;
    private float posicionY = 0;
    private float direccionX = 1;
    private float direccionY = 1;

    // Tamaño
    private int alto = 424;
    private int ancho = 691;

    // Animación
    private Animation animacion;
    private float timer;

    // Vida
    private int hp=30;

    // Estado
    private Estado estado;

    public AlienShip(Texture textura, Estado estado){
        TextureRegion region = new TextureRegion(textura);
        TextureRegion[][]  frames = region.split(ancho,alto);
        animacion = new Animation(0.20f, frames[0][0]);
        animacion.setPlayMode(Animation.PlayMode.LOOP);
        timer = 0;

        this.estado = estado;
    }

    public void render (SpriteBatch batch) {
        timer += Gdx.graphics.getDeltaTime();
        TextureRegion region = (TextureRegion) animacion.getKeyFrame(timer);
        switch (estado){
            case MOVIENDO:
                batch.draw(region, posicionX, posicionY);
                break;
            case PAUSADO:
                break;
            case SALIENDOX:
                batch.draw(region, posicionX, posicionY);
                break;
            case SALIENDOY:
                batch.draw(region, posicionX, posicionY);
                break;
            case DERROTA:
                break;
        }
    }

    // mueve la nave
    public void spaceShipMove(float xM, float yM){
        posicionX = xM;
        posicionY = yM;
    }

    // invierte la dirección en x
    public void cambiarDireccionX(){
        direccionX *= -1;
    }

    // invierte la dirección en y
    public void cambiarDireccionY(){
        direccionY *= -1;
    }

    public Estado saliendoPor() {
        if (this.posicionX >= 1080-this.ancho ){
            this.setEstado(Estado.SALIENDOX);
            this.posicionX = 1080-this.ancho;
            return this.getEstado();
        }
        if (this.posicionY >=1920-this.alto){
            this.setEstado(Estado.SALIENDOY);
            this.posicionY = 1920-this.alto;
            return this.getEstado();
        }
        if (this.posicionX <= 0 ){
            this.setEstado(Estado.SALIENDOX);
            this.posicionX = 0;
            return this.getEstado();
        }
        if (this.posicionY <=0 ){
            this.setEstado(Estado.SALIENDOY);
            this.posicionY = 0;
            return this.getEstado();
        }
        else {
            return Estado.PAUSADO;
        }
    }

    public float getDireccionX() {
        return direccionX;
    }

    public float getDireccionY() {
        return direccionY;
    }

    public enum Estado{
        MOVIENDO,
        PAUSADO,
        SALIENDOX,
        SALIENDOY,
        DERROTA
    }

    public float getPosicionX() {
        return posicionX;
    }

    public void setPosicionX(float posicionX) {
        this.posicionX = posicionX;
    }

    public float getPosicionY() {
        return posicionY;
    }

    public void setPosicionY(float posicionY) {
        this.posicionY = posicionY;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
