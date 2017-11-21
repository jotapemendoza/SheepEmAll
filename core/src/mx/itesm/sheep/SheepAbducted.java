package mx.itesm.sheep;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Andreu on 15/10/17.
 */

public class SheepAbducted {

    // Posición oveja
    private float x = 0;
    private float y = 0;

    // Multiplicador de Velocidad
    private float velocidad = 1;

    // Color de la oveja
    private String color;

    // Tamaño
    private int alto = 255;
    private int ancho = 200;

    // Textura
    private Texture sheepText;


    public SheepAbducted(Texture textura, String color) {
        this.color = color;
        this.sheepText = textura;
    }

    public void render (SpriteBatch batch) {
        TextureRegion region = new TextureRegion(sheepText,ancho,alto);
        batch.draw(sheepText , x, y);
        y += velocidad;

        Gdx.app.log("y: ",Float.toString(y));
        Gdx.app.log("velocidad: ",Float.toString(velocidad));
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


    public int getAncho() { return ancho; }

    public int getAlto() { return alto; }

    public String getColor() { return color; }

    public void setVelocidad(float velocidad) { this.velocidad = velocidad; }

}
