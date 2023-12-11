package Controlador;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class MickeyMouse {

	public static int x, y; // Posición de Mickey
	public static Image[] images; // Array de imágenes de Mickey
	public static int currentFrame; // Índice de la imagen actual
	public static Rectangle mickeyBounds;
	private static int vida;
    private static int vidaMaxima;
	
	//Métodos (Getter y Setter)
	public static int getX() {
		return x;
	}

	public static void setX(int x) {
		MickeyMouse.x = x;
	}

	public static int getY() {
		return y;
	}

	public static void setY(int y) {
		MickeyMouse.y = y;
	}

	public static Image[] getImages() {
		return images;
	}

	public static void setImages(Image[] images) {
		MickeyMouse.images = images;
	}

	public static int getCurrentFrame() {
		return currentFrame;
	}

	public static void setCurrentFrame(int currentFrame) {
		MickeyMouse.currentFrame = currentFrame;
	}

	public static Rectangle getMickeyBounds() {
		return mickeyBounds;
	}

	public static void setMickeyBounds(Rectangle mickeyBounds) {
		MickeyMouse.mickeyBounds = mickeyBounds;
	}
	public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        MickeyMouse.vida = vida;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public void setVidaMaxima(int vidaMaxima) {
        MickeyMouse.vidaMaxima = vidaMaxima;
    }

    public void recibirDanio(int cantidadDanio) {
        vida -= cantidadDanio;

        // Asegurarse de que la vida no sea menor que 0
        vida = Math.max(0, vida);
    }

    public void curar(int cantidadCura) {
        vida += cantidadCura;

        // Asegurarse de que la vida no supere la vida máxima
        vida = Math.min(vida, vidaMaxima);
    }
	public MickeyMouse(int x, int y) {
        MickeyMouse.x = x;
        MickeyMouse.y = y;
        vida = 1000; // Inicializar la vida
        vidaMaxima = 1000; // Establecer la vida máxima

        // Cargar la secuencia de imágenes de Mickey
        images = new Image[4]; // Supongamos que tenemos 4 imágenes de la animación

        for (int i = 0; i < images.length; i++) {
            try {
                ImageIcon icon = new ImageIcon("DisneySprite//MickeyMouse//MickeyMouse" + (i + 1) + ".png");
                images[i] = icon.getImage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        currentFrame = 0; // Empezamos desde la primera imagen
    }

	public void move(int newX, int newY) {
		// Método para cambiar la posición de Mickey
		MickeyMouse.x = newX;
		MickeyMouse.y = newY;
	}
	
	//Método para detectar la colision entre 2 objetos
	public static boolean colisionaCon(Hienas hiena) {
        // Verificar colisión entre Mickey y la hiena
        Rectangle mickeyBounds = new Rectangle(x, y, images[currentFrame].getWidth(null), images[currentFrame].getHeight(null));
        Rectangle hienaBounds = new Rectangle(hiena.getX(), hiena.getY(), hiena.getImages()[0].getWidth(null), hiena.getImages()[0].getHeight(null));

        return mickeyBounds.intersects(hienaBounds); // Devuelve true si hay colisión, de lo contrario, false
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

        // Dibujar la barra de vida debajo de Mickey
        int barraVidaWidth = 50; // Ancho de la barra de vida
        int barraVidaHeight = 10; // Altura de la barra de vida
        int barraVidaX = x; // Posición X de la barra de vida
        int barraVidaY = y + images[currentFrame].getHeight(null) + 5; // Posición Y de la barra de vida

        // Calcular la longitud de la barra de vida proporcional a la vida actual
        int longitudBarraVida = (int) ((double) vida / vidaMaxima * barraVidaWidth);

        // Dibujar la barra de vida
        g.drawRect(barraVidaX, barraVidaY, barraVidaWidth, barraVidaHeight);
        g.fillRect(barraVidaX, barraVidaY, longitudBarraVida, barraVidaHeight);
    }
}


