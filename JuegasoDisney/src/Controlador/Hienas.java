package Controlador;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Hienas {
	
    int x, y; // Posición de la hiena
	Image[] images; // Array de imágenes de Mickey
	int currentFrame; // Índice de la imagen actual
    
    public Hienas(int x, int y) {
        this.x = x;
        this.y = y;
        
		// Cargar la secuencia de imágenes de las hienas
		images = new Image[13]; // Supongamos que tenemos 13 imágenes de la animación

		for (int i = 0; i < images.length; i++) {
			try {
				ImageIcon icon = new ImageIcon(".//DisneySprite//Hienas//hiena"+ (i+1) + ".png");
				images[i] = icon.getImage();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		currentFrame = 0; // Empezamos desde la primera imagen
    }
    
    public void moverHacia(int targetX, int targetY) {
        // Método para que la hiena se mueva hacia la posición de Mickey
        // Aquí puedes implementar un algoritmo simple de seguimiento
        // Por ejemplo:
        if (this.x < targetX) {
            this.x++;
        } else if (this.x > targetX) {
            this.x--;
        }
        if (this.y < targetY) {
            this.y++;
        } else if (this.y > targetY) {
            this.y--;
        }
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