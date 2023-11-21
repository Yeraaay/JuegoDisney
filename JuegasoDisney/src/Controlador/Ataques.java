package Controlador;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Ataques {

	int x, y; // Posición de Mickey
	Image[] images; // Array de imágenes de ataque
	int currentFrame; // Índice de la imagen actual
	
	public Ataques() {
	}

	public Ataques(int x, int y) {
		this.x = x;
		this.y = y;

		// Cargar la secuencia de imágenes del ataque
		images = new Image[5]; // Supongamos que tenemos 5 imágenes de la animación

		for (int i = 0; i < images.length; i++) {
			try {
				ImageIcon icon = new ImageIcon("DisneySprite//Ataques//ataque" + (i+1) + ".png");
				images[i] = icon.getImage();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		currentFrame = 0; // Empezamos desde la primera imagen
	}

	public void move(int newX, int newY) {
		// Método para cambiar la posición de Mickey
		this.x = newX;
		this.y = newY;
	}
	
    public int restarX() {
    	x = x-10;
        return x;
    }

    public int restarY() {
    	y = y-10;
        return y;
    }
    
    public int sumarX() {
    	x = x+10;
        return x;
    }

    public int sumarY() {
    	y = y+10;
        return y;
    }

	public void updateAnimation() {
		// Método para actualizar la animación de Mickey (cambiar la imagen actual)
		currentFrame++;
		if (currentFrame >= images.length) {
			currentFrame = 0; // Reiniciamos la animación al llegar al final
		}
	}

	public void draw(Graphics g) {
		// Método para dibujar a Mickey con la imagen actual de la animación
		g.drawImage(images[currentFrame], x, y, null);
	}


}