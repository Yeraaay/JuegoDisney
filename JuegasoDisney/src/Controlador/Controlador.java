package Controlador;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * @author Yeray Moraleda, Jesús Guijarro, Enrique Bellido
 */

public class Controlador extends JFrame implements Runnable, KeyListener {

	private final int ANCHURA = 1920;
	private final int ALTURA= 1080;
	private static final long serialVersionUID = 1L;
	private MickeyMouse mickey;
	public List<Hienas> hiena = new ArrayList<Hienas>();
	private Ataques ataque;
	BufferedImage buffer; // Imagen de fondo para "borrar" el rastro
	final int TOTAL_FRAMES = 5; // Número total de frames en la animación
	int contadorVelocidad = 0;
	int contadorAtaque = 0;
	int contadorFinAtaque = 0;
	public List<Corazon> listacorazon = new ArrayList<Corazon>();
	private BufferedImage fondoImage; // Imagen de fondo
	private int fondoWidth; // Ancho de la imagen de fondo
	private int fondoHeight; // Alto de la imagen de fondo
	private int fondoOffsetX = 0; // Desplazamiento horizontal del fondo
	private int fondoOffsetY = 0; // Desplazamiento vertical del fondo
	int speed = 50; // Define la velocidad de movimiento
	public boolean wPresionada = false;
	public boolean aPresionada = false;
	public boolean sPresionada = false;
	public boolean dPresionada = false;

	// Tiempo de partida
	private int tiempoPartida = 60;
	private boolean juegoActivo = true;
	private JLabel labelTiempo;

	public Controlador() {
		setTitle("Disney's Survival");
		setSize(1920, 1080);
		setVisible(true);
		setResizable(false);

		addKeyListener(this); // Agregar el KeyListener al JFrame
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);

		mickey = new MickeyMouse(ANCHURA / 2, ALTURA /2);
		ataque = new Ataques((ANCHURA/2) -55, (ALTURA / 2) -40);
		buffer = new BufferedImage(ANCHURA, ALTURA, BufferedImage.TYPE_INT_RGB);

		generarCorazones();

