import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.image.WritableRaster;

import java.nio.ByteBuffer;

import com.jogamp.opengl.awt.GLCanvas;

public class Main implements Runnable, KeyListener {
    private static Thread displayT = new Thread(new Main());
    private static float ROTATION_SPEED = 3f;
    private static float ZOOM_SPEED = 1.05f;
    private static boolean bQuit = false;
    public static float rotationX = 0f;
    public static float rotationY = 0f;
    public static float rotationZ = 0f;
    public static float zoomChange = 1.0f;
    public static boolean wireFrame = false;
//    public static ByteBuffer pixels; // "массив" пикселей
//    public static int widthTexture; // ширина текстуры
//    public static int heightTexture; // высота тексту
    public static double [][] H = null;
    public static int heightMap;
    public static int widthMap;
    public static void main(String[] args) {
        displayT.start();
    }



    public void run() {
//        BufferedImage image;
//        try {
//            image = ImageIO.read(new File("map.jpg"));
//            heightMap = image.getHeight();
//            widthMap = image.getWidth();
//            H = new double[widthMap][heightMap];
//            for (int x = 0 ; x < widthMap ; x++)
//                for (int y = 0 ; y < heightMap ; y++)
//                    H[x][y] = (char)image.getRGB(x,y) % 256 / 20.0;
//        } catch (IOException e){
//            e.printStackTrace();
//        }
        Frame frame = new Frame("Jogl 3D Shape/Rotation");
        frame.setLocation(0,0);
        GLCanvas canvas = new GLCanvas();
        int size = frame.getExtendedState();

        canvas.addGLEventListener(new JavaRenderer());
        frame.add(canvas);
        frame.setUndecorated(true);
        size |= Frame.MAXIMIZED_BOTH;
        frame.setExtendedState(size);
        canvas.addKeyListener(this);
        frame.pack();
        //frame.setLocationRelativeTo(null);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                bQuit = true;
                System.exit(0);
            }
        });

        frame.setVisible(true);
        canvas.requestFocus();
        while( !bQuit ) {
            canvas.display();
        }
    }

        public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            displayT = null;
            bQuit = true;
            System.exit(0);
        }
        if(e.getKeyCode() == KeyEvent.VK_W) {
            rotationX = ROTATION_SPEED;
        }
        if(e.getKeyCode() == KeyEvent.VK_S) {
            rotationX = -ROTATION_SPEED;
        }
        if(e.getKeyCode() == KeyEvent.VK_A) {
            rotationY = ROTATION_SPEED;
        }
        if(e.getKeyCode() == KeyEvent.VK_D) {
            rotationY = -ROTATION_SPEED;
        }
        if(e.getKeyCode() == KeyEvent.VK_Q) {
            rotationZ = ROTATION_SPEED;
        }
        if(e.getKeyCode() == KeyEvent.VK_E) {
            rotationZ = -ROTATION_SPEED;
        }
        if(e.getKeyCode() == KeyEvent.VK_MINUS) {
            zoomChange = 1/ZOOM_SPEED;
        }
        if(e.getKeyCode() == KeyEvent.VK_EQUALS) {
            zoomChange = ZOOM_SPEED;
        }
        if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            wireFrame = !wireFrame;
        }
    }

    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_S) {
            rotationX = 0f;
        }
        if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_D) {
            rotationY = 0f;
        }
        if(e.getKeyCode() == KeyEvent.VK_Q || e.getKeyCode() == KeyEvent.VK_E) {
            rotationZ = 0f;
        }
        if(e.getKeyCode() == KeyEvent.VK_MINUS || e.getKeyCode() == KeyEvent.VK_EQUALS) {
            zoomChange = 1.0f;
        }
    }

    public void keyTyped(KeyEvent e) {
    }
}
