package Controlador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Controlador extends JPanel implements KeyListener, ActionListener {

    private BufferedImage[] frames;
    private int currentFrameIndex = 0;
    private int x = 200; // Coordenada x inicial de la animación
    private int y = 200; // Coordenada y inicial de la animación
    private Timer animationTimer;

    public Controlador() {
        // Ruta donde se encuentran los frames de la animación exportada desde Aseprite
        String rutaDirectorio = ".//DisneySprite";
        int numFrames = 4; // Número total de frames en la animación

        frames = new BufferedImage[numFrames];

        // Cargar cada frame como una imagen en el arreglo
        for (int i = 0; i < numFrames; i++) {
            String nombreArchivo = "//MickeyMouse//MickeyMouseMovimiento" + (i + 1) + ".png"; // Nombre del archivo de imagen
            try {
                frames[i] = ImageIO.read(new File(rutaDirectorio + nombreArchivo));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        int delay = 180; // Velocidad de la animación en milisegundos
        animationTimer = new Timer(delay, this);
        animationTimer.start();

        setFocusable(true); // Permite al panel recibir eventos del teclado
        addKeyListener(this); // Agrega el KeyListener al panel
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (frames[currentFrameIndex] != null) {
            g.drawImage(frames[currentFrameIndex], x, y, this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        currentFrameIndex = (currentFrameIndex + 1) % frames.length;
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        int speed = 50; // Velocidad de movimiento

        if (key == KeyEvent.VK_W) {
            y -= speed; // Mueve hacia arriba
        } else if (key == KeyEvent.VK_S) {
            y += speed; // Mueve hacia abajo
        } else if (key == KeyEvent.VK_A) {
            x -= speed; // Mueve hacia la izquierda
        } else if (key == KeyEvent.VK_D) {
            x += speed; // Mueve hacia la derecha
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