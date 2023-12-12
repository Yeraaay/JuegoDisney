package Controlador;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;
public class Corazon {
	private int x, y; // Posición del corazón
    private Image imagen; // Imagen del corazón

    public Corazon(int x, int y) {
        this.x = x;
        this.y = y;

        // Cargar la imagen del corazón
        try {
            ImageIcon icon = new ImageIcon("ruta/del/corazon.png"); // Ajusta la ruta de la imagen
            imagen = icon.getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void draw(Graphics g) {
        // Dibujar el corazón en la posición actual
        g.drawImage(imagen, x, y, null);
    }

    // Otros métodos que puedas necesitar, como detección de colisiones, etc.

    // Método para generar una posición aleatoria para el corazón en el mapa
    public void generarPosicionAleatoria(int mickeyX, int mickeyY, int maxXOffset, int maxYOffset) {
        Random random = new Random();

        // Generar coordenadas aleatorias alrededor de Mickey
        x = mickeyX + random.nextInt(maxXOffset) - maxXOffset / 2;
        y = mickeyY + random.nextInt(maxYOffset) - maxYOffset / 2;

        // Asegurarse de que las coordenadas estén dentro de los límites del mapa
        x = Math.max(0, Math.min(x, maxXOffset));
        y = Math.max(0, Math.min(y, maxYOffset));
    }
}

