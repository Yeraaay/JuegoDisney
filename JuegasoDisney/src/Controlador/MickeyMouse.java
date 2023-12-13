package Controlador;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class MickeyMouse {

	public int x, y; // Posición de Mickey
	public static Image[] imagesIzquierda = new Image[4]; // Array de imágenes para la izquierda de Mickey
	public static Image[] imagesDerecha = new Image[4]; // Array de imágenes para la derecha de Mickey
	public static Image[] images = new Image[4];
	public static int currentFrame; // Índice de la imagen actual
	public static Rectangle mickeyBounds;
	private static int vida;
	private static int vidaMaxima;
	private int longitudBarraVida;
	public boolean direccion = true;

	//Métodos (Getter y Setter)    
	public  int getX() {
		return x;
	}

	public  void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
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

	public boolean isDireccion() {
		return direccion;
	}
	public void setDireccion(boolean direccion) {
		this.direccion = direccion;
	}

	public void recibirDanio(int cantidadDanio) {
		vida -= cantidadDanio;

		// Asegurarse de que la vida no sea menor que 0
		vida = Math.max(0, vida);

		// Calcular la longitud de la barra de vida proporcional al nuevo valor de vida
		int barraVidaWidth = 50; // Ancho de la barra de vida
		longitudBarraVida = (int) ((double) vida / vidaMaxima * barraVidaWidth);
	}

	//CONSTRUCTOR DE MICKEY MOUSE
	public MickeyMouse(int x, int y) {
		this.x = x;
		this.y = y;
		MickeyMouse.vida = 1000; // Inicializar la vida
		MickeyMouse.vidaMaxima = 1000; // Establecer la vida máxima
		this.direccion = true;
		MickeyMouse.setImagesIzquierda(imagesIzquierda);
		MickeyMouse.setImagesDerecha(imagesDerecha);
		this.images = getImagesDerecha();
	}

	public static Image[] getImagesIzquierda() {
		return imagesIzquierda;
	}

	public static void setImagesIzquierda(Image[] imagesIzquierda) {
		for (int i = 0; i < imagesIzquierda.length; i++) {
			try {
				ImageIcon icon = new ImageIcon("DisneySprite//MickeyMouse//MickeyMouseIzquierda" + (i + 1) + ".png");
				imagesIzquierda[i] = icon.getImage();
			} catch (Exception e) {
				e.printStackTrace();
			}
			currentFrame = 0;
		}
		
	}

	public static Image[] getImagesDerecha() {
		return imagesDerecha;
	}

	public static void setImagesDerecha(Image[] imagesDerecha) {
		for (int i = 0; i < imagesDerecha.length; i++) {
			try {
				ImageIcon icon = new ImageIcon("DisneySprite//MickeyMouse//MickeyMouse" + (i + 1) + ".png");
				imagesDerecha[i] = icon.getImage();
			} catch (Exception e) {
				e.printStackTrace();
			}
			currentFrame = 0;
		}
	}

	public void move(int newX, int newY) {
		// Método para cambiar la posición de Mickey
		this.x = newX;
		this.y = newY;
	}

	//Método para detectar la colision entre 2 objetos
	public boolean colisionaCon(Hienas hiena) {
		// Verificar colisión entre Mickey y la hiena
		mickeyBounds = new Rectangle(x, y, MickeyMouse.getImages()[currentFrame].getWidth(null), MickeyMouse.getImages()[currentFrame].getHeight(null));
		Rectangle hienaBounds = new Rectangle(hiena.getX(), hiena.getY(), hiena.getImages()[0].getWidth(null), hiena.getImages()[0].getHeight(null));

		return mickeyBounds.intersects(hienaBounds); // Devuelve true si hay colisión, de lo contrario, false
	}
	
	public boolean colisionaConCorason(Corazon corazon) {
		// Verificar colisión entre Mickey y la hiena
		mickeyBounds = new Rectangle(x, y, MickeyMouse.getImages()[currentFrame].getWidth(null), MickeyMouse.getImages()[currentFrame].getHeight(null));
		Rectangle coraBounds = new Rectangle(corazon.getX(), corazon.getY(), corazon.getImagen()[0].getWidth(null), corazon.getImagen()[0].getHeight(null));

		return mickeyBounds.intersects(coraBounds); // Devuelve true si hay colisión, de lo contrario, false
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

	// Método para dibujar a Mickey con la imagen actual de la animación	    
	public void draw(Graphics g) {
		g.drawImage(images[currentFrame], x, y, null);
		
		// Dibujar la barra de vida debajo de Mickey
		int barraVidaWidth = 50; // Ancho de la barra de vida
		int barraVidaHeight = 10; // Altura de la barra de vida
		int barraVidaX = x + 25; // Posición X de la barra de vida
		int barraVidaY = y + images[currentFrame].getHeight(null) + 5; // Posición Y de la barra de vida


		longitudBarraVida = (int) ((double) vida / vidaMaxima * barraVidaWidth);

		// Dibujar la barra de vida
		g.setColor(Color.GREEN); // Establecer el color de la barra de vida (puedes ajustar esto)
		g.fillRect(barraVidaX, barraVidaY, longitudBarraVida, barraVidaHeight);

		// Dibujar el contorno de la barra de vida
		g.setColor(Color.BLACK); // Establecer el color del contorno (puedes ajustar esto)
		g.drawRect(barraVidaX, barraVidaY, barraVidaWidth, barraVidaHeight);
	}

}


