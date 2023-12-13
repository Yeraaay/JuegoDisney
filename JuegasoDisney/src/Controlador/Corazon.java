package Controlador;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;
public class Corazon {
	private int x, y; // Posición del corazón
    private Image[] imagen= new Image[6]; // Imagen del corazón
    public static int currentFrame;
    private int velX = 0; // Velocidad en la dirección X
    private int velY = 0; // Velocidad en la dirección Y
    
    public Corazon(int x, int y) {
        this.x = x;
        this.y = y;
        velX = 0;
        velY = 0;
        // Cargar la imagen del corazón
        for (int i = 0; i < imagen.length; i++) {
			try {
				ImageIcon icon = new ImageIcon("DisneySprite//corazon" + (i + 1) + ".png");
				imagen[i] = icon.getImage();
			} catch (Exception e) {
				e.printStackTrace();
			}
			currentFrame = 0;
		}
    }
    
    

    public Image[] getImagen() {
		return imagen;
	}

	public void setImagen(Image[] imagen) {
		this.imagen = imagen;
	}

	public static int getCurrentFrame() {
		return currentFrame;
	}

	public static void setCurrentFrame(int currentFrame) {
		Corazon.currentFrame = currentFrame;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public void updateAnimation() {
		// Método para actualizar la animación del corazon (cambiar la imagen actual)
		currentFrame++;
		if (currentFrame >= imagen.length) {
			currentFrame = 0; // Reiniciamos la animación al llegar al final
		}
	}
    
    public void draw(Graphics g) {
    	Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(imagen[currentFrame], x, y, null);
    }

    // Otros métodos que puedas necesitar, como detección de colisiones, etc.

    public void generarPosicionAleatoria(int minFondoOffsetX, int maxFondoOffsetX, int minFondoOffsetY, int maxFondoOffsetY) {
        Random random = new Random();

        // Generar coordenadas aleatorias dentro de los límites específicos del mapa
        x = minFondoOffsetX + random.nextInt(maxFondoOffsetX - minFondoOffsetX);
        y = minFondoOffsetY + random.nextInt(maxFondoOffsetY - minFondoOffsetY);
    }

}

