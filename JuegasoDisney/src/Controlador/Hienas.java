package Controlador;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.List;

import javax.swing.ImageIcon;

public class Hienas {

	int x, y; // Posición de la hiena
	Image[] images; // Array de imágenes de la hiena
	Image[] imagenMuerte; // Array de imágenes de la hiena cuando su vida disminuye a 0
	private int currentFrame; // Índice de la imagen actual
	private int currentFrameMuerte; // Índice de la imagen actual
	private int vida;
	private boolean muerto = false;

	//Métodos (Getter y Setter)
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Image[] getImages() {
		return images;
	}

	public void setImages(Image[] images) {
		this.images = images;
	}

	public int getCurrentFrame() {
		return currentFrame;
	}

	public void setCurrentFrame(int currentFrame) {
		this.currentFrame = currentFrame;
	}

	public int getVida() {
		return vida;
	}

	public void setVida(int vida) {
		this.vida = vida;
	}

	public Hienas(int x, int y) {
		this.x = x;
		this.y = y;
		vida = 100;

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

	// Método para que la hiena se mueva hacia la posición de la hiena
	public void moverHacia(int targetX, int targetY) {
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
		// Método para actualizar la animación de las hienas (cambiar la imagen actual)
		currentFrame++;
		if (currentFrame >= images.length) {
			currentFrame = 0; // Reiniciamos la animación al llegar al final
		}
	}
	
	public boolean hienaColisionaCon(Hienas otraHiena) {
	    int esteX = getX();
	    int esteY = getY();
	    int esteAncho = images[currentFrame].getWidth(null); // Ancho de la imagen actual
	    int esteAlto = images[currentFrame].getHeight(null); // Alto de la imagen actual

	    int otraX = otraHiena.getX();
	    int otraY = otraHiena.getY();
	    int otraAncho = otraHiena.getImages()[otraHiena.getCurrentFrame()].getWidth(null); // Ancho de la imagen de la otra hiena
	    int otraAlto = otraHiena.getImages()[otraHiena.getCurrentFrame()].getHeight(null); // Alto de la imagen de la otra hiena

	    // Comprobar si hay colisión entre las dos hienas
	    return (esteX < otraX + otraAncho &&
	            esteX + esteAncho > otraX &&
	            esteY < otraY + otraAlto &&
	            esteY + esteAlto > otraY);
	}


	public boolean colisionaConOtrasHienas(List<Hienas> otrasHienas) {
	    for (Hienas otraHiena : otrasHienas) {
	        if (this != otraHiena && this.hienaColisionaCon(otraHiena)) {
	        	return true; // Existe colisión con alguna otra hiena	        	
	        }
	    }
	    return false; // No hay colisión con otras hienas
	}


	public int reducirVida(int vidaQuitada) {
		setVida(getVida() - vidaQuitada);
		
        if (getVida() <= 0 && !muerto) { // Verificar si la vida es 0 y la hiena no ha muerto ya
            muerto = true; // Marcar que la hiena ha muerto para evitar que se repita la animación

            imagenMuerte = new Image[8];
            for (int i = 0; i < imagenMuerte.length; i++) {
                try {
                    ImageIcon icon = new ImageIcon(".//DisneySprite//Hienas//hienaMuerte" + (i + 1) + ".png");
                    imagenMuerte[i] = icon.getImage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

		return getVida();
	}

    public void draw(Graphics g) {
    	if (getVida() <= 0 && muerto && imagenMuerte != null) {
            if (currentFrameMuerte < imagenMuerte.length) {
                g.drawImage(imagenMuerte[currentFrameMuerte], x, y, null);
                currentFrameMuerte++;
            }
        } else {
            // Si la hiena aún está viva, dibujar la imagen de la animación normal
            g.drawImage(images[currentFrame], x, y, null);
        }

        //g.setColor(Color.red);
        //g.fillRect(x, y - 10, getVida(), 5); // Barra de vida
    }



}