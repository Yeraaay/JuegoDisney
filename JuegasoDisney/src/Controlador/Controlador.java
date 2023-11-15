package Controlador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Controlador extends JPanel implements KeyListener, ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage[] framesMickeyMouse;
    private BufferedImage[] framesHienas;
    private int currentFrameIndexMickeyMOuse = 0;
    private int currentFrameIndexHiena = 0;
    private int xMickeyMouse = 100; // Coordenada x inicial de la animación
    private int yMickeyMouse = 100; // Coordenada y inicial de la animación
    private int xHiena = 200; // Coordenada x inicial de la animación
    private int yHiena= 200; // Coordenada y inicial de la animación
    private Timer animationTimerMickeyMouse;
    private Timer animationTimerHiena;
    private boolean flip = false; // Variable para controlar el giro

    public Controlador() {
        // Ruta donde se encuentran los frames de la animación exportada desde Aseprite
        String rutaDirectorio = ".//DisneySprite";
        int numFramesMickeyMouse = 4; // Número total de frames en la animación

        framesMickeyMouse = new BufferedImage[numFramesMickeyMouse];

        // Cargar cada frame como una imagen en el arreglo
        for (int i = 0; i < numFramesMickeyMouse; i++) {
            String nombreArchivo = "//MickeyMouse//MickeyMouseMovimiento" + (i + 1) + ".png"; // Nombre del archivo de imagen
            try {
            	framesMickeyMouse[i] = ImageIO.read(new File(rutaDirectorio + nombreArchivo));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        int delayMickeyMouse = 250; // Velocidad de la animación en milisegundos
        animationTimerMickeyMouse = new Timer(delayMickeyMouse, this);
        animationTimerMickeyMouse.start();
        
        int numFramesHiena = 4; // Número total de frames en la animación

        framesHienas = new BufferedImage[numFramesHiena];

        // Cargar cada frame como una imagen en el arreglo
        for (int i = 0; i < numFramesHiena; i++) {
            String nombreArchivo = "//Hienas//hiena" + (i + 1) + ".png"; // Nombre del archivo de imagen
            try {
            	framesHienas[i] = ImageIO.read(new File(rutaDirectorio + nombreArchivo));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        int delayHiena = 150; // Velocidad de la animación en milisegundos
        animationTimerHiena = new Timer(delayHiena, this);
        animationTimerHiena.start();

        setFocusable(true); // Permite al panel recibir eventos del teclado
        addKeyListener(this); // Agrega el KeyListener al panel
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (framesMickeyMouse[currentFrameIndexMickeyMOuse] != null) {
            // Crear una copia de la imagen original para no modificar la original
            BufferedImage image = framesMickeyMouse[currentFrameIndexMickeyMOuse];

            // Si flip es verdadero, voltea la imagen horizontalmente
            if (flip) {
                // Voltear horizontalmente usando AffineTransform
                AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
                tx.translate(-image.getWidth(null), 0);
                AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                image = op.filter(image, null);
            }

            g.drawImage(image, xMickeyMouse, yMickeyMouse, this);
        }
        if (framesHienas[currentFrameIndexHiena] != null) {
            g.drawImage(framesHienas[currentFrameIndexHiena], xHiena, yHiena, this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Lógica para la animación de Mickey Mouse
        currentFrameIndexMickeyMOuse = (currentFrameIndexMickeyMOuse + 1) % framesMickeyMouse.length;

        // Lógica para la animación de las hienas y su movimiento hacia Mickey Mouse
        currentFrameIndexHiena = (currentFrameIndexHiena + 1) % framesHienas.length;

        // Calcula la distancia entre la hiena y Mickey Mouse en cada eje
        int distanciaX = xMickeyMouse - xHiena;
        int distanciaY = yMickeyMouse - yHiena;

        // Mueve la hiena hacia la posición de Mickey Mouse en ambas direcciones
        if (distanciaX != 0) {
            xHiena += (distanciaX > 0) ? 1 : -1;
        }
        if (distanciaY != 0) {
            yHiena += (distanciaY > 0) ? 1 : -1;
        }

        repaint();
    }


    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        int speed = 15;

        if (key == KeyEvent.VK_W) {
            // Mueve hacia arriba
            yMickeyMouse -= speed;
        } else if (key == KeyEvent.VK_S) {
            // Mueve hacia abajo
            yMickeyMouse += speed;
        } else if (key == KeyEvent.VK_A) {
            // Mueve hacia la izquierda y activa el flip
            xMickeyMouse -= speed;
            flip = true;
        } else if (key == KeyEvent.VK_D) {
            // Mueve hacia la derecha y desactiva el flip
            xMickeyMouse += speed;
            flip = false;
        }

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // No se usa en este ejemplo, pero debe implementarse por la interfaz KeyListener
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // No se usa en este ejemplo, pero debe implementarse por la interfaz KeyListener
    }

    public static void main(String[] args) {
        JFrame ventana = new JFrame("Animación con Movimiento");
        ventana.setSize(500, 500);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Controlador animacion = new Controlador();
        ventana.add(animacion);
        ventana.setVisible(true);
    }
}