package Controlador;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.ImageIcon;

public class Hienas {

	private int x, y; // Posición de la hiena
	private Image[] images = new Image[13]; // Array de imágenes de la hiena
	public static Image[] imagesIzquierda = new Image[13]; // Array de imágenes para la izquierda de las hienas
	public static Image[] imagesDerecha = new Image[13]; // Array de imágenes para la derecha de las hienas
	private Image[] imagenMuerte = new Image[8]; // Array de imágenes de la hiena cuando su vida disminuye a 0
	private int currentFrame; // Índice de la imagen actual
	private int currentFrameMuerte; // Índice de la imagen actual
	private int vida;
	private boolean muerto = false;
	private boolean direccion = true;

	//Métodos (Getter y Setter)
	public static Image[] getImagesIzquierda() {
		return imagesIzquierda;
	}

	public static void setImagesIzquierda(Image[] imagesIzquierda) {
		for (int i = 0; i < imagesIzquierda.length; i++) {
			try {
				ImageIcon icon = new ImageIcon(".//DisneySprite//Hienas//hiena"+ (i+1) + ".png");
				imagesIzquierda[i] = icon.getImage();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static Image[] getImagesDerecha() {
		return imagesDerecha;
	}

	public static void setImagesDerecha(Image[] imagesDerecha) {
	    for (int i = 0; i < imagesDerecha.length; i++) {
	        try {
	            ImageIcon icon = new ImageIcon(".//DisneySprite//Hienas//hiena"+ (i+1) + ".png");
	            Image image = icon.getImage();
	            
	            // Convertir la Image a BufferedImage
	            BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
	            Graphics2D bGr = bufferedImage.createGraphics();
	            bGr.drawImage(image, 0, 0, null);
	            bGr.dispose();

	            // Crear una transformación afín para reflejar la imagen horizontalmente
	            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
	            tx.translate(-bufferedImage.getWidth(null), 0);

	            // Aplicar la transformación a la imagen
	            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
	            BufferedImage mirroredImage = op.filter(bufferedImage, null);

	            // Convertir BufferedImage de vuelta a Image
	            imagesDerecha[i] = (Image) mirroredImage;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
	
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
	
	public Image[] getImagenMuerte() {
		return imagenMuerte;
	}

	public void setImagenMuerte(Image[] imagenMuerte) {
		this.imagenMuerte = imagenMuerte;
	}

	public int getCurrentFrameMuerte() {
		return currentFrameMuerte;
	}

	public void setCurrentFrameMuerte(int currentFrameMuerte) {
		this.currentFrameMuerte = currentFrameMuerte;
	}

	public boolean isMuerto() {
		return muerto;
	}

	public void setMuerto(boolean muerto) {
		this.muerto = muerto;
	}

	public boolean isDireccion() {
		return direccion;
	}

	public void setDireccion(boolean direccion) {
		this.direccion = direccion;
	}

	public Hienas(int x, int y) {
		this.x = x;
		this.y = y;
		vida = 100;
		Hienas.setImagesIzquierda(imagesIzquierda);
		Hienas.setImagesDerecha(imagesDerecha);
		this.images = getImagesIzquierda();

		currentFrame = 0; // Empezamos desde la primera imagen
	}

	// Método para que la hiena se mueva hacia la posición de la hiena
	public void moverHacia(int targetX, int targetY) {
		if (this.x < targetX) {
			this.x+=3;
			direccion = true;
			this.images = Hienas.getImagesDerecha();
		} else if (this.x > targetX) {
			this.x-=3;
			direccion = false;
			this.images = Hienas.getImagesIzquierda();
		}
		if (this.y < targetY) {
			this.y+=3;
		} else if (this.y > targetY) {
			this.y-=3;
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
    	if (getVida() <= 0 && muerto) {
            if (currentFrameMuerte < imagenMuerte.length) {
                g.drawImage(imagenMuerte[currentFrameMuerte], x, y, null);
                currentFrameMuerte++;
            }
        } else {
            // Si la hiena aún está viva, dibujar la imagen de la animación normal
            g.drawImage(images[currentFrame], x, y, null);
        }

    }
}