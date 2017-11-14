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
    private float direccionX = 0;
    private float direccionY = 0;

    // Tamaño
    private int alto = 424;
    private int ancho = 691;

    // Animación
    private Animation animacion;
    private float timer;

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
                batch.draw(region,direccionX,direccionY);
                break;
            case PAUSADO:
                break;
            case SALIENDO:
                break;
        }
    }

    // mueve la nave
    public void spaceShipMove(float xM, float yM){
        direccionX = direccionX + xM;
        direccionY = direccionY + yM;
    }

    // invierte la dirección en x
    public void cambiarDireccionX(){
        direccionX *= -1;
    }

    // invierte la dirección en y
    public void cambiarDireccionY(){
        direccionY *= -1;
    }

    public enum Estado{
        MOVIENDO,
        PAUSADO,
        SALIENDO
    }

    public float getDireccionX() {
        return direccionX;
    }

    public void setDireccionX(float direccionX) {
        this.direccionX = direccionX;
    }

    public float getDireccionY() {
        return direccionY;
    }

    public void setDireccionY(float direccionY) {
        this.direccionY = direccionY;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
