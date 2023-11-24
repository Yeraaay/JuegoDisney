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

/**
 * @author Yeray Moraleda, Jesús Guijarro, Enrique Bellido
 */

public class Controlador extends JFrame implements Runnable, KeyListener {
	
	private final int ANCHURA = 1920;
	private final int ALTURA= 1080;

	private static final long serialVersionUID = 1L;
	private MickeyMouse mickey;
	private List<Hienas> hiena = new ArrayList<Hienas>();
	private Ataques ataque;
	BufferedImage buffer; // Imagen de fondo para "borrar" el rastro
	final int TOTAL_FRAMES = 5; // Número total de frames en la animación
	int contadorVelocidad = 0;
	int contadorAtaque = 0;
	int contadorFinAtaque = 0;
	
	private BufferedImage fondoImage; // Imagen de fondo
    private int fondoWidth; // Ancho de la imagen de fondo
    private int fondoHeight; // Alto de la imagen de fondo
    private int fondoOffsetX = 0; // Desplazamiento horizontal del fondo
    private int fondoOffsetY = 0; // Desplazamiento vertical del fondo
	
	
	
	public Controlador() {
		mickey = new MickeyMouse(ANCHURA / 2, ALTURA /2);
		hiena.add(new Hienas(200, 100));
		hiena.add(new Hienas(200, 200));
		hiena.add(new Hienas(200, 300));
		hiena.add(new Hienas(200, 400));
		hiena.add(new Hienas(200, 500));
		hiena.add(new Hienas(200, 600));
		hiena.add(new Hienas(200, 700));
		hiena.add(new Hienas(200, 800));
		hiena.add(new Hienas(200, 900));
		hiena.add(new Hienas(200, 1000));
		ataque = new Ataques((ANCHURA/2) -55, (ALTURA / 2) -40);
		
		buffer = new BufferedImage(ANCHURA, ALTURA, BufferedImage.TYPE_INT_RGB);
		
        try {
            fondoImage = ImageIO.read(new File("DisneySprite/Mapa/mapav2.png")); // Cambia la ruta por la de tu imagen
            fondoWidth = fondoImage.getWidth();
            fondoHeight = fondoImage.getHeight();
        } catch (IOException e) {
            e.printStackTrace();
            fondoImage = null; // Manejo de error: si no se puede cargar la imagen, se asigna null
        }

		setTitle("Disney's Survival");
		setSize(1920, 1080);
		setVisible(true);
		
        addKeyListener(this); // Agregar el KeyListener al JFrame
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
		

		Thread hilo = new Thread(this);
		hilo.start();
	}

	public void run() {
		while (true) {
			contadorVelocidad = contadorVelocidad + 5;
			
            for (Hienas hiena : hiena) {
                hiena.moverHacia(MickeyMouse.getX(), MickeyMouse.getY());
                hiena.updateAnimation();

                if (MickeyMouse.colisionaCon(hiena)) {
                    hiena.reducirVida(10);
                }
            }
            
            for (int i = 0; i < hiena.size(); i++) {
                Hienas hienaActual = hiena.get(i);
                
                // Movimiento hacia Mickey o posición actual si están muy cerca de otra hiena
                if (!hienaActual.colisionaConOtrasHienas(hiena.subList(0, i))) {
                    hienaActual.moverHacia(MickeyMouse.x, MickeyMouse.y);
                }
            }
			
			// Movimiento de las hienas hacia Mickey
            for (Hienas hiena: hiena) {
            	hiena.moverHacia(MickeyMouse.x, MickeyMouse.y);
            }
//			hiena.get(0).moverHacia(MickeyMouse.x, MickeyMouse.y);
//			hiena.get(1).moverHacia(MickeyMouse.x, MickeyMouse.y);
//			hiena.get(2).moverHacia(MickeyMouse.x, MickeyMouse.y);

			if (contadorVelocidad % 40 == 0) {
				mickey.updateAnimation();
				contadorAtaque++;
			}
			if (contadorVelocidad % 15 == 0) {
	            for (Hienas hiena: hiena) {
	            	hiena.updateAnimation();
	            }
//				hiena.get(0).updateAnimation();
//				hiena.get(1).updateAnimation();
//				hiena.get(2).updateAnimation();
			}
			if (contadorAtaque >= 10) {
				ataque.updateAnimation();
				contadorFinAtaque++;
				if (contadorFinAtaque == 5) {
					contadorAtaque = 0;
					contadorFinAtaque = 0;
				}
			}
			
			repaint();

			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void update(Graphics g) {
		paint(g);
	}
	
	public void keyPressed(KeyEvent e) {
	    int key = e.getKeyCode();

	    int speed = 25; // Define la velocidad de movimiento

	    if (key == KeyEvent.VK_W) {
//	        mickey.restarY(); // Mover hacia arriba
//	        ataque.restarY(); // Mover hacia arriba
	        // Actualizar offsetY para desplazar el fondo hacia abajo
	        fondoOffsetY -= speed;
	    } else if (key == KeyEvent.VK_S) {
//	        mickey.sumarY(); // Mover hacia abajo
//	        ataque.sumarY(); // Mover hacia abajo
	        // Actualizar offsetY para desplazar el fondo hacia arriba
	        fondoOffsetY += speed;
	    } else if (key == KeyEvent.VK_A) {
//	        mickey.restarX(); // Mover hacia la izquierda
//	        ataque.restarX(); // Mover hacia la izquierda
	        // Actualizar offsetX para desplazar el fondo hacia la derecha
	        fondoOffsetX -= speed;
	    } else if (key == KeyEvent.VK_D) {
//	        mickey.sumarX(); // Mover hacia la derecha
//	        ataque.sumarX(); // Mover hacia la derecha
	        // Actualizar offsetX para desplazar el fondo hacia la izquierda
	        fondoOffsetX += speed;
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
//	    hiena.get(0).draw(bufferGraphics);
//	    hiena.get(1).draw(bufferGraphics);
//	    hiena.get(2).draw(bufferGraphics);
	    ataque.draw(bufferGraphics);

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
		// TODO Auto-generated method stub
		
	}



}