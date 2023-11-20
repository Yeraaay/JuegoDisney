package Controlador;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

/**
 * @author Yeray Moraleda, Jesús Guijarro, Enrique Bellido
 */

public class Controlador extends JFrame implements Runnable, KeyListener {

	private static final long serialVersionUID = 1L;
	private MickeyMouse mickey;
	private Hienas hiena;
	private Ataques ataque;
	BufferedImage buffer; // Imagen de fondo para "borrar" el rastro
	final int TOTAL_FRAMES = 5; // Número total de frames en la animación
	int contadorVelocidad = 0;
	int contadorAtaque = 0;
	int contadorFinAtaque = 0;

	public Controlador() {
		mickey = new MickeyMouse(50, 50);
		hiena = new Hienas(200, 200);
		ataque = new Ataques(-5, 10);

		setTitle("Mickey vs Hienas");
		setSize(400, 400);
		setVisible(true);
		
        addKeyListener(this); // Agregar el KeyListener al JFrame
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});

		buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

		Thread hilo = new Thread(this);
		hilo.start();
	}

	public void run() {
		while (true) {
			contadorVelocidad = contadorVelocidad + 5;

			// Movimiento de las hienas hacia Mickey
			hiena.moverHacia(mickey.x, mickey.y);

			if (contadorVelocidad % 10 == 0) {
				mickey.updateAnimation();
				contadorAtaque++;
			}
			if (contadorVelocidad % 5 == 0) {
				hiena.updateAnimation();            	
			}
			if (contadorAtaque >= 10) {
				ataque.updateAnimation();
				contadorFinAtaque++;
				if (contadorFinAtaque == 4) {
					contadorAtaque = 0;
					contadorFinAtaque = 0;
				}
			}
			repaint();

			try {
				Thread.sleep(100);
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

        if (key == KeyEvent.VK_W) {
            mickey.restarY(); // Mover hacia arriba
            ataque.restarY(); // Mover hacia arriba
        } else if (key == KeyEvent.VK_S) {
            mickey.sumarY(); // Mover hacia abajo
            ataque.sumarY(); // Mover hacia abajo
        } else if (key == KeyEvent.VK_A) {
            mickey.restarX(); // Mover hacia la izquierda
            ataque.restarX(); // Mover hacia la izquierda
        } else if (key == KeyEvent.VK_D) {
            mickey.sumarX(); // Mover hacia la derecha
            ataque.sumarX(); // Mover hacia la derecha
        }
    }



	public void paint(Graphics g) {
		Graphics bufferGraphics = buffer.getGraphics();

		bufferGraphics.setColor(getBackground());
		bufferGraphics.fillRect(0, 0, getWidth(), getHeight());

		mickey.draw(bufferGraphics);
		hiena.draw(bufferGraphics);
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