		try {
			fondoImage = ImageIO.read(new File("DisneySprite/Mapa/mapav2.png")); // Cambia la ruta por la de tu imagen
			fondoWidth = fondoImage.getWidth();
			fondoHeight = fondoImage.getHeight();
		} catch (IOException e) {
			e.printStackTrace();
			fondoImage = null; // Manejo de error: si no se puede cargar la imagen, se asigna null
		}

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});


		Thread hilo = new Thread(this);
		hilo.start();
	}

	public int getTiempoPartida() {
		return tiempoPartida;
	}

	private void inicializarHienas() {
		// Generar 60 hienas en posiciones aleatorias alrededor de Mickey, pero más lejos
		for (int i = 0; i < 25; i++) {
			Hienas nuevaHiena = generarHienaAleatoria();
			hiena.add(nuevaHiena);
		}
	}

	private Hienas generarHienaAleatoria() {
		int mickeyX = mickey.getX();
		int mickeyY = mickey.getY();

		// Generar coordenadas aleatorias alrededor de Mickey desde todas las direcciones
		int direccion = (int) (Math.random() * 8); // 0-3: izquierda, derecha, arriba, abajo, 4-7: diagonales
		int nuevaX = 0;
		int nuevaY = 0;

		switch (direccion) {
		case 0: // Izquierda
			nuevaX = mickeyX - (int) (Math.random() * 1000 + 200);
			nuevaY = mickeyY + (int) (Math.random() * 1000 - 200);
			break;
		case 1: // Derecha
			nuevaX = mickeyX + (int) (Math.random() * 1000 + 200);
			nuevaY = mickeyY + (int) (Math.random() * 1000 - 200);
			break;
		case 2: // Arriba
			nuevaX = mickeyX + (int) (Math.random() * 1000 - 200);
			nuevaY = mickeyY - (int) (Math.random() * 1000+ 200);
			break;
		case 3: // Abajo
			nuevaX = mickeyX + (int) (Math.random() * 1000 - 200);
			nuevaY = mickeyY + (int) (Math.random() * 1000 + 200);
			break;
		case 4: // Diagonal superior izquierda
			nuevaX = mickeyX - (int) (Math.random() * 1000 + 200);
			nuevaY = mickeyY - (int) (Math.random() * 1000 + 200);
			break;
		case 5: // Diagonal superior derecha
			nuevaX = mickeyX + (int) (Math.random() * 1000 + 200);
			nuevaY = mickeyY - (int) (Math.random() * 1000 + 200);
			break;
		case 6: // Diagonal inferior izquierda
			nuevaX = mickeyX - (int) (Math.random() * 1000 + 200);
			nuevaY = mickeyY + (int) (Math.random() * 1000 + 200);
			break;
		case 7: // Diagonal inferior derecha
			nuevaX = mickeyX + (int) (Math.random() * 1000 + 200);
			nuevaY = mickeyY + (int) (Math.random() * 1000 + 200);
			break;
		}

		return new Hienas(nuevaX, nuevaY);
	}

	public void generarCorazones() {
		// Generar 5 corazones en posiciones aleatorias alrededor de Mickey, pero más lejos
		for (int i = 0; i < 5; i++) {
			Corazon nuevoCorazon = new Corazon(0, 0);
			nuevoCorazon.generarPosicionAleatoria(960, 1000, 540, 1000);
			listacorazon.add(nuevoCorazon);
		}
	}

	public void run() {
		while (true) {
			contadorVelocidad = contadorVelocidad + 2;

			for (Hienas hiena : hiena) {
				hiena.moverHacia(mickey.getX(), mickey.getY());
				hiena.updateAnimation();

				if (mickey.colisionaCon(hiena)) {
					hiena.reducirVida(10);
					mickey.setVida(mickey.getVida() - 2);
					System.out.println("Vida mickey: " + mickey.getVida());
				}
			}
			if (mickey.getVida() <= 0 || tiempoPartida == 4200) {
				// Mostrar JOptionPane
				int opcion = JOptionPane.showOptionDialog(
						this,
						"GAME OVER ¿Desea reiniciar el juego?",
						"Game Over",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						null,
						new Object[]{"Reiniciar", "Salir"},
						"Reiniciar");

				if (opcion == JOptionPane.YES_OPTION) {
					reiniciarJuego(); // Reiniciar el juego
				} else {
					System.exit(0); // Salir del juego
				}
			}

			for(Corazon corazon: listacorazon) {
				if (mickey.colisionaConCorason(corazon) && mickey.getVida() < mickey.getVidaMaxima() - 150) {
					mickey.setVida(mickey.getVida() + 150);
					listacorazon.remove(corazon);
					 break; // Rompe el bucle para evitar problemas al modificar la lista mientras se itera sobre ella
				}
			}

			for (int i = 0; i < hiena.size(); i++) {
				Hienas hienaActual = hiena.get(i);
				if (hienaActual.getVida() <= 0) {
					if (!hienaActual.isMuerto()) {
						// Si la hiena no ha sido marcada como muerta, realizar la animación de muerte
						hienaActual.reducirVida(0); // Esto marca a la hiena como muerta para que se inicie la animación
					} else {
						// Si la animación de muerte no ha terminado, seguir mostrando las imágenes de la muerte
						if (hienaActual.getCurrentFrameMuerte() < hienaActual.getImagenMuerte().length) {
							// Mostrar la imagen de la animación de muerte actual
							hienaActual.setCurrentFrameMuerte(hienaActual.getCurrentFrameMuerte() + 1);
						} else {
							// Si la animación ha terminado, reemplazar la hiena muerta con una nueva
							hiena.set(i, generarHienaAleatoria());
						}
					}
				}
			}

			for (int i = 0; i < hiena.size(); i++) {
				Hienas hienaActual = hiena.get(i);

				// Movimiento hacia Mickey o posición actual si están muy cerca de otra hiena
				if (!hienaActual.colisionaConOtrasHienas(hiena.subList(0, i))) {
					hienaActual.moverHacia(mickey.x, mickey.y);
				}
			}


			if (contadorVelocidad % 70 == 0) {
				tiempoPartida--;
				System.out.println("Timepo restante: " + getTiempoPartida());
			}

			if (contadorVelocidad % 240 == 0) {
				inicializarHienas();
			}

			if (contadorVelocidad % 70 == 0){
				generarCorazones();
			}

			if (contadorVelocidad % 10 == 0) {
				for (Corazon corazon: listacorazon) {
					corazon.updateAnimation();					
				}
			}

			if (contadorVelocidad % 10 == 0) {
				mickey.updateAnimation();
				contadorAtaque++;
			}
			if (contadorVelocidad % 20 == 0) {
				for (Hienas hiena: hiena) {
					hiena.updateAnimation();
				}

			}
			if (contadorAtaque >= 30) {
				ataque.updateAnimation();
				contadorFinAtaque++;
				if (contadorFinAtaque == 5) {
					contadorAtaque = 0;
					contadorFinAtaque = 0;
				}
			}
			if (wPresionada && !aPresionada && !dPresionada && fondoOffsetY>10) {
				for (Hienas hiena : hiena) {
					hiena.setY(hiena.getY()+speed);
					hiena.updateAnimation();
				}
				for (Corazon corazon: listacorazon) {
					corazon.setY(corazon.getY() + speed * 2);					
					corazon.updateAnimation();
				}
				fondoOffsetY -= speed*2;

			} else if (sPresionada && !aPresionada && !dPresionada && fondoOffsetY<3220) {
				for (Hienas hiena : hiena) {
					hiena.setY(hiena.getY()-speed);
					hiena.updateAnimation();
				}
				for (Corazon corazon: listacorazon) {
					corazon.setY(corazon.getY() - speed * 2);
					corazon.updateAnimation();
				}

				fondoOffsetY += speed*2;
			}else if (aPresionada && !wPresionada && !sPresionada && fondoOffsetX>10){
				mickey.setImages(mickey.getImagesIzquierda());
				for (Hienas hiena : hiena) {
					hiena.setX(hiena.getX()+speed);
					hiena.updateAnimation();
				}
				for (Corazon corazon: listacorazon) {
					corazon.setX(corazon.getX() + speed * 2);
					corazon.updateAnimation();
				}

				fondoOffsetX -= speed*2;
			} else if (dPresionada && !wPresionada && !sPresionada && fondoOffsetX<5750) {
				mickey.setImages(mickey.getImagesDerecha());
				for (Hienas hiena : hiena) {
					hiena.setX(hiena.getX()-speed);
					hiena.updateAnimation();
				}
				for (Corazon corazon: listacorazon) {
					corazon.setX(corazon.getX() - speed * 2);
					corazon.updateAnimation();
				}

				fondoOffsetX += speed*2;
			}else if (wPresionada && aPresionada && fondoOffsetY>10 && fondoOffsetX>10) {
				mickey.setImages(mickey.getImagesIzquierda());
				for (Hienas hiena : hiena) {
					hiena.setY(hiena.getY()+speed);
					hiena.setX(hiena.getX()+speed);
					hiena.updateAnimation();
				}
				for (Corazon corazon: listacorazon) {
					corazon.setY(corazon.getY()+speed);
					corazon.setX(corazon.getX()+speed);
					corazon.updateAnimation();
				}

				fondoOffsetY -= speed;
				fondoOffsetX -= speed;
			}else if (wPresionada && dPresionada && fondoOffsetY>10 && fondoOffsetX<5750) {
				mickey.setImages(mickey.getImagesDerecha());
				for (Hienas hiena : hiena) {
					hiena.setY(hiena.getY()+speed);
					hiena.setX(hiena.getX()-speed);
					hiena.updateAnimation();
				}
				for (Corazon corazon: listacorazon) {
					corazon.setY(corazon.getY()+speed);
					corazon.setX(corazon.getX()-speed);
					corazon.updateAnimation();
				}

				fondoOffsetY -= speed;
				fondoOffsetX += speed;
			}else if (sPresionada && aPresionada && fondoOffsetY<3220 && fondoOffsetX>10) {
				mickey.setImages(mickey.getImagesIzquierda());
				for (Hienas hiena : hiena) {
					hiena.setY(hiena.getY()-speed);
					hiena.setX(hiena.getX()+speed);
					hiena.updateAnimation();
				}
				for (Corazon corazon: listacorazon) {
					corazon.setY(corazon.getY()-speed);
					corazon.setX(corazon.getX()+speed);
					corazon.updateAnimation();
				}

				fondoOffsetY += speed;
				fondoOffsetX -= speed;
			}else if (sPresionada && dPresionada && fondoOffsetY<3220 && fondoOffsetX<5750) {
				mickey.setImages(mickey.getImagesDerecha());
				for (Hienas hiena : hiena) {
					hiena.setY(hiena.getY()-speed);
					hiena.setX(hiena.getX()-speed);
					hiena.updateAnimation();
				}
				for (Corazon corazon: listacorazon) {
					corazon.setY(corazon.getY()-speed);
					corazon.setX(corazon.getX()-speed);
					corazon.updateAnimation();
				}

				fondoOffsetY += speed;
				fondoOffsetX += speed;
			}
			repaint();

			try {

				Thread.sleep(30);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void reiniciarJuego() {
	    fondoOffsetX = 0;
	    fondoOffsetY = 0;

	    // Limpiar todas las hienas
	    hiena.clear();

	    // Limpiar todos los corazones
	    listacorazon.clear();

	    // Reiniciar la posición inicial de Mickey
	    mickey.setX(ANCHURA / 2);
	    mickey.setY(ALTURA / 2);

	    // Reiniciar la vida de Mickey
	    mickey.setVida(1000);

	    // Reinicializar los arrays adicionales de hienas y corazones
	    contadorVelocidad = 0; // Reinicializar el contador de velocidad para generar nuevas hienas y corazones
	}

	
	private void reiniciarHienas() {
		// Limpiar la lista actual de hienas
		hiena.clear();

		// Volver a generar 20 hienas en posiciones aleatorias alrededor de Mickey, pero más lejos
		for (int i = 0; i < 20; i++) {
			hiena.add(generarHienaAleatoria());
		}
	}
	
	public void update(Graphics g) {
		paint(g);
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_W) {
			wPresionada = true;
		} else if (key == KeyEvent.VK_S) {
			sPresionada = true;
		} else if (key == KeyEvent.VK_A) {
			aPresionada = true;
		} else if (key == KeyEvent.VK_D) {
			dPresionada = true;
		}
	}


	public void paint(Graphics g) {
		Graphics bufferGraphics = buffer.getGraphics();
		bufferGraphics.setColor(getBackground());
		bufferGraphics.fillRect(0, 0, getWidth(), getHeight());

		if (fondoImage != null) {
			int maxXOffset = fondoWidth - getWidth();
			int maxYOffset = fondoHeight - getHeight();
			fondoOffsetX = Math.min(Math.max(fondoOffsetX, 0), maxXOffset);
			fondoOffsetY = Math.min(Math.max(fondoOffsetY, 0), maxYOffset);

			bufferGraphics.drawImage(fondoImage, -fondoOffsetX, -fondoOffsetY, this);
		}
		mickey.draw(bufferGraphics);
		for (Hienas hiena: hiena) {
			hiena.draw(bufferGraphics);
		}


		ataque.draw(bufferGraphics);
		for (Corazon corazon: listacorazon) {
			corazon.draw(bufferGraphics);
		}

		g.drawImage(buffer, 0, 0, this);
	}



	public static void main(String[] args) {
		new Controlador();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_W) {
			wPresionada = false;
		} else if (key == KeyEvent.VK_A) {
			aPresionada = false;
		} else if (key == KeyEvent.VK_S) {
			sPresionada = false;
		} else if (key == KeyEvent.VK_D) {
			dPresionada = false;
		}
	}




